package Service;

import Exceptions.NoStudentsOnServiceException;
import Student.StudentMain;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import dataStructures.TwoWayList;

import java.io.Serializable;

public abstract class OtherServices extends ServiceClass implements Serializable {
    private static final long serialVersionUID = 0L;

    private TwoWayList<StudentMain> students;

    protected final int capacity;

    public OtherServices(long  latitude, long longitude, String name, int capacity){
        super(latitude, longitude, name);
        students = new DoublyLinkedList<>();

        this.capacity = capacity;
    }


    public void removeStudent( StudentMain s) {
        int index = students.indexOf(s);
        if(index != -1) {
            students.remove(index);
        }
    }
    public void addStudent (StudentMain s){
        students.addLast(s);
    }

    public boolean isFull() {
        return students.size() == capacity;
    }


    public  TwoWayIterator<StudentMain> getStudentsIteratorByOrder() throws NoStudentsOnServiceException {
        TwoWayIterator<StudentMain> it = students.twoWayiterator();
        if(!it.hasNext()) throw new NoStudentsOnServiceException(this.getName());
        return it;
    }

    @Override
    public double getPrice() {
        return 0;
    }




}
