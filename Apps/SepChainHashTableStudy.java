package Apps;

import Exceptions.NoStudentsCExeption;
import Student.StudentMain;
import dataStructures.Iterator;

import java.io.Serializable;

public interface SepChainHashTableStudy extends Serializable {
    void put(StudentMain s);
    Iterator<StudentMain> iterator(String c) throws NoStudentsCExeption;
    void remove(StudentMain s);
}
