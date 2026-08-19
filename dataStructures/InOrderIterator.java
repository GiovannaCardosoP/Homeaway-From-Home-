package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;

    /**
     * Root Node
     */
    private BTNode<E> root;

    /**
     *
     * @param root
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        this.rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance();
        return elem;
    }

    private void advance() {
        //TODO: Left as an exercise.
        //vai ao node mais à esquerda, (rewind())
        // e depois sub-arvore direita
        //começar pelo furtherLeftElement e depois subir
        // start:  next=root.furtherLeftElement();

        //nucna volto para <--- esquerda, só para cima e direita
        if(next==null) return;
        if(next.getRightChild() != null){ //sub-arvore
            //se nao houver leftChild, retorna o rightChild( ele mesmo)
            next = ((BTNode<E>) next.getRightChild()).furtherLeftElement();
        }else{

            //if(next.getParent() != null && next.equals( ((BTNode<E>) next.getParent()).getLeftChild()) ){
            //    next = (BTNode<E>) next.getParent();
            //} else{
            //obter parent do parent atual -> subir na tree
            BTNode<E> temp = (BTNode<E>) next.getParent(); //este eu ja visitei
            //quero obter o pai do pai para percorrer o lado direito
            //ou seja, se é rigthChild, tens de subir para o avô pq o pai foi visitado apos o leftChild
            while ( temp != null && next == temp.getRightChild()){//(next.equals(((BTNode<E>) next.getParent()).getRightChild())) ) {
                //next = (BTNode<E>) temp.getParent();

                next = temp;
                temp = (BTNode<E>) temp.getParent();

            }
            // se chegou ao maior elemento
            // sai do loop, com o primeiro pai (root)
                /*
                if(next == root) {
                    next = null;
                }
                 */
            next = temp;

        }

    }


    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
