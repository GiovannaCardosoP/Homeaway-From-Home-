package Apps;
import Enum.ServiceType;
import Exceptions.*;
import Service.ServiceMain;
import dataStructures.Iterator;

import java.io.Serializable;

public interface ServiceArea extends Serializable {

    /**
     * Rebuilds the service map from the allServiceToList list.
     * This is needed because the map is transient and not saved directly.
     */
    void serializedMaps();
    void addService(String name, Long longitude, Long latitude, int price, int val, ServiceType t) throws ServiceAlreadyExistsException, InvalidPriceException,
            InvalidDiscountException, InvalidCapacityException;

    boolean hasServicesByType(ServiceType service);

    boolean hasServicesByStarAndType(ServiceType type, int star);

    void actualizeRanking(String service, int stars, String description) throws IsNotValidStarException, ServiceDoesNotExistExeption;
    Iterator<ServiceMain> allServicesIterator() throws NoServicesException;
    ServiceMain getServiceByName(String s) throws ServiceDoesNotExistExeption;

    Iterator<ServiceMain> getServicesByStar() throws NoServicesException;

    Iterator<ServiceMain> getServicesByStarAndType(ServiceMain studentAt, ServiceType sType, int average);

    ServiceMain getLeastExpensive(ServiceType t) throws NoServicesException;

    ServiceMain getBestAverage(ServiceType t) throws NoServicesException;
}
