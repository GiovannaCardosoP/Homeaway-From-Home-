package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{
    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a  right  child  of z before the  rotation,
     * then z becomes the left child of y after the rotation.
     * @param z - root of the rotation
     * @pre: z has a right child
     */


    protected void rotateLeft( BTNode<Entry<K,V>> z){
        //TODO: Left as an exercise.
        //  a single rotation modifies a constant number of parent-child relationships,
        // it can be implemented in O(1)time


        BTNode<Entry<K,V>> newRoot = (BTNode<Entry<K, V>>) z.getRightChild();
        BTNode<Entry<K,V>> leftChild = (BTNode<Entry<K, V>>) newRoot.getLeftChild();

        newRoot.setLeftChild(z);
        z.setRightChild(leftChild);
        if(leftChild != null ) leftChild.setParent(z);

        BTNode<Entry<K,V>> parentOfZ = (BTNode<Entry<K, V>>) z.getParent();
        if (parentOfZ != null) {
            if (parentOfZ.getLeftChild() == z)
                parentOfZ.setLeftChild(newRoot);
            else
                parentOfZ.setRightChild(newRoot);

        }else{
            root = newRoot;
        }
        newRoot.setParent(parentOfZ);
        z.setParent(newRoot);

        ((AVLNode<Entry<K,V>>) z ).setHeight((AVLNode<Entry<K, V>>) z.getLeftChild(), (AVLNode<Entry<K, V>>) z.getRightChild());
        ((AVLNode<Entry<K,V>>) newRoot ).setHeight((AVLNode<Entry<K, V>>)  newRoot.getLeftChild(), (AVLNode<Entry<K, V>>) newRoot.getRightChild());


    }

    /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z) {
        //TODO: Left as an exercise.
        //  a single rotation modifies a constant number of parent-child relationships,
        // it can be implemented in O(1)time

        BTNode<Entry<K, V>> newRoot = (BTNode<Entry<K, V>>) z.getLeftChild();
        BTNode<Entry<K, V>> rightChild = (BTNode<Entry<K, V>>) newRoot.getRightChild();
        newRoot.setRightChild(z);
        z.setLeftChild(rightChild);

        if(rightChild != null ){
        rightChild.setParent(z);
        }

        BTNode<Entry<K,V>> parentOfZ = (BTNode<Entry<K, V>>) z.getParent();
        if(parentOfZ != null){
            if (parentOfZ.getLeftChild() == z)
                parentOfZ.setLeftChild(newRoot);
            else
                parentOfZ.setRightChild(newRoot);
        } else {
            root = newRoot;
        }
        newRoot.setParent(parentOfZ);
        z.setParent(newRoot);

        ((AVLNode<Entry<K,V>>) z ).setHeight((AVLNode<Entry<K, V>>) z.getLeftChild(), (AVLNode<Entry<K, V>>) z.getRightChild());
        ((AVLNode<Entry<K,V>>) newRoot ).setHeight((AVLNode<Entry<K, V>>)  newRoot.getLeftChild(), (AVLNode<Entry<K, V>>) newRoot.getRightChild());



    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        //TODO: Left as an exercise.
        // the modification of a tree T caused by a trinode restructuring operation
        // can be implemented through case analysis either as a single rotation or as a double rotation.
        // The double rotation arises when position x has the middle of the three relevant keys
        // and is first rotated above its parent Y, and then above what was originally its grandparent Z.
        // In any of the cases, the trinode restructuring is completed with O(1)running time

        //X = PIVOT  -> slide 38
        BTNode<Entry<K,V>> left  = (BTNode<Entry<K, V>>) x.getLeftChild();
        BTNode<Entry<K,V>> right = (BTNode<Entry<K, V>>) x.getRightChild();
        BTNode<Entry<K,V>> y;

        if (left == null && right == null) {
            y = null;
        }
        else if (left == null) { // if there´s no left child study right
            y = right;
        }
        else if (right == null) { // if there´s no right child study left
            y = left;
        }
        else {
            int hl = left.getHeight();
            int hr = right.getHeight();

            if (hl >= hr) // who is higher to study
                y = left;
            else
                y = right;
        }

        if( y == null) {return null;}
        BTNode<Entry<K,V>> yLeft  = (BTNode<Entry<K, V>>) y.getLeftChild();
        BTNode<Entry<K,V>> yRight = (BTNode<Entry<K, V>>) y.getRightChild();
        BTNode<Entry<K,V>> t;

        if (yLeft == null && yRight == null) {
            t = null;   // should never happen
        }
        else if (yLeft == null) { // if there´s no right child study left
            t = yRight;
        }
        else if (yRight == null) { // if there´s no left child study right
            t = yLeft;
        }
        else {
            int hyl = (yLeft).getHeight();
            int hyr = (yRight).getHeight();

            if (hyl >= hyr) // who is higher to study
                t = yLeft;
            else
                t = yRight;
        }

        BTNode<Entry<K,V>> newRoot;

        // y is left child of z  LL or LR
        if (y == left) {
            if (t == yLeft) {
                // ll
                rotateRight(x);
                newRoot = (BTNode<Entry<K, V>>) x.getParent();
            }
            else {
                //lr
                rotateLeft(y);
                rotateRight(x);
                newRoot = (BTNode<Entry<K, V>>) x.getParent();
            }

        }
        // y is right child of z  RR or RL
        else {
            if (t == yRight) {
                //rr
                rotateLeft(x);
                newRoot = (BTNode<Entry<K, V>>) x.getParent();
            }
            else {
                //rl
                rotateRight(y);
                rotateLeft(x);
                newRoot = (BTNode<Entry<K, V>>) x.getParent();
            }
        }
        return newRoot;
    }
}
