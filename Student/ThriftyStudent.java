package Student;
import Enum.StudentType;
import Service.*;

import java.io.Serializable;

public class ThriftyStudent extends StudentClass implements Serializable {
    private static final long serialVersionUID = 0L;
    private ServiceMain lessExpensiveServiceE;


    public ThriftyStudent(String name, ServiceMain home, String country) {
        super(name, home, country);
        lessExpensiveServiceE = null;

    }


    public String getType() {
        return StudentType.THRIFTY.name().toLowerCase();
    }


    public boolean setLocationByGo(ServiceMain newLocation) {
        this.location = newLocation;


        if (newLocation instanceof EatingService) {

            if (lessExpensiveServiceE == null || lessExpensiveServiceE.getPrice() > newLocation.getPrice()) {
                lessExpensiveServiceE = newLocation;
                return false;
            }

            return lessExpensiveServiceE.getPrice() < newLocation.getPrice();

        } else {
            return false;
        }
    }
}
