package Service;


import java.io.Serializable;

public interface Service extends Serializable, ServiceMain {
    void addRating(Evaluation e);
    boolean hasTag(String tag);

}
