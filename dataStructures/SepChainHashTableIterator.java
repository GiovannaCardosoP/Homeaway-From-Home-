package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    //TODO: Left as exercise
    private Map<K,V>[] table;
    int counter;
    private Iterator<Map.Entry<K,V>> it;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        //TODO: Left as exercise
        this.table = table;
        counter = 0;
        it = table[0].iterator();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
	//TODO: Left as exercise
        return it != null && it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        //TODO: Left as exercise
        if(!hasNext()) throw new NoSuchElementException();
        Map.Entry<K,V> result = it.next();
        if (!it.hasNext()) {
            it = table[counter++].iterator();
            next();
        }
        return result;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        //TODO: Left as exercise
        counter = 0;
        it = table[counter].iterator();
    }

}

