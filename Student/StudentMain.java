package Student;

import Service.ServiceMain;
import dataStructures.Iterator;

public interface StudentMain{
    ServiceMain getLocation();
    ServiceMain getHome();
    String getName();
    String getType();
    String getCountry();
    /**
     *
     * @return iterator os visited locations.
     */
    Iterator<ServiceMain> getVisitedLocations();
    /**
     *
     * @return size of the array of visited locations.
     */
    int getSize();
}
