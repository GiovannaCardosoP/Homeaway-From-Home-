package Exceptions;

public class BoundsDoesNotExistException extends Exception {
    private String name;
    public BoundsDoesNotExistException(String name){
        super();
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
