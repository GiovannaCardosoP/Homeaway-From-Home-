package Student;

import Enum.StudentType;
import Service.ServiceMain;

import java.io.Serializable;

public class OutgoingStudent extends OtherStudentsClass implements Serializable {

    private static final long serialVersionUID = 0L;

    public OutgoingStudent(String name, ServiceMain home, String country){
        super(name,home, country);
    }

    public boolean setLocationByGo(ServiceMain newLocation){
        this.location = newLocation;
        if(visitedLoc.indexOf(newLocation) == -1){
            visitedLoc.addLast(newLocation);
        }
        return false;
    }

    public void addLocation(ServiceMain newLocation){
        if(visitedLoc.indexOf(newLocation) == -1){
            visitedLoc.addLast(newLocation);
        }
    }

    public String getType() {
        return StudentType.OUTGOING.name();
    }
}
