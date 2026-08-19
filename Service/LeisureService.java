package Service;

import Enum.ServiceType;
import dataStructures.TwoWayIterator;
import Student.StudentMain;

import java.io.Serializable;

public class LeisureService extends ServiceClass implements Serializable {
    private static final long serialVersionUID = 0L;
    private final double ticket;
    public LeisureService(long latitude, long longitude, String name, int ticket, int discount) {
        super(latitude, longitude, name);
        this.ticket = (double) (ticket * (100 - discount)) / (100);
    }


    @Override
    public String getType() {
        return ServiceType.LEISURE.name();
    }

    @Override
    public double getPrice() {
        return ticket;
    }



    @Override
    public TwoWayIterator<StudentMain> getStudentsIteratorByOrder() {
        return null;
    }


}
