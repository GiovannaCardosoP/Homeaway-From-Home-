package Student;

import Service.ServiceMain;
import dataStructures.Iterator;

import java.io.Serializable;

public abstract class StudentClass implements Student, StudentMain, Serializable {
    private static final long serialVersionUID = 0L;
    private final String name;
    protected ServiceMain location;
    protected ServiceMain home;
    protected final String country;

    public StudentClass(String name, ServiceMain home, String country){
        this.name = name;
        location = home;
        this.home = home;
        this.country = country;

    }
    @Override
    public ServiceMain getLocation() {
        return location;
    }

    @Override
    public ServiceMain getHome() {
        return home;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCountry(){
        return country;
    }
    @Override
    abstract public boolean setLocationByGo(ServiceMain newLocation);

    @Override
    public Iterator<ServiceMain> getVisitedLocations(){
        return null;
    }
    @Override
    public int getSize(){ return 0;}

    @Override
    public void setHome(ServiceMain home) {
        this.home = home;
        location = home;
    }


}
