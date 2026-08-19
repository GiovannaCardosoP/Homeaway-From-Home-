package Student;

import Service.ServiceMain;
import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.SinglyLinkedList;
import java.io.Serializable;

public abstract class OtherStudentsClass extends StudentClass implements Serializable {

    private static final long serialVersionUID = 0L;
    protected List<ServiceMain> visitedLoc;

        public OtherStudentsClass(String name, ServiceMain home, String country) {
        super(name, home, country);
        visitedLoc = new SinglyLinkedList<>();
    }

    abstract public boolean setLocationByGo(ServiceMain newLocation);

    @Override
    public Iterator<ServiceMain> getVisitedLocations(){
        return visitedLoc.iterator();
    }

    @Override
    public int getSize(){
        return visitedLoc.size();
    }

}
