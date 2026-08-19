package Apps;

import Enum.ServiceType;
import Enum.StudentType;
import Exceptions.*;
import Student.*;
import dataStructures.*;
import Service.*;

public class AreaClass implements Area {

    private static final long serialVersionUID = 0L;
    private static final String LT = "<";
    private static final String GT = ">";
    private static final int VALIDLESSSTAR = 1;
    private static final int VALIDTOPSTAR = 5;
    private final String name;
    private final long latLeft;
    private final long logLeft;
    private final long latRight;
    private final long logRight;
    private ServiceArea service;
    private StudentApp student;


    public AreaClass(String name, long one, long two, long three, long four) {
        this.name = name;
        latLeft = one;
        logLeft = two;
        latRight = three;
        logRight = four;
        service = new ServiceAreaClass();
        student = new StudentAppClass();
    }

    public void serializedMaps(){
        student.serializedMaps();
        service.serializedMaps();
    }


    @Override
    public StudentMain getStudentByName(String name) throws StudentDoesNotExistException {
        return student.getStudentByName(name);
    }

    @Override
    public ServiceMain getServiceByName(String name) throws ServiceDoesNotExistExeption{
        return service.getServiceByName(name);
    }

    @Override
    public String getName() {
        return name;
    }


    private boolean isValidLoc(Long log, Long lat) {
        return  (!(logLeft<=log && log<=logRight && latLeft>=lat && lat>=latRight));
    }
    @Override
    public void newServiceCommand(String name, Long longitude, Long latitude, int price, int val, ServiceType t) throws InvalidLocationException, InvalidPriceException,
            InvalidDiscountException, InvalidCapacityException, ServiceAlreadyExistsException {
        if(isValidLoc(longitude, latitude)) throw new InvalidLocationException();
        service.addService(name,longitude, latitude, price, val, t);
    }

    private String getServiceType(String name) throws ServiceDoesNotExistExeption {
        return service.getServiceByName(name).getType();
    }

    @Override
    public Iterator<ServiceMain> allServicesIterator() throws NoServicesException{
        return service.allServicesIterator();
    }
    private boolean isThereSpace(String nameL) throws ServiceDoesNotExistExeption {
        ServiceMain s = service.getServiceByName(nameL);
        return (s instanceof OtherServices) && (((OtherServices) s ).isFull());
    }

    @Override
    public void newStudentCommand(String name, StudentType type, String homeName, String country) throws ServiceDoesNotExistExeption, LodgingFullException,StudentNameEException{
        if(!getServiceType(homeName).equals(ServiceType.LODGING.name())) throw new ServiceDoesNotExistExeption(homeName);
        if(isThereSpace(homeName)) throw new  LodgingFullException();
        ServiceMain home =  getServiceByName(homeName);
        StudentMain s = student.addNewStudent(name, type, country, home);
        addStudentToOtherServices(s, home);
        }

    private void addStudentToOtherServices(StudentMain s, ServiceMain location){
        if(location instanceof LodgingService && s.getHome().getName().equalsIgnoreCase(location.getName())){
            ((OtherServices) location).addStudent(s);
        }else if (location instanceof EatingService){
            ((OtherServices) location).addStudent(s);
        }
    }

    private void removeStudentToOtherServices(StudentMain s, ServiceMain service, boolean goCommand){
        if((goCommand && service instanceof EatingService) || (!goCommand && service instanceof OtherServices)){
                ((OtherServices) service).removeStudent(s);
        }
    }

    @Override
    public Iterator<StudentMain> studentsIterator(String command) throws NoStudentsExeption, NoStudentsCExeption{
        return student.getStudentsIterator(command);
    }

    @Override
    public String removeStudentIfExists(String name) throws StudentDoesNotExistException {
        StudentMain s = getStudentByName(name);
        student.removeStudent(name);
        removeStudentToOtherServices(s, s.getHome(), false);
        removeStudentToOtherServices(s, s.getLocation(), false);
        return s.getName();
    }


    @Override
    public StudentMain executeWhere(String name) throws StudentDoesNotExistException{
        return getStudentByName(name);
    }

    @Override
    public Iterator<ServiceMain> getVisitedLocation(String name) throws StudentDoesNotExistException, IsThriftyException, HasNotVisitedLocationsException {
        return student.getVisitedLocation(name);
    }

    @Override
    public void rateService(String service, int stars, String description) throws IsNotValidStarException, ServiceDoesNotExistExeption{
        this.service.actualizeRanking(service,stars,description);
    }

    private boolean isServiceHome(StudentMain student, ServiceMain home) {
        return student.getHome().getName().equals(home.getName());
    }

