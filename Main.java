/**
 *  @author Giovanna Pereira (68014) gec.pereira@campus.fct.unl.pt
 *  @author Elsa Coimbra (67915)  ee.coimbra@campus.fct.unl.pt
 */


import Enum.Commands;
import Enum.ServiceType;
import Enum.StudentType;
import Exceptions.*;
import Student.Student;
import dataStructures.Iterator;
import dataStructures.List;
import Apps.*;
import Service.*;
import Student.*;

import java.io.*;
import java.util.Scanner;

import static Enum.Commands.EXIT;

public class Main {

    public static final String UNDERSCORE = "_";
    public static final String EMPTY_STRING = " ";
    public static final String NOT_DEFINED = "System bounds not defined.";
    public static final String INV_BOUND = "Invalid bounds.";
    public static final String BOUNDED = "%s created.\n";
    public static final String BYE = "Bye!";
    public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
    public static final String LOCATION_INVALID = "Invalid location!\n";
    public static final String NAME_ALREADY_EXISTS = "%s already exists!\n";
    public static final String LODG_FULL = "lodging %s is full!\n";
    public static final String NAME_N_EXISTS = "%s does not exist!\n";
    public static final String INVALID_MENU_PRICE = "Invalid menu price!";
    public static final String INVALID_ROOM_PRICE = "Invalid room price!";
    public static final String INVALID_TICKET = "Invalid ticket price!";
    public static final String INVALID_DISCOUNT = "Invalid discount price!";
    public static final String INVALID_CAP = "Invalid capacity!";
    public static final String ADDED = "%s %s added.\n";
    public static final String STUDENT_ADDED = "%s added.\n";
    public static final String INV_SERV = "Invalid service type!";
    public static final String INV_STU = "Invalid student type!";
    public static final String LEFT = "%s has left.\n";
    public static final String NO_SERVICES = "No services yet!\n";
    public static final String NO_STUDENTS = "No students yet!";
    public static final String NO_STUDENTS_C = "No students from %s!\n";
    public static final String LOD_N_EXISTS = "lodging %s does not exist!\n";
    public static final String STUDENT_LOCATION = "%s is now at %s.\n";
    public static final String DISTRACTED_MSG = "%s is now at %s. %s is distracted!\n";
    public static final String UNKNOWN_LOCATION = "Unknown %s!\n";
    public static final String ALREADY_THERE = "Already there!";
    public static final String WHERE_MSG = "%s is at %s %s (%d, %d).\n";
    public static final String THAT_IS_HOME = "That is %s's home!\n";
    public static final String IS_NOT_VALID_HOME = "Move is not acceptable for %s!\n";
    public static final String IS_VALID_HOME = "lodging %s is now %s's home. %s is at home.\n";
    public static final String IS_THRIFTY = "%s is thrifty!\n";
    public static final String NO_VISITED = "%s has not visited any locations!\n";
    public static final String EATING_FULL = "eating %s is full!\n";
    public static final String EVALUATION_MSG = "Your evaluation has been registered!";
    public static final String INVALID_EVALUATION = "Invalid evaluation!";
    public static final String INVALID_STARS = "Invalid stars!";
    public static final String NO_SER_SYSTEM = "No services in the system.";
    public static final String SERVICES_SORTED = "Services sorted in descending order";
    public static final String NO_SERVICES_AVERAGE = "No %s services with average!\n";
    public static final String NO_SERVICES_TYPE = "No %s services!\n";
    private static final String NOT_VAL_SERV = "%s is not a valid service!\n";
    private static final String BOUNDS_ALREADY_EXIST = "Bounds already exists. Please load it!";
    private static final String SAVE_MSG = "%s saved.\n";
    private static final String FILE = ".ser";
    private static final String BOUNDS_NOT_EXIST = "Bounds %s does not exist!\n";
    private static final String LOADED = "%s loaded.\n";
    private static final String SERVICES_MSG = "%s: %s (%d, %d).\n";
    private static final String STUDENTS_MSG = "%s: %s at %s.\n";
    private static final String VISITED_LOCATION_MSG = "%s\n";
    private static final String USERS_MSG = "%s: %s\n";
    private static final String ORDER_NOT_EXIST = "This order does not exists!";
    private static final String DOES_NOT_CONTROL = "%s does not control student entry and exit!\n";
    private static final String NO_STUDENTS_ON = "No students on %s!\n";
    private static final String RANKING_MSG = "%s: %d\n";
    private static final String TAG_MSG = "%s %s\n";
    private static final String NO_SERVICES_WITH_TAG = "There are no services with this tag!";
    private static final String RANKED_MSG = "%s services closer with %d average\n";
    private static final String RANKED_LIST = "%s\n";


