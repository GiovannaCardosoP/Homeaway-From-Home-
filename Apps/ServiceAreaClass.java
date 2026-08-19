package Apps;
import Enum.ServiceType;
import Exceptions.*;
import Service.*;
import dataStructures.*;

public class ServiceAreaClass implements ServiceArea {
    private static final long serialVersionUID = 0L;


    private transient Map<String, ServiceMain> getService;
    private List<ServiceMain> allServiceToList;
    private TwoWayList<ServiceMain>[]  servicesByStar;
    private List<ServiceMain>[][]  servicesByStarAndType;
    private ServiceMain[] lessExpensiveServices;

    public ServiceAreaClass() {
        getService = new ClosedHashTable<>();
        allServiceToList = new SinglyLinkedList<>();
        servicesByStarAndType = initializeServicesByStarType();
        servicesByStar = initializeServicesByStar();
        lessExpensiveServices = new Service[3];
    }

    @Override
    public void serializedMaps(){
        getService = new SepChainHashTable<>();
        Iterator<ServiceMain> it = allServiceToList.iterator();
        while(it.hasNext()){
            ServiceMain service = it.next();
            getService.put(service.getName().toUpperCase(), service);
        }
    }



    @SuppressWarnings("unchecked")
    private TwoWayList<ServiceMain>[] initializeServicesByStar() {


        TwoWayList<ServiceMain>[] s = (DoublyLinkedList<ServiceMain>[]) new DoublyLinkedList[5];


        for (int i = 0; i < 5; i++) {
            s[i] = new DoublyLinkedList<>();
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    private List<ServiceMain>[][] initializeServicesByStarType() {


        List<ServiceMain>[][] s = (DoublyLinkedList<ServiceMain>[][]) new DoublyLinkedList[3][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                s[j][i] = new DoublyLinkedList<>();
            }
        }
        return s;
    }

    private boolean isValidPrice(int price) {
        return (price <= 0) ;
    }
    private boolean isNotValidDiscount(ServiceType t, int val) {
        return((t.equals(ServiceType.LEISURE) && (val < 0 || val > 100)));
    }
    public boolean isEatingOrLodging(ServiceType t){
        return !t.equals(ServiceType.LEISURE);
    }

    private boolean isNotValidCapacity(ServiceType t, int val) {
        return ((isEatingOrLodging(t)) && (val <= 0));
    }

    @Override
    public void addService(String name, Long longitude, Long latitude, int price, int val, ServiceType t) throws ServiceAlreadyExistsException, InvalidPriceException,
            InvalidDiscountException, InvalidCapacityException {

        ServiceMain s = getService.get(name.toUpperCase());
        if (s != null) throw new ServiceAlreadyExistsException(s.getName());
        if(isValidPrice(price)) throw new InvalidPriceException(t);
        if(isNotValidDiscount(t, val)) throw new InvalidDiscountException();
        if(isNotValidCapacity(t, val)) throw new InvalidCapacityException();

        switch (t){
            case LODGING ->s = new LodgingService(price,name,longitude,latitude, val);
            case LEISURE -> s = new LeisureService(latitude, longitude, name, price, val);
            default ->  s = new EatingService(price,name,longitude,latitude, val);
        }

        servicesByStar[3].addFirst(s);
        servicesByStarAndType[t.ordinal()][3].addLast(s);
        getService.put(s.getName().toUpperCase(),s);
        allServiceToList.addLast(s);
        ServiceMain old = lessExpensiveServices[ServiceType.valueOf(s.getType()).ordinal()];
        if(old == null || s.getPrice() < old.getPrice()) lessExpensiveServices[ServiceType.valueOf(s.getType()).ordinal()] = s;

    }

    @Override
    public boolean hasServicesByType(ServiceType serviceT) {
        return lessExpensiveServices[serviceT.ordinal()] != null;
    }

    @Override
    public boolean hasServicesByStarAndType(ServiceType type, int star) {
        return !servicesByStarAndType[type.ordinal()][star-1].isEmpty();
    }


    private boolean isValidStar(int n){
        return n >= 1 && n<=5;
    }
    @Override
    public void actualizeRanking(String service, int stars, String description) throws IsNotValidStarException, ServiceDoesNotExistExeption{
        if(!isValidStar(stars)) throw new IsNotValidStarException();
        ServiceMain s = getServiceByName(service);

        int oldAverage = s.getEvaluationAverage();
        Evaluation e = new EvaluationClass(description,stars);
        ((Service)s).addRating(e);
        ServiceType t = ServiceType.valueOf(s.getType());
        int newAverage = s.getEvaluationAverage();
        int oldIndexByStar = servicesByStar[oldAverage-1].indexOf(s);
        int oldIndexByStarAndType = servicesByStarAndType[t.ordinal()][oldAverage-1].indexOf(s);

        if(newAverage != oldAverage) {
            servicesByStar[oldAverage - 1].remove(oldIndexByStar);
            servicesByStar[newAverage - 1].addFirst(s);
            servicesByStarAndType[t.ordinal()][oldAverage-1].remove(oldIndexByStarAndType);
            servicesByStarAndType[t.ordinal()][newAverage-1].addLast(s);

        }
    }
    @Override
    public Iterator<ServiceMain> allServicesIterator() throws NoServicesException {

        Iterator<ServiceMain> it = allServiceToList.iterator();

        if(!it.hasNext()){
            throw new NoServicesException();
        }
        return it;
    }

    @Override
    public ServiceMain getServiceByName(String name) throws ServiceDoesNotExistExeption{
       ServiceMain s = getService.get(name.toUpperCase());
       if(s == null) throw new ServiceDoesNotExistExeption(name);
       return s;
    }


    @Override
    public Iterator<ServiceMain> getServicesByStar() throws NoServicesException {

        List<ServiceMain> copy = new DoublyLinkedList<>();

        for (int i = 0; i < 5; i++) {

            TwoWayList<ServiceMain>  study = servicesByStar[i];
            TwoWayIterator<ServiceMain> it = study.twoWayiterator();

                while (it.hasNext()){
                    copy.addFirst(it.next());
                }

        }

        if(copy.isEmpty()) throw new NoServicesException();
        return copy.iterator();
    }


    @Override
    public Iterator<ServiceMain> getServicesByStarAndType(ServiceMain studentAt, ServiceType sType, int average) {

        long studentLat = studentAt.getLatitude();
        long studentLong = studentAt.getLongitude();
        List<ServiceMain> old = servicesByStarAndType[sType.ordinal()][average-1];
        List<ServiceMain> copy = new DoublyLinkedList<>();


        ServiceMain temp = old.get(0);
        long minDistance = getManhattanDistance(studentLat,temp.getLatitude(), studentLong, temp.getLongitude());
        copy.addLast(temp);

        for (int i = 1; i < old.size(); i++) {

            ServiceMain study = old.get(i);
            long distance = getManhattanDistance(studentLat, study.getLatitude(), studentLong, study.getLongitude());

            if (distance < minDistance) {

                    copy = new DoublyLinkedList<>();
                    copy.addLast(study);
                    minDistance = distance;


            }else if(distance == minDistance){
            copy.addLast(study);
            }
        }

        return copy.iterator();
    }


    @Override
    public ServiceMain getBestAverage(ServiceType t) throws NoServicesException{

        for(int i = 4; i > 0; i--) {
            if (!servicesByStarAndType[t.ordinal()][i].isEmpty()) {
                return servicesByStarAndType[t.ordinal()][i].getFirst();
            }
        }
    throw  new NoServicesException();

    }

    @Override
    public ServiceMain getLeastExpensive(ServiceType t) throws NoServicesException{

        ServiceMain s = lessExpensiveServices[t.ordinal()];

        if(s == null){
            throw new NoServicesException();
        }
        return lessExpensiveServices[t.ordinal()];
    }

    private long getManhattanDistance(long lat1, long lat2, long long1, long long2){
        return Math.abs((lat1 - lat2)) + Math.abs((long1 - long2));
    }

}