    @Override
    public void executeMoveStudent(String name, String newHomeName) throws ServiceDoesNotExistExeption,StudentDoesNotExistException,
            ThatIsHomeException, LodgingFullException,MoveIsNotAcceptable  {
        StudentMain s = getStudentByName(name);
        ServiceMain newHome = getServiceByName(newHomeName);
        if(isServiceHome(s, newHome)) throw new ThatIsHomeException(s.getName());
        if(isThereSpace(newHomeName)) throw new  LodgingFullException();
        ServiceMain oldHome = student.moveStudent(s,newHome);
        removeStudentToOtherServices(s,oldHome, false);
        addStudentToOtherServices(s,newHome);
    }


    @Override
    public boolean executeGoCommand(String name, String location) throws ServiceDoesNotExistExeption,StudentDoesNotExistException,
            LocationNotEOrLeiException,AlreadyThereException,ServiceFullException{
        StudentMain s = getStudentByName(name);
        ServiceMain e = getServiceByName(location);
        if(getServiceType(location).equalsIgnoreCase(ServiceType.LODGING.name())) throw new LocationNotEOrLeiException();
        if(alreadyThere(name,location))   throw new AlreadyThereException();
        if(isThereSpace(location))  throw new ServiceFullException(getServiceByName(location).getName());
        removeStudentToOtherServices(s, s.getLocation(), true);
        addStudentToOtherServices(s, e);
        return student.updateStudentLocationByGO(s,e);
    }

    private boolean isValidOrder(String order){
        return order.equals(LT) || order.equals(GT);
    }


    @Override
    public IteratorStudy ListStudentsByService(String order, String service) throws OrderDoesNotExistException,
            ServiceDoesNotExistExeption, ServiceDoesNotControlEntryExitExceptio, NoStudentsOnServiceException {

        ServiceMain s = getServiceByName(service);
        if(!isValidOrder(order)) throw new OrderDoesNotExistException();
        if(getServiceType(service).equalsIgnoreCase(ServiceType.LEISURE.name())) throw new ServiceDoesNotControlEntryExitExceptio(s.getName());
        return new IteratorStudyClass(order, s.getStudentsIteratorByOrder());

    }

    private boolean alreadyThere(String nameS, String nameLoc) throws StudentDoesNotExistException {
        return (getStudentByName(nameS).getLocation().getName().equalsIgnoreCase(nameLoc));
    }

    @Override
    public Iterator<ServiceMain> getServicesByStar() throws NoServicesException{
        return service.getServicesByStar();
    }


    @Override
    public Iterator<ServiceMain> listServicesByTag(String tag) throws NoServicesException{
        List<ServiceMain> serviceList = new SinglyLinkedList<>();
        Iterator<ServiceMain> it = service.allServicesIterator();
        while(it.hasNext()){
            ServiceMain s = it.next();
            if(((Service)s).hasTag(tag.toUpperCase())) serviceList.addLast(s);
        }
        if(serviceList.isEmpty()) throw new NoServicesException();
        return serviceList.iterator();
    }


    private boolean isValidStar(int n){
        return n >= VALIDLESSSTAR && n<= VALIDTOPSTAR ;
    }

    @Override
    public Iterator<ServiceMain> getServicesByStarAndType(String student, String t, int average) throws IsNotValidStarException, StudentDoesNotExistException,
            ServiceDoesNotExistExeption,NoServicesTypeAndStar, InvalidServiceTypeException{

        if(!isValidStar(average)) throw new IsNotValidStarException();
        if(getStudentByName(student) == null) throw new StudentDoesNotExistException(student);
        ServiceType type = getTypeS(t);
        if(!service.hasServicesByType(type)) throw new ServiceDoesNotExistExeption(null);
        if(!service.hasServicesByStarAndType(type,average)) throw new NoServicesTypeAndStar();
        ServiceMain loc = this.getStudentByName(student).getLocation();
        return service.getServicesByStarAndType(loc,type, average);
    }


    private ServiceType getTypeS(String type) throws InvalidServiceTypeException {
        try {
            return ServiceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceTypeException();
        }
    }

    @Override
    public ServiceMain executeFind(String name, String serviceType) throws StudentDoesNotExistException, NoServicesException, InvalidServiceTypeException {
        StudentMain s = getStudentByName(name);
        ServiceType t = getTypeS(serviceType);
        ServiceMain targetService;
        if(s instanceof ThriftyStudent)targetService = service.getLeastExpensive(t);
        else targetService = service.getBestAverage(t);
        return targetService;
    }
}