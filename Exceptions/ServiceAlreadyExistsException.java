package Exceptions;

public class ServiceAlreadyExistsException extends Exception {
    private String name;
    public ServiceAlreadyExistsException(String name) { super();
    this.name = name;}

    public String getName() {
        return name;
    }
}
