package Exceptions;

public class IsThriftyException extends Exception{
    private String name;
    public IsThriftyException(String name){
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
