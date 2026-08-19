package Exceptions;
import Enum.ServiceType;

public class InvalidPriceException extends Exception {
    private final ServiceType type;
    public InvalidPriceException(ServiceType type) { this.type = type; }
    public ServiceType getServiceType() { return type; }
}
