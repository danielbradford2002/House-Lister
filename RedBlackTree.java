// --== CS400 Fall 2022 File Header Information ==--
// Name: Daniel Bradford
// Email: dcbradford@wisc.edu
// Team: AG
// TA: Yuye
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

//RedBlackTree.Node<Integer> a = new RedBlackTree.Node<Integer>(5);

/**
 * Red-Black Tree implementation with a Node inner class for representing the nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red black tree by
 * modifying the insert functionality. In this activity, we will start with implementing rotations
 * for the binary search tree insert algorithm. You can use this class' insert method to build a
 * regular binary search tree, and its toString method to display a level-order traversal of the
 * tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements Iterable<T> {

    /**
     * This class represents a node holding a single value within a binary tree the parent, left,
     * and right child references are always maintained.
     */
    protected static class Node<T> {
        public int blackHeight;
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;

        public Node(T data) {
            this.data = data;
        }

        /**
         * @return true when this node has a parent and is the left child of that parent, otherwise
         * return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        public boolean isRightChild() {
            return parent != null && parent.rightChild == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input data value to a new
     * node in a leaf position within the tree. After this insertion, no attempt is made to
     * restructure or balance the tree. This tree will not hold null references, nor duplicate data
     * values.
     *
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain equal data references
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
            root.blackHeight = 1;
            size++;
            return true;
        } // add first node to an empty tree
        else {
            boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
            if (returnValue) size++;
            else
                throw new IllegalArgumentException("This RedBlackTree already contains that " +
                        "value" + ".");
            return returnValue;
        }
    }

    /**
     * enforces the properties of the red black tree after the insert method is called. Makes sure
     * the black height is alright and that two red nodes do not come after each other.
     *
     * @param redNode is the red node that could be causing problems in the red black tree
     */
    protected void enforceRBTreePropertiesAfterInsert(Node<T> redNode) {
        if (redNode.parent != null) {

            if (redNode.parent.blackHeight == 0) {


                //case 1 (if parent's sibling is black and on the same side as the redNode)

                if (redNode.isLeftChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.isLeftChild()) {
                            if (redNode.parent.parent.rightChild == null || redNode.parent.parent.rightChild.blackHeight == 1) {
                                redNode.parent.blackHeight = 1;
                                redNode.parent.parent.blackHeight = 0;
                                rotate(redNode.parent, redNode.parent.parent);
                                root.blackHeight = 1;
                            }
                        }
                    }
                }
                if (redNode.isRightChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.isRightChild()) {
                            if (redNode.parent.parent.leftChild == null || redNode.parent.parent.leftChild.blackHeight == 1) {
                                redNode.parent.blackHeight = 1;
                                redNode.parent.parent.blackHeight = 0;
                                rotate(redNode.parent, redNode.parent.parent);
                                root.blackHeight = 1;

                            }
                        }
                    }
                }
                //case 2
                if (redNode.isLeftChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.isRightChild()) {
                            if (redNode.parent.parent.leftChild == null || redNode.parent.parent.leftChild.blackHeight == 1) {
                                rotate(redNode, redNode.parent);
                                redNode.blackHeight = 1;
                                redNode.parent.blackHeight = 0;
                                rotate(redNode, redNode.parent);
                                root.blackHeight = 1;
                            }
                        }
                    }
                }
                if (redNode.isRightChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.isLeftChild()) {
                            if (redNode.parent.parent.rightChild == null || redNode.parent.parent.rightChild.blackHeight == 1) {
                                rotate(redNode, redNode.parent);
                                redNode.blackHeight = 1;
                                redNode.parent.blackHeight = 0;
                                rotate(redNode, redNode.parent);
                                root.blackHeight = 1;
                            }
                        }
                    }
                }
                //case 3 if parent sibling is red
                if (redNode.parent.isRightChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.parent.leftChild != null) {
                            if (redNode.parent.parent.leftChild.blackHeight == 0) {
                                redNode.parent.parent.leftChild.blackHeight = 1;
                                redNode.parent.parent.blackHeight = 0;
                                redNode.parent.blackHeight = 1;
                                root.blackHeight = 1;
                                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);
                            }
                        }
                    }
                }

                if (redNode.parent.isLeftChild()) {
                    if (redNode.parent.parent != null) {
                        if (redNode.parent.parent.rightChild != null) {
                            if (redNode.parent.parent.rightChild.blackHeight == 0) {
                                redNode.parent.parent.rightChild.blackHeight = 1;
                                redNode.parent.parent.blackHeight = 0;
                                redNode.parent.blackHeight = 1;
                                root.blackHeight = 1;
                                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the position that the
     * newNode should be inserted, and then extend this tree by the newNode in that position.
     *
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the newNode should be
     *                inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0) return false;

            // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }

    public Iterator<T> iterator() {
        return new RedBlackTreeIterator<>(this);
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree. When the provided
     * child is a leftChild of the provided parent, this method will perform a right rotation. When
     * the provided child is a rightChild of the provided parent, this method will perform a left
     * rotation. When the provided nodes are not related in one of these ways, this method will
     * throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position (between these two node
     *               arguments)
     * @param parent is the node being rotated from parent to child position (between these two node
     *               arguments)
     * @throws IllegalArgumentException when the provided child and parent node references are not
     *                                  initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (!(child.isLeftChild() || child.isRightChild())) {
            throw new IllegalArgumentException("error. child is neither the right child nor the " + "left child");
        }
        if (child.isLeftChild()) { // this is the left rotation
            parent.leftChild = child.rightChild;
            child.rightChild = parent;
            if (parent.parent != null) {
                child.parent = parent.parent;
                if (parent.isLeftChild()) {
                    parent.parent.leftChild = child;
                } else {
                    parent.parent.rightChild = child;
                }
            } else {
                root = child; //if the parent was already the root, the child becomes the root
            }
        } else if (child.isRightChild()) { // this is the right rotation
            parent.rightChild = child.leftChild;
            child.leftChild = parent;
            if (parent.parent != null) {
                child.parent = parent.parent;
                if (parent.isLeftChild()) {
                    parent.parent.leftChild = child;
                } else {
                    parent.parent.rightChild = child;
                }
            } else {
                root = child;  //if the parent was already the root, the child becomes the root
            }
        }
    }

    /**
     * Get the size of the tree (its number of nodes).
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     *
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     *
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks for the value *data*.
     *
     * @param data    the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }


    /**
     * This method performs an inorder traversal of the tree. The string representations of each
     * data value within this tree are assembled into a comma separated string within brackets
     * (similar to many implementations of java.util.Collection, like java.util.ArrayList,
     * LinkedList, etc). Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above produces a level order
     * traversal of the nodes / values of the tree.
     *
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        sb.append(toInOrderStringHelper("", this.root));
        if (this.root != null) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" ]");
        return sb.toString();
    }

    private String toInOrderStringHelper(String str, Node<T> node) {
        if (node == null) {
            return str;
        }
        str = toInOrderStringHelper(str, node.leftChild);
        str += (node.data.toString() + ", ");
        str = toInOrderStringHelper(str, node.rightChild);
        return str;
    }

    /**
     * This method performs a level order traversal of the tree rooted at the current node. The
     * string representations of each data value within this tree are assembled into a comma
     * separated string within brackets (similar to many implementations of java.util.Collection).
     * Note that the Node's implementation of toString generates a level order traversal. The
     * toString of the RedBlackTree class below produces an inorder traversal of the nodes / values
     * of the tree. This method will be helpful as a helper for the debugging and testing of your
     * rotation implementation.
     *
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        String output = "[ ";
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if (next.leftChild != null) q.add(next.leftChild);
                if (next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if (!q.isEmpty()) output += ", ";
            }
        }
        return output + " ]";
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
    }


    /**
     * Main method to run tests. Comment out the lines for each test to run them.
     *
     * @param args
     */
    public static void main(String[] args) {
    }

}


//class RedBlackTreeTest {
//    /**
//     * Test to make sure the color change and rotation works correctly on a basic tree
//     */
//    @Test
//    public void testOne() {
//        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
//        RedBlackTree.Node<Integer> testNode5 = new RedBlackTree.Node<>(5);
//        RedBlackTree.Node<Integer> testNode0 = new RedBlackTree.Node<>(0);
//        RedBlackTree.Node<Integer> testNode1 = new RedBlackTree.Node<>(1);
//        tree.insert(5);
//        tree.insert(0);
//        tree.insert(1);
//        assertEquals(testNode1.blackHeight, 1);
//        assertEquals(testNode0.blackHeight, 0);
//        assertEquals(testNode5.blackHeight, 0);
//    }
//
//    /**
//     * Tests the rotate method and ensures color change works correctly after the rotation method
//     * after inserting 5 nodes. The hierarchy of the tree is also being tested
//     */
//    @Test
//    public void testTwo(){
//        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
//        RedBlackTree.Node<Integer> testNode91 = new RedBlackTree.Node<>(91);
//        RedBlackTree.Node<Integer> testNode41 = new RedBlackTree.Node<>(41);
//        RedBlackTree.Node<Integer> testNode51 = new RedBlackTree.Node<>(51);
//        RedBlackTree.Node<Integer> testNode31 = new RedBlackTree.Node<>(31);
//        tree.insert(91);
//        tree.insert(41);
//        tree.insert(51);
//        tree.insert(31);
//        assertEquals(testNode51.blackHeight, 1);
//        assertEquals(testNode41.blackHeight, 0);
//        assertEquals(testNode91.blackHeight, 0);
//        assertEquals(testNode31.blackHeight, 1);
//        assertEquals(testNode51.leftChild.data, testNode41.data);
//        assertEquals(testNode51.rightChild.data, testNode91.data);
//        assertEquals(testNode41.leftChild.data, testNode31.data);
//        RedBlackTree.Node<Integer> testNode15 = new RedBlackTree.Node<>(15);
//        tree.insert(15);
//        assertEquals(testNode51.blackHeight, 1);
//        assertEquals(testNode31.blackHeight, 0);
//        assertEquals(testNode91.blackHeight, 0);
//        assertEquals(testNode15.blackHeight, 1);
//        assertEquals(testNode41.blackHeight, 1);
//        assertEquals(testNode51.leftChild.data, testNode31.data);
//        assertEquals(testNode51.rightChild.data, testNode91.data);
//        assertEquals(testNode31.leftChild.data, testNode15.data);
//        assertEquals(testNode31.rightChild.data, testNode41.data);
//    }
//
//    /**
//     * Tests rotate and color change for 5 nodes(right rotation). Also test hierarchy
//     *Tests the rotate method and also makes sure the color change for 5 node works correctly.
//     * makes sure the hierarchy is in correct order
//     */
//    @Test
//    public void testThree(){
//        RedBlackTree<Integer> testRBTree = new RedBlackTree<Integer>();
//        RedBlackTree.Node<Integer> testNode73 = new RedBlackTree.Node<>(73);
//        RedBlackTree.Node<Integer> testNode53 = new RedBlackTree.Node<>(53);
//        RedBlackTree.Node<Integer> testNode63 = new RedBlackTree.Node<>(63);
//        RedBlackTree.Node<Integer> testNode93 = new RedBlackTree.Node<>(93);
//        testRBTree.insert(73);
//        testRBTree.insert(53);
//        testRBTree.insert(63);
//        testRBTree.insert(93);
//        assertEquals(testNode63.blackHeight, 1);
//        assertEquals(testNode53.blackHeight, 0);
//        assertEquals(testNode73.blackHeight, 0);
//        assertEquals(testNode93.blackHeight, 1);
//        assertEquals(testNode63.leftChild.data, testNode53.data);
//        assertEquals(testNode63.rightChild.data, testNode73.data);
//        assertEquals(testNode73.rightChild.data, testNode93.data);
//        RedBlackTree.Node<Integer> testNode83 = new RedBlackTree.Node<>(83);
//        testRBTree.insert(83);
//        assertEquals(testNode63.blackHeight, 1);
//        assertEquals(testNode53.blackHeight, 0);
//        assertEquals(testNode83.blackHeight, 0);
//        assertEquals(testNode73.blackHeight, 1);
//        assertEquals(testNode93.blackHeight, 1);
//        assertEquals(testNode63.leftChild.data, testNode53.data);
//        assertEquals(testNode63.rightChild.data, testNode83.data);
//        assertEquals(testNode83.leftChild.data, testNode73.data);
//        assertEquals(testNode83.rightChild.data, testNode93.data);
//    }
//}

