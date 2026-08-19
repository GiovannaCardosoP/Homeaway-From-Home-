package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    private Iterator<E> iterator;

    /**
     *  Filter.
     */
    private Predicate<E> filter;

    /**
     * Node with the next element in the iteration.
     */
    private E nextToReturn;

    /**
     *
     * @param list to be iterated
     * @param filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> filter) {
        //TODO: Left as an exercise.
        iterator = list;
        this.filter = filter;
        advanceNext();
    }

    /**
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     * Complexity: O(1)
     */
    public boolean hasNext() {
        //TODO: Left as an exercise.
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     * Complexity: O(n)
     */
    public E next() throws NoSuchElementException{
        //TODO: Left as an exercise.
        if (nextToReturn == null) throw new NoSuchElementException();
         E current = nextToReturn; //salvo antes de avançar
         advanceNext();
         return current;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * Complexity: O(n)
     */
    public void rewind() {
        //TODO: Left as an exercise.
        iterator.rewind();
        advanceNext();
    }

    private void advanceNext(){ // ATUALIZA NEXTNODE PARA O NODE COM FILTER
        nextToReturn = null; //não encontrado e para o hasNext() sair do loop infinito
       while (iterator.hasNext() && nextToReturn == null){ //nao entra mais se tiver encontrado
           E found = iterator.next();
           if(filter.check(found)){
               nextToReturn = found; //só muda o estaod caso encontre
           }
       }

    }

}
