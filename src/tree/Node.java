package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Node<T> {
    private T data;
    private List<Node<T>> children;

    public Node() {
        children = new ArrayList<Node<T>>();
    }

    public Node(T data) {
        this();
        setData(data);
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public int getNumberOfChildren() {
        return getChildren().size();
    }

    public boolean hasChildren() {
        return getNumberOfChildren() > 0;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public void addChild(Node<T> child) {
        children.add(child);
    }

    public void addChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
        children.add(index, child);
    }

    public void removeChildren() {
        this.children = new ArrayList<Node<T>>();
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }

    public Node<T> getChildAt(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void removeChild(Node<T> child) {
        children.remove(child); }
}
