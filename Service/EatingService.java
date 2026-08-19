package Service;
import Enum.ServiceType;

import java.io.Serializable;

public class EatingService extends OtherServices implements Serializable {
    private static final long serialVersionUID = 0L;
    private final int price;



    public EatingService(int price, String name, long lon, long lat, int capacity){
        super(lat,lon,name, capacity);
        this.price = price;

    }


    public double getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return ServiceType.EATING.name();
    }
}
