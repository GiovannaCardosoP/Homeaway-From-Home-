package Service;
import Enum.ServiceType;

import java.io.Serializable;

public class LodgingService extends OtherServices implements Serializable {
    private static final long serialVersionUID = 0L;
    private final int roomPrice;

    public LodgingService(int roomPrice, String name, long longitude, long latitude, int capacity) {
        super(latitude, longitude, name, capacity);
        this.roomPrice= roomPrice;

    }

    public String getType() {
        return ServiceType.LODGING.name();
    }
    public double getPrice() {
        return roomPrice;
    }
}
