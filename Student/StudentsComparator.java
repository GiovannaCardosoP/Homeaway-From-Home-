package Student;

import dataStructures.Comparator;

import java.io.Serializable;

public class StudentsComparator implements Comparator<Student>, Serializable {
    private static final long serialVersionUID = 0L;
    @Override
    public int compare(Student x, Student y) {
        return x.getName().compareTo(y.getName());
    }
}
