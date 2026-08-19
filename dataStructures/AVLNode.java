package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    public AVLNode( E element, AVLNode<E> parent,  AVLNode<E> left, AVLNode<E> right ){
        super(element, parent, left, right);
        //TODO: Left as an exercise.
        height = 1 + Math.max(h(left), h(right));
    }

    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    private int h(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setLeftChild(node);
        AVLNode<E> right= (AVLNode<E>) getRightChild();
        height = 1 + Math.max(h(node),h(right) );
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setRightChild(node);
        AVLNode<E> left= (AVLNode<E>) getLeftChild();
        height = 1 + Math.max(h(left), h(node) );
    }

    public void setHeight(AVLNode<E> left, AVLNode<E> right) {
        this.height = 1 + Math.max(h(left), h(right));
    }

}
