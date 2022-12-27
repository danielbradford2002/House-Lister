import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class RedBlackTreeIterator<T extends Comparable<T>> implements Iterator<T> {
    private RedBlackTree<T> rbt;
    private LinkedList<T> ll;
    private Iterator<T> it;
    private int currentIndex = 0;
    private Stack<RedBlackTree.Node> stack;

    public RedBlackTreeIterator(RedBlackTree<T> rb) {
        //recursion of in order traversal method
        //look up how to implement in order traversal and while doing traversal put all of the
        // nodes of rbtree into the linkedlist and then it=ll.iterator();
        ll = new LinkedList<T>();
        rbt = rb;
        stack = new Stack<RedBlackTree.Node>();
    }

    public LinkedList<T> inOrderTraversal(RedBlackTree<T> redBlack) {
        rbt = redBlack;
        if (rbt.root == null) {
            return ll;
        }
        RedBlackTree.Node<T> current = rbt.root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.leftChild;
            }
            current = stack.pop();
            ll.add(current.data);
            current = current.rightChild;
        }
        it = ll.iterator();
        return ll;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public T next() {
        if (hasNext()) {
            return it.next();
        } else throw new NoSuchElementException("error. there is no next element");
    }
}

