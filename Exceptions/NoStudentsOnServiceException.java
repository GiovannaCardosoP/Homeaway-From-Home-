package Exceptions;

public class NoStudentsOnServiceException extends Exception {
    private String name;
    public NoStudentsOnServiceException(String name){
        super();
        this.name=name;
    }
    public String getName(){
        return name;
    }
}
