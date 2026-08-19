package Exceptions;

public class HasNotVisitedLocationsException extends Exception {
    private String name;
    public HasNotVisitedLocationsException(String name){
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
