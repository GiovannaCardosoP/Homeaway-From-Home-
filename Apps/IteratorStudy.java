package Apps;

import Student.StudentMain;

import java.io.Serializable;

public interface IteratorStudy extends Serializable {

    boolean hasNext();


    StudentMain next();

}
