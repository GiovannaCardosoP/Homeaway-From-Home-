package dataStructures;

import dataStructures.exceptions.EmptyQueueException;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private List<E> list;

    public QueueInList( ){
        list = new SinglyLinkedList<E>();
    }

     // Time Complexity:

    /**
     * Returns true iff the queue contains no elements.
     *
     * @return
     * Complexity:  O(1)
     */
    @Override
    public boolean isEmpty() {
        //TODO: Left as an exercise.
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return
     * Complexity:  O(1)
     */
    @Override
    public int size() {
        //TODO: Left as an exercise.
        return list.size();
    }

    /**
     * Inserts the specified element at the rear of the queue.
     *
     * @param element
     * Complexity:  O(1)
     */
    @Override
    public void enqueue(E element) {
        //TODO: Left as an exercise.
        list.addLast(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return
     * @throws EmptyQueueException
     * Complexity:  O(1)
     */
    @Override
    public E dequeue() {
        //TODO: Left as an exercise

        if(isEmpty()) throw new EmptyQueueException();
        return list.removeFirst();
    }
    /**
     * Returns the element at the front of the queue.
     *
     * @return
     * @throws EmptyQueueException
     * Complexity:  O(1)
     */
    @Override
    public E peek() {
        //TODO: Left as an exercise.

        if(isEmpty()) throw new EmptyQueueException();
        return list.getFirst();
    }
}
