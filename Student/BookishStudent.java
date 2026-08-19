package Student;


import Enum.ServiceType;
import Enum.StudentType;
import Service.ServiceMain;
import java.io.Serializable;

public class BookishStudent extends OtherStudentsClass implements Serializable {
    private static final long serialVersionUID = 0L;
    public BookishStudent(String name, ServiceMain home, String country){
        super(name,home, country);
    }


    public String getType() {
        return StudentType.BOOKISH.name().toLowerCase();
    }

    public boolean setLocationByGo(ServiceMain newLocation){
        this.location = newLocation;
        if(newLocation.getType().equals(ServiceType.LEISURE.name()) && visitedLoc.indexOf(newLocation) == -1){
            visitedLoc.addLast(newLocation);
        }
        return false;
    }
    }
