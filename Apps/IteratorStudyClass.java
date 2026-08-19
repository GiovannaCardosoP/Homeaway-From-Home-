package Apps;

import Student.StudentMain;
import dataStructures.TwoWayIterator;

public class IteratorStudyClass implements IteratorStudy {
    private static final long serialVersionUID = 0L;
    private static final String LESS_THAN = "<";
    private String order;
    private TwoWayIterator<StudentMain> study;

    public IteratorStudyClass(String order, TwoWayIterator<StudentMain> it){
        this.order = order;
        study = it;
        if(order.equalsIgnoreCase(LESS_THAN)) it.fullForward();
        else   it.rewind();
    }

    @Override
    public boolean hasNext(){
        if(order.equalsIgnoreCase(LESS_THAN)) return study.hasPrevious();
        else return study.hasNext();
    }
    @Override
    public StudentMain next(){
        if(order.equalsIgnoreCase(LESS_THAN)) return study.previous();
        else return study.next();
    }
}
