package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class TwoWayDoublyIterator<E> extends DoublyIterator<E>
        implements TwoWayIterator<E> {

    /**
     * Node with the first element in the iteration.
     */
    private DoublyListNode<E> lastNode;
    /**
     * Node with the previous element in the iteration.
     */
    private DoublyListNode<E> prevToReturn;

    /**
     * DoublyLLIterator constructor
     *
     * @param first - Node with the first element of the iteration
     * @param last  - Node with the last element of the iteration
     */
    public TwoWayDoublyIterator(DoublyListNode<E> first, DoublyListNode<E> last) {
        super(first);
        //TODO: Left as an exercise.
        lastNode = last;
    }

    //All methods are O(1)


    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements in the reverse direction
     */
    public boolean hasPrevious( ) {
        //TODO: Left as an exercise.
        return (prevToReturn != null);
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ) throws NoSuchElementException{   // HEAD TO TAIL
        //TODO: Left as an exercise.
        if (nextToReturn == null) throw new NoSuchElementException();
        prevToReturn = nextToReturn;
        E current = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return current;
    }

    /**
     * Returns the previous element in the iteration.
     * @return previous element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E previous( ) { // TAIL TO HEAD
        //TODO: Left as an exercise.
        if (prevToReturn == null) throw new NoSuchElementException();
        E current = prevToReturn.getElement();
        nextToReturn = prevToReturn;
        // tem que ser depois de nextToReturn = prevToReturn;
        // dado que nextToreturn é o ATUAL, portanto so se faz a atualizaçao por ultimo
        prevToReturn = prevToReturn.getPrevious();  //avançar para tras (head)

        return current;
    }

    /**
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element
     */

    public void fullForward() { // ITERAÇÃO DE TAIL TO HEAD
        //TODO: Left as an exercise.
        prevToReturn = lastNode;
        nextToReturn = null; //nucna há nada para além do tail
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        //TODO: Left as an exercise.
        super.rewind();
    }

}
