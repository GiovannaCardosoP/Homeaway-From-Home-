package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {


    //TODO: Left as an exercise.
    private Iterator<Map.Entry<E,?>> it;


    //SEPCHAIN
    //RETORNAR ITERATOR DE MAPAS -> MAP<K,V>[] entry.
    //cada position [i] é um MapSinglyList, com a lista de SinglyList<Entry<K,V>>
    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
        //TODO: Left as an exercise.
        this.it = it;


    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        //TODO: Left as an exercise.
        return it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        //TODO: Left as an exercise.
        if (!it.hasNext()) throw new NoSuchElementException();
        Map.Entry<E,?> entry = it.next();
        return entry.key();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        //TODO: Left as an exercise.
        it.rewind();

    }
}
