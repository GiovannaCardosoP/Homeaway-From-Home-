package Service;

import Exceptions.NoStudentsOnServiceException;
import dataStructures.TwoWayIterator;
import Student.StudentMain;

public interface ServiceMain {

    String getName();
    String getType();
    long getLatitude();
    long getLongitude();
    double getPrice();

    TwoWayIterator<StudentMain> getStudentsIteratorByOrder() throws NoStudentsOnServiceException;
    int getEvaluationAverage();

}
