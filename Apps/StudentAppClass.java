package Apps;

import Exceptions.*;
import Service.ServiceMain;
import Student.*;
import dataStructures.*;
import Enum.StudentType;

public class StudentAppClass implements StudentApp {
    private static final long serialVersionUID = 0L;
    private static final String ALL = "all";
    private static final int ZERO = 0;
    // An extra list of students exclusively to maintain the logic of
    // reconstructing the maps in the serializedMaps method
    private List<StudentMain> allStudents;
    private transient Map<String,StudentMain> getStudent;
    private transient SortedMap<String,StudentMain> allStudentsToLIst;
    private transient Map<String, List<StudentMain>> studentsByCountry;

    public StudentAppClass() {
        allStudents = new SinglyLinkedList<>();
        getStudent = new SepChainHashTable<>();
        allStudentsToLIst = new AVLSortedMap<>(); // avl
        studentsByCountry = new SepChainHashTable<>();
    }

    @Override
    public void serializedMaps(){
        getStudent = new SepChainHashTable<>();
        allStudentsToLIst = new AVLSortedMap<>();
        studentsByCountry = new SepChainHashTable<>();

        Iterator<StudentMain> it = allStudents.iterator();
        while(it.hasNext()){
            StudentMain s = it.next();
            getStudent.put(s.getName().toUpperCase(), s);
            allStudentsToLIst.put(s.getName().toUpperCase(), s);
            String country = s.getCountry();
            List<StudentMain> newList = studentsByCountry.get(country.toUpperCase());
            if(newList == null){
                newList = new DoublyLinkedList<>();
                studentsByCountry.put(country.toUpperCase(),newList);
            }
            newList.addLast(s);
        }
    }

    @Override
    public StudentMain addNewStudent(String name, StudentType type, String country, ServiceMain home) throws StudentNameEException{
        StudentMain s = getStudent.get(name.toUpperCase());
        if(s != null) throw new StudentNameEException(s.getName());
        switch (type) {
            case BOOKISH -> {
                s = new BookishStudent(name, home, country);
            }
            case OUTGOING -> {
                s = new OutgoingStudent(name, home, country);
                ((OutgoingStudent) s).addLocation(home);
            }
            default ->
                    s = new ThriftyStudent(name, home, country);
        }
        allStudents.addLast(s);
        getStudent.put(name.toUpperCase(),s);
        allStudentsToLIst.put(name.toUpperCase(),s);
        String key = country.toUpperCase();
        List<StudentMain> list = studentsByCountry.get(key);
        if(list == null){
            list = new DoublyLinkedList<>();
            studentsByCountry.put(key,list);
        }
        list.addLast(s);
        return s;
    }

    @Override
    public Iterator<StudentMain> getStudentsIterator(String command) throws NoStudentsExeption, NoStudentsCExeption {
        Iterator<StudentMain> it;
        if(command.equalsIgnoreCase(ALL)){
            it= allStudentsToLIst.values();
            if(!it.hasNext()) throw new NoStudentsExeption();
        } else{
            List<StudentMain> list = studentsByCountry.get(command.toUpperCase());
            if(list == null) throw new NoStudentsCExeption();
            it = list.iterator();
        }
        return it;
    }

    @Override
    public void removeStudent(String name) throws StudentDoesNotExistException {
        StudentMain s =getStudent.get(name.toUpperCase());
        if(s == null) throw new StudentDoesNotExistException(name);

        allStudents.remove(allStudents.indexOf(s));
        getStudent.remove(name.toUpperCase());
        allStudentsToLIst.remove(name.toUpperCase());
        String key = s.getCountry().toUpperCase();
        List<StudentMain> list = studentsByCountry.get(key);

        if (list != null) {
            int idx = list.indexOf(s);
            if (idx != -1) {
                list.remove(idx);
                if(list.isEmpty()){
                    studentsByCountry.remove(key);
                }
            }
        }
    }

    @Override
    public Iterator<ServiceMain> getVisitedLocation(String name) throws StudentDoesNotExistException, IsThriftyException, HasNotVisitedLocationsException {
        StudentMain s = getStudent.get(name.toUpperCase());
        if(s == null) throw new StudentDoesNotExistException(name);
        if(s instanceof ThriftyStudent) throw new IsThriftyException(s.getName());
        if(hasVisitedLocations(s)) throw new HasNotVisitedLocationsException(s.getName());
        return s.getVisitedLocations();
    }

    private boolean hasVisitedLocations(StudentMain s) {
        return s.getSize() == ZERO;
    }

    @Override
    public ServiceMain moveStudent(StudentMain student, ServiceMain newHome) throws  MoveIsNotAcceptable  {
        if(isNotValidThrifty(student, newHome)) throw new MoveIsNotAcceptable();
        ServiceMain oldHome = student.getHome();
        ((Student)student).setHome(newHome);
        ((Student)student).setLocationByGo(newHome);
        if(student instanceof OutgoingStudent){
            ((OutgoingStudent) student).addLocation(newHome);
        }
        return oldHome;
    }


    private boolean isNotValidThrifty(StudentMain s, ServiceMain newHome) {
        return StudentType.THRIFTY.name().equalsIgnoreCase(s.getType()) && newHome.getPrice() >= s.getHome().getPrice();
    }

    @Override
    public boolean  updateStudentLocationByGO(StudentMain name, ServiceMain e){
        return ((Student)name).setLocationByGo(e);
    }

    @Override
    public StudentMain getStudentByName(String name) throws StudentDoesNotExistException{
       StudentMain s = getStudent.get(name.toUpperCase());
       if(s == null) throw new StudentDoesNotExistException(name);
       return s;
    }
}