    public static final String HELP_MSG =
            "bounds - Defines the new geographic bounding rectangle\n" +
                    "save - Saves the current geographic bounding rectangle to a text file\n" +
                    "load - Load a geographic bounding rectangle from a text file\n" +
                    "service - Adds a new service to the current geographic bounding rectangle. The service may be eating, lodging or leisure\n" +
                    "services - Displays the list of services in current geographic bounding rectangle, in order of registration\n" +
                    "student - Adds a student to the current geographic bounding rectangle\n" +
                    "students - Lists all the students or those of a given country in the current geographic bounding rectangle, in alphabetical order of the student's name\n" +
                    "leave - Removes a student from the the current geographic bounding rectangle\n" +
                    "go - Changes the location of a student to a leisure service, or eating service\n" +
                    "move - Changes the home of a student\n" +
                    "users - List all students who are in a given service (eating or lodging)\n" +
                    "star - Evaluates a service\n" +
                    "where - Locates a student\n" +
                    "visited - Lists locations visited by one student\n" +
                    "ranking - Lists services ordered by star\n" +
                    "ranked - Lists the service(s) of the indicated type with the given score that are closer to the student location\n" +
                    "tag - Lists all services that have at least one review whose description contains the specified word\n" +
                    "find - Finds the most relevant service of a certain type, for a specific student\n" +
                    "help - Shows the available commands\n" +
                    "exit - Terminates the execution of the program\n";



    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        processCommand(in);
        in.close();
    }



    /**
     * gets the next command to be executed.
     *
     * @param in
     * @return the next Command.
     */
    private static Commands getCommand(Scanner in) {

        try {
            String comm = in.next().trim().toUpperCase();
            return Commands.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Commands.UNKNOWN;
        }

    }

    /**
     * read command and execute.
     *
     * @param in
     */
    private static void processCommand(Scanner in) {

        Area currentArea = null;
        List<Student> currentStudents = null;

        Commands command;

        do {
            command = getCommand(in);

            switch (command) {

                case HELP -> System.out.printf(HELP_MSG);
                case EXIT -> exitCommand(currentArea);
                case UNKNOWN -> System.out.println(UNKNOWN_COMMAND);
                case BOUNDS -> currentArea = executeBounds(in, currentArea);
                case SAVE -> executeSave(currentArea);
                case LOAD -> currentArea = executeLoad(in);
                case SERVICE -> executeCreateService(in, currentArea);
                case SERVICES -> listServices(currentArea);
                case STUDENT -> executeStudent(in, currentArea);
                case LEAVE -> executeLeave(in, currentArea);
                case STUDENTS -> listStudents(currentArea, in);
                case WHERE -> executeWhere(currentArea, in);
                case GO -> executeGo(currentArea, in);
                case MOVE -> executeMove(currentArea, in);
                case USERS -> executeUsers(currentArea, in);
                case VISITED -> listLocations(currentArea, in);
                case STAR -> executeStar(currentArea, in);
                case RANKING -> executeRanking(currentArea);
                case TAG -> executeTag(currentArea, in);
                case RANKED -> executeRanked(currentArea, in);
                case FIND -> executeFind(currentArea, in);
            }
        } while (!command.equals(EXIT));
    }
    private static void exitCommand(Area a) {
        if (a != null) writeAreaToFile(a);
        System.out.println(BYE);
    }

    /**
     * executes bounds and returns the app class created.
     *
     * @param in
     * @return Appclass.
     */
    private static Area executeBounds(Scanner in, Area currentArea) {
        try {
            long latLeft = in.nextLong();
            long logLeft = in.nextLong();
            long latRight = in.nextLong();
            long logRight = in.nextLong();
            String name = in.nextLine().trim();
            if (latLeft <= latRight || logRight <= logLeft) throw new BoundsNotDefinedException();
            if (boundsExist(name)) throw new BoundsAlreadyExistException();
            if(currentArea != null) writeAreaToFile(currentArea);
            Area area = new AreaClass(name, latLeft, logLeft, latRight, logRight);
            writeAreaToFile(area);
            System.out.printf(BOUNDED, name);
            return area;
        } catch (BoundsNotDefinedException e) {
            System.out.println(INV_BOUND);
            return currentArea;
        } catch (BoundsAlreadyExistException e) {
            System.out.println(BOUNDS_ALREADY_EXIST);
            return currentArea;
        }

    }


    private static boolean boundsExist(String areaName) {
        File file = new File( areaName.replace(EMPTY_STRING, UNDERSCORE).toLowerCase() + FILE);
        return file.isFile();
    }

    private static void executeSave(Area area) {
        try {
            if (area == null) throw new BoundsNotDefinedException();
            writeAreaToFile(area);
            System.out.printf(SAVE_MSG, area.getName());
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }

    }

    private static void writeAreaToFile(Area area) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( area.getName().replace(EMPTY_STRING, UNDERSCORE).toLowerCase() + FILE))) {
            oos.writeObject(area);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AreaClass executeLoad(Scanner in) {
        try {
            String name = in.nextLine().trim();
            if (!boundsExist(name)) throw new BoundsDoesNotExistException(name);
            return auxLoad(name);

        } catch (BoundsDoesNotExistException e) {
            System.out.printf(BOUNDS_NOT_EXIST, e.getName());
            return null;
        }


    }


    private static AreaClass auxLoad(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name.replace(EMPTY_STRING, UNDERSCORE).toLowerCase() + FILE))) {
            AreaClass loaded = (AreaClass) ois.readObject();
            System.out.printf(LOADED, loaded.getName());
            ois.close();
            loaded.serializedMaps();
            return loaded;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void executeCreateService(Scanner in, Area a) {
        try {
            String type = in.next();
            long latitude = in.nextLong();
            long longitude = in.nextLong();
            int price = in.nextInt();
            int val = in.nextInt();
            String name = in.nextLine().trim();
            if (a == null) throw new BoundsNotDefinedException();
            executeService(a, name, longitude, latitude, price, val, type);
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }
    }


    private static void executeService(Area area, String name, Long longitude, Long latitude, int price, int val, String type) {
        ServiceType t = null;
        try {
            t = getTypeS(type);
            area.newServiceCommand(name, longitude, latitude, price, val, t);
            System.out.printf(ADDED, type.toLowerCase(), name);
        } catch (InvalidServiceTypeException e) {
            System.out.println(INV_SERV);
        } catch (InvalidPriceException e) {
            switch (e.getServiceType()) {
                case EATING -> System.out.println(INVALID_MENU_PRICE);
                case LODGING -> System.out.println(INVALID_ROOM_PRICE);
                case LEISURE -> System.out.println(INVALID_TICKET);
            }
        } catch (InvalidDiscountException e) {
            System.out.println(INVALID_DISCOUNT);
        } catch (InvalidLocationException e) {
            System.out.printf(LOCATION_INVALID, name);
        } catch (InvalidCapacityException e) {
            System.out.println(INVALID_CAP);
        } catch (ServiceAlreadyExistsException e) {
            System.out.printf(NAME_ALREADY_EXISTS, e.getName());
        }
    }

    private static ServiceType getTypeS(String type) throws InvalidServiceTypeException {
        try {
            return ServiceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceTypeException();
        }
    }


    private static void listServices(Area a) {
        try {
            if (a == null) throw new BoundsNotDefinedException();
            Iterator<ServiceMain> it = a.allServicesIterator();
            while (it.hasNext()) {
                ServiceMain s = it.next();
                System.out.printf(SERVICES_MSG, s.getName(), s.getType().toLowerCase(), s.getLatitude(), s.getLongitude());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (NoServicesException e) {
            System.out.printf(NO_SERVICES);
        }
    }


    private static void executeStudent(Scanner in, Area a) {
        String type = in.nextLine().trim().toUpperCase();
        String name = in.nextLine();
        String country = in.nextLine().trim();
        String homeName = in.nextLine();
        try {
            if (a == null) throw new BoundsNotDefinedException();
            getSType(type);
            a.newStudentCommand(name, StudentType.valueOf(type), homeName, country);
            System.out.printf(STUDENT_ADDED, name);
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (ServiceDoesNotExistExeption e) {
            System.out.printf(LOD_N_EXISTS, homeName);
        } catch (LodgingFullException e) {
            System.out.printf(LODG_FULL, homeName);
        } catch (StudentNameEException e) {
            System.out.printf(NAME_ALREADY_EXISTS, e.getName());
        } catch (InvalidStudentTypeException e) {
            System.out.println(INV_STU);
        }
    }


    private static StudentType getSType(String type) throws InvalidStudentTypeException {
        try {
            return StudentType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStudentTypeException();
        }
    }

    /**
     * removes a student.
     *
     * @param in
     * @param area
     */
    private static void executeLeave(Scanner in, Area area) {
        try {
            String name = in.nextLine().trim();
            if (area == null) throw new BoundsNotDefinedException();
            System.out.printf(LEFT, area.removeStudentIfExists(name));
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(NAME_N_EXISTS, e.getName());
        }

    }


    private static void listStudents(Area area, Scanner in) {
        String c = in.nextLine().trim();

        try {
            if (area == null) throw new BoundsNotDefinedException();
            Iterator<StudentMain> it = area.studentsIterator(c);
            while (it.hasNext()) {
                StudentMain s = it.next();
                System.out.printf(STUDENTS_MSG, s.getName(), s.getType().toLowerCase(), s.getLocation().getName());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (NoStudentsExeption e) {
            System.out.println(NO_STUDENTS);
        } catch (NoStudentsCExeption e) {
            System.out.printf(NO_STUDENTS_C, c);
        }
    }

    private static void executeWhere(Area area, Scanner in) {
        String name = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            StudentMain s = area.executeWhere(name);
            ServiceMain location = s.getLocation();
            System.out.printf(WHERE_MSG, s.getName(), location.getName(), location.getType().toLowerCase(), location.getLatitude(), location.getLongitude());
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(NAME_N_EXISTS, e.getName());
        }
    }


    private static void executeGo(Area a, Scanner in){
        String nameS = in.nextLine().trim();
        String newLoc = in.nextLine().trim();
        try{
            if( a == null) throw new BoundsNotDefinedException();
            if (a.executeGoCommand(nameS, newLoc)) {
                System.out.printf(DISTRACTED_MSG, a.getStudentByName(nameS).getName(), a.getServiceByName(newLoc).getName(), a.getStudentByName(nameS).getName() );
            } else {
                System.out.printf(STUDENT_LOCATION, a.getStudentByName(nameS).getName(), a.getServiceByName(newLoc).getName());
            }
        }catch(ServiceDoesNotExistExeption e){
            System.out.printf(UNKNOWN_LOCATION, e.getName());
        }catch(StudentDoesNotExistException e){
            System.out.printf(NAME_N_EXISTS, nameS);
        }catch(LocationNotEOrLeiException e){
            System.out.printf(NOT_VAL_SERV, newLoc);
        }catch(AlreadyThereException e){
            System.out.println(ALREADY_THERE);
        }catch(ServiceFullException e){
            System.out.printf(EATING_FULL,e.getName());
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }

    }



    private static void executeMove(Area area, Scanner in) {
        String name = in.nextLine().trim();
        String newHome = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            area.executeMoveStudent(name, newHome);
            String home = area.getServiceByName(newHome).getName();

            String student = area.getStudentByName(name).getName();
            System.out.printf(IS_VALID_HOME, home, student, student);
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (ServiceDoesNotExistExeption e) {
            System.out.printf(LOD_N_EXISTS, newHome);
        } catch (StudentDoesNotExistException e){
            System.out.printf(NAME_N_EXISTS, e.getName());
        } catch (ThatIsHomeException e) {
            System.out.printf(THAT_IS_HOME, e.getName());
        }catch (LodgingFullException e) {
            System.out.printf(LODG_FULL, newHome);
        } catch (MoveIsNotAcceptable e) {
            System.out.printf(IS_NOT_VALID_HOME, name);
        }
    }
    private static void listLocations(Area area, Scanner in) {
        String name = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            Iterator<ServiceMain> it = area.getVisitedLocation(name);
            while (it.hasNext()) {
                ServiceMain s = it.next();
                System.out.printf(VISITED_LOCATION_MSG, s.getName());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }  catch (StudentDoesNotExistException e) {
            System.out.printf(NAME_N_EXISTS, e.getName());
        } catch (IsThriftyException e) {
            System.out.printf(IS_THRIFTY, e.getName());
        } catch(HasNotVisitedLocationsException e) {
            System.out.printf(NO_VISITED, e.getName());
        }
    }



    private static void executeUsers(Area currentArea, Scanner in) {
        String order = in.next();
        String service = in.nextLine().trim();
        try {
            if (currentArea == null) throw new BoundsNotDefinedException();
            IteratorStudy it = currentArea.ListStudentsByService(order, service);
            while (it.hasNext()) {
                StudentMain s = it.next();
                System.out.printf(USERS_MSG, s.getName(), s.getType().toLowerCase());
            }
        }catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (OrderDoesNotExistException e ){
            System.out.println(ORDER_NOT_EXIST);
        } catch (ServiceDoesNotExistExeption e){
            System.out.printf(NAME_N_EXISTS,service);
        } catch (ServiceDoesNotControlEntryExitExceptio e){
            System.out.printf(DOES_NOT_CONTROL, e.getName());
        } catch(NoStudentsOnServiceException e){
            System.out.printf(NO_STUDENTS_ON, e.getName());
        }
    }



    private static void executeStar(Area area, Scanner in) {
        int stars = in.nextInt();
        String location = in.nextLine().trim();
        String description = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            area.rateService(location, stars, description);
            System.out.println(EVALUATION_MSG);
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (ServiceDoesNotExistExeption e) {
            System.out.printf(NAME_N_EXISTS, location);
        } catch (IsNotValidStarException e) {
            System.out.println(INVALID_EVALUATION);
        }
    }


    private static void executeRanking(Area area) {
        try {
            if (area == null) throw new BoundsNotDefinedException();
            Iterator<ServiceMain> it = area.getServicesByStar();
            System.out.println(SERVICES_SORTED);
            while (it.hasNext()) {
                ServiceMain s = it.next();
                System.out.printf(RANKING_MSG, s.getName(), s.getEvaluationAverage());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch (NoServicesException e) {
            System.out.printf(NO_SER_SYSTEM);
        }
    }


    private static void executeTag(Area area, Scanner in){
        String tag = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            Iterator<ServiceMain> it = area.listServicesByTag(tag);
            while (it.hasNext()) {
                ServiceMain s = it.next();
                System.out.printf(TAG_MSG, s.getType().toLowerCase(), s.getName());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }catch (NoServicesException e) {
            System.out.println(NO_SERVICES_WITH_TAG);
        }
    }



    private static void executeRanked(Area area, Scanner in) {
        String type = in.next().trim();
        int average = in.nextInt();
        String student = in.nextLine().trim();
        try {
            if (area == null) throw new BoundsNotDefinedException();
            Iterator<ServiceMain> it = area.getServicesByStarAndType(student, type, average);
            System.out.printf(RANKED_MSG,type ,average);
            while (it.hasNext()) {
                ServiceMain s = it.next();
                System.out.printf(RANKED_LIST, s.getName());
            }
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        } catch(StudentDoesNotExistException e){
            System.out.printf(NAME_N_EXISTS,student);
        } catch (InvalidServiceTypeException e) {
            System.out.println(INV_SERV);
        } catch (IsNotValidStarException e) {
            System.out.println(INVALID_STARS);
        } catch (ServiceDoesNotExistExeption e) {
            System.out.printf(NO_SERVICES_TYPE, type);
        } catch (NoServicesTypeAndStar e){
            System.out.printf(NO_SERVICES_AVERAGE, type);
        }

    }

    private static void executeFind(Area a, Scanner in) {
        String name = in.nextLine().trim();
        String serviceType = in.nextLine().trim();
        try {
            if (a == null) throw new BoundsNotDefinedException();
            ServiceType t = getTypeS(serviceType);
            ServiceMain s = a.executeFind(name,serviceType);
            System.out.println(s.getName());
        } catch (BoundsNotDefinedException e) {
            System.out.println(NOT_DEFINED);
        }catch(InvalidServiceTypeException e){
            System.out.println(INV_SERV);
        }catch(StudentDoesNotExistException e){
            System.out.printf(NAME_N_EXISTS, name);
        }catch(NoServicesException e){
            System.out.printf(NO_SERVICES_TYPE, serviceType);
        }
    }
}
