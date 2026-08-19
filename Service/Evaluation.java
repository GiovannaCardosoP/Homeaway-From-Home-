package Service;

import java.io.Serializable;

public interface Evaluation extends Serializable {
    public int getRank();

    public boolean hasTag(String tag);
}
