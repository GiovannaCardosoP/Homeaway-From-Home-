package Student;


import Service.ServiceMain;

import java.io.Serializable;

public interface Student extends Serializable, StudentMain{

    /**
     * executes command go
     * @param newLocation
     * @return ture if is a thrifty student and new location is less expensive
     */
    abstract public boolean setLocationByGo(ServiceMain newLocation);


    void setHome(ServiceMain home);
}
