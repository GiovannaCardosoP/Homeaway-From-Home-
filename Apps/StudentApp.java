package Apps;

import Exceptions.*;
import Enum.StudentType;
import Service.ServiceMain;
import Student.StudentMain;
import dataStructures.Iterator;

import java.io.Serializable;

public interface StudentApp extends Serializable {

    /**
     * Rebuilds all maps in the student application from the allStudents list
     * This is needed because the maps are transient, so we use the extra list allStudents to rebuild them
     */
    void serializedMaps();

    /**
     * Creates a new student of the given type and adds it to the system
     * @param name     the student's name
     * @param type     the type of student
     * @param home the lodging service used as home
     * @param country the country of the student
     * @throws StudentNameEException if a student with the same name already exists
     */
    StudentMain addNewStudent(String name, StudentType type, String country, ServiceMain home) throws StudentNameEException;

    Iterator<StudentMain> getStudentsIterator(String command) throws NoStudentsExeption, NoStudentsCExeption;

    void removeStudent(String name) throws StudentDoesNotExistException;

    Iterator<ServiceMain> getVisitedLocation(String name) throws StudentDoesNotExistException, IsThriftyException, HasNotVisitedLocationsException;

    ServiceMain moveStudent(StudentMain s, ServiceMain newHome) throws MoveIsNotAcceptable;

    boolean  updateStudentLocationByGO(StudentMain student, ServiceMain newLocation);

    StudentMain getStudentByName(String name) throws StudentDoesNotExistException;
}
