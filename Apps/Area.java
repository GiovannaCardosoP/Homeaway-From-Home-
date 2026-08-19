package Apps;

import Enum.ServiceType;
import Enum.StudentType;
import Exceptions.*;
import Service.ServiceMain;
import Student.*;
import dataStructures.Iterator;

import java.io.Serializable;

public interface Area extends Serializable {

    /**
     * Rebuilds all transient maps in this area.
     * Calls the serializedMaps() methods of the studentApp and serviceArea
     * to reconstruct their internal maps from the lists.
     */
    void serializedMaps();

    String getName();

    /**
     * Returns the student with the specified name.
     * @param name student's name
     * @return the student object with the given name, or null if not found
     */
    StudentMain getStudentByName(String name) throws StudentDoesNotExistException;
    /**
     * Creates a new service based on the given type and adds it to the system
     * @param name     the name of the service
     * @param longitude      the longitude of the service
     * @param latitude      the latitude of the service
     * @param price    the price of the service
     * @param val the discount
     * @param t     the type of the service
     * @throws ServiceAlreadyExistsException if a service with the same name already exists
     * @throws InvalidPriceException if the provided price is invalid for the service type
     * @throws InvalidDiscountException if the provided discount value is invalid
     * @throws InvalidLocationException if the location coordinates are invalid
     * @throws InvalidCapacityException if the capacity or value is invalid for the service type
     */

    void newServiceCommand(String name, Long longitude, Long latitude, int price, int val, ServiceType t) throws InvalidLocationException, InvalidPriceException,
            InvalidDiscountException, InvalidCapacityException, ServiceAlreadyExistsException;


    /**
     * Returns an iterator of all services.
     * @return an Iterator for all services
     * @throws NoServicesException if no service of the specified type is available
     */
    Iterator<ServiceMain> allServicesIterator() throws NoServicesException;
    /**
     * Returns an iterator of all students.
     * @param command "all" or by country
     * @return an Iterator for all students
     * @throws NoStudentsExeption  if there are no students in the system
     * @throws NoStudentsCExeption if there are no students from the specified country
     */
    Iterator<StudentMain> studentsIterator(String command) throws NoStudentsExeption, NoStudentsCExeption;

    /**
     * Removes a student by name if they exist.
     * @param name the student's name
     * @return student's name
     *  @throws StudentDoesNotExistException if no student with the given name exists
     */
    String removeStudentIfExists(String name) throws StudentDoesNotExistException;

    /**
     * Returns the service with the specified name and type.
     * @param name the name of the service
     * @return the service object with the given name and type, or null if not found
     */
    ServiceMain getServiceByName(String name) throws ServiceDoesNotExistExeption;

    /**
     * Creates a new student of the given type and adds it to the system
     * @param name     the student's name
     * @param type     the type of student
     * @param homeName the name of the lodging service used as home
     * @param country the country of the student
     * @throws ServiceDoesNotExistExeption if the specified lodging service does not exist
     * @throws LodgingFullException if the lodging service has no available space
     * @throws StudentNameEException if a student with the same name already exists
     */
    void newStudentCommand(String name, StudentType type, String homeName, String country) throws ServiceDoesNotExistExeption, LodgingFullException,StudentNameEException;

    /**
     *  Gets the current student when their location is requested
     * @param name the student's name
     * @return the student
     * @throws ServiceDoesNotExistExeption if the service does not exist
     */
    StudentMain executeWhere(String name) throws StudentDoesNotExistException;

    /**
     * Returns an iterator of all locations visited by the student.
     * @param name the student's name
     * @return an Iterator of visited locations
     * @throws StudentDoesNotExistException if no student with the given name exists
     * @throws StudentDoesNotExistException if no student with the given name exists
     * @throws IsThriftyException if the student is of type THRIFTY and cannot have visited locations
     * @throws HasNotVisitedLocationsException if the student has not visited any locations
     */
    Iterator<ServiceMain> getVisitedLocation(String name)  throws StudentDoesNotExistException, IsThriftyException, HasNotVisitedLocationsException;

    /**
     * Adds a star rating to a service and updates its ranking.
     * @param service the name of the service
     * @param stars   the star rating to add
     * @param description tag
     * @throws IsNotValidStarException if the provided star value is invalid
     * @throws ServiceDoesNotExistExeption if the specified service does not exist
     */
    void rateService(String service, int stars, String description) throws IsNotValidStarException, ServiceDoesNotExistExeption;



    /**
     * Changes a student's home to the specified new lodging service.
     * @param name     the student's name
     * @param newHome  the name of the new home
     * @throws ServiceDoesNotExistExeption if the specified new lodging service does not exist
     * @throws StudentDoesNotExistException if no student with the given name exists
     * @throws ThatIsHomeException if the new lodging is the same as the student's current home
     * @throws LodgingFullException if the new lodging has no available space
     * @throws MoveIsNotAcceptable if the move is not allowed for the student's type
     */
    void executeMoveStudent(String name, String newHome) throws ServiceDoesNotExistExeption,StudentDoesNotExistException, ThatIsHomeException, LodgingFullException,MoveIsNotAcceptable;

    /**
     * Moves a student to the specified location
     * @param name     the student's name
     * @param location the location
     * @return true if the move was successful
     *  @throws StudentDoesNotExistException if no student with the given name exists
     */
    boolean executeGoCommand(String name, String location)throws ServiceDoesNotExistExeption,StudentDoesNotExistException, LocationNotEOrLeiException,AlreadyThereException,ServiceFullException;

    /**
     * Lists all students in a given eating or lodging service in the specified order.
     * @param order the order of listing
     * @param service the name of the service
     * @return an iterator over the students in the service
     *  @throws StudentDoesNotExistException if no student with the given name exists
     */
    IteratorStudy ListStudentsByService(String order, String service)throws OrderDoesNotExistException, ServiceDoesNotExistExeption, ServiceDoesNotControlEntryExitExceptio, NoStudentsOnServiceException;

    /**
     * Returns an iterator of all services sorted by star rating.
     * @return an Iterator of services by star rating
     * @throws NoServicesException if no service of the specified type is available
     */
     Iterator<ServiceMain> getServicesByStar() throws NoServicesException;

    /**
     * Returns an iterator over all services that match the given tag.
     * @param tag the tag
     * @return an iterator over services with the specified tag
     * @throws NoServicesException if no services are found with the given tag
     */
    Iterator<ServiceMain> listServicesByTag(String tag) throws NoServicesException;

    /**
     * Returns an iterator of services filtered by type and average star rating.
     * @param type    the service type as a string
     * @param average the minimum average star rating
     * @throws IsNotValidStarException if the provided star (rating) value is invalid
     * @throws InvalidServiceTypeException if type is invalid
     * @return an Iterator
     */
    Iterator<ServiceMain> getServicesByStarAndType(String student, String type, int average)throws IsNotValidStarException,StudentDoesNotExistException, ServiceDoesNotExistExeption, NoServicesTypeAndStar, InvalidServiceTypeException;

    /**
     * Finds the most relevant service of a given type for a specific student.
     * @param name the name of the student
     * @param serviceType the type of service required
     * @return the most relevant service for the student
     * @throws InvalidServiceTypeException if type is invalid
     * @throws StudentDoesNotExistException if no student with the given name exists
     * @throws NoServicesException if no service of the specified type is available
     */
    ServiceMain executeFind(String name, String serviceType) throws StudentDoesNotExistException, NoServicesException, InvalidServiceTypeException;
}
