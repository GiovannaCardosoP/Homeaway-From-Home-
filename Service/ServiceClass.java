package Service;

import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.ListInArray;

import java.io.Serializable;

public abstract class ServiceClass implements Service, Serializable {

    private final long latitude;
    private final long longitude;
    private final String name;
    private List<Evaluation> Evaluations;



    public ServiceClass(long  latitude, long longitude, String name){
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        Evaluations = new ListInArray<>(100);
        Evaluation e = new EvaluationClass(null,4);
        Evaluations.addLast(e);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getLatitude(){
        return latitude;
    }

    @Override
    public long getLongitude(){
        return longitude;
    }

    @Override
    public void addRating(Evaluation e){
        Evaluations.addLast(e);
    }

    @Override
    public int getEvaluationAverage(){
        float sum = 0;

        Iterator<Evaluation> it = Evaluations.iterator();
         while(it.hasNext()){
             sum += it.next().getRank();
         }

        return Math.round(sum/ Evaluations.size());
    }

    @Override
    public boolean hasTag(String tag){
        boolean found = false;

        Iterator<Evaluation> it = Evaluations.iterator();

        while(it.hasNext() && !found){
            Evaluation e = it.next();
            if(e.hasTag(tag)) found = true;
        }
        return found;
    }


}
