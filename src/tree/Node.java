package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that forms the main element of a tree. The node contains generic data
 * which can be any type of object. The class keeps track of any children that
 * a node may have.
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Node<T> {
    private T data;
    private List<Node<T>> children;

    /**
     * Constructor for Node class
     */
    public Node() {
        children = new ArrayList<Node<T>>();
    }

    /**
     * Constructor for Node class that sets the data of the Node
     * @param data Object that node holds
     */
    public Node(T data) {
        this();
        setData(data);
    }

    /**
     * Method that returns the nodes children
     * @return list of node's children
     */
    public List<Node<T>> getChildren() {
        return this.children;
    }

    /**
     * Method that returns of the number of children the not has
     * @return 0 if no children, greater than 0 otherwise
     */
    public int getNumberOfChildren() {
        return getChildren().size();
    }

    /**
     * Method that checks if a node has children
     * @return true if node has children, false otherwise
     */
    public boolean hasChildren() {
        return getNumberOfChildren() > 0;
    }

    /**
     * Method that sets the nodes children
     * @param children The list of nodes to be set
     */
    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    /**
     * Method that adds a node to the node's children
     * @param child Node to be added
     */
    public void addChild(Node<T> child) {
        children.add(child);
    }

    /**
     * Method that adds a node to the node's children at
     * a specified index
     * @param index Where node is to be added
     * @param child Node to be added
     */
    public void addChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
        children.add(index, child);
    }

    /**
     * Method that removes all children from a node
     */
    public void removeChildren() {
        this.children = new ArrayList<Node<T>>();
    }

    /**
     * Method that reoves a child from a node at a specified index
     * @param index Location of child to remove
     * @throws IndexOutOfBoundsException
     */
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }

    /**
     * Method that return the child node located at the specified index
     * @param index The location of the child
     * @return ChildNode if found, null otherwise
     * @throws IndexOutOfBoundsException
     */
    public Node<T> getChildAt(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }

    /**
     * Method that return that data stored in the node
     * @return
     */
    public T getData() {
        return this.data;
    }

    /**
     * Set the data that the node stores
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Method that removes a child that matches the node passed in
     * @param child The child to be removed
     */
    public void removeChild(Node<T> child) {
        children.remove(child); }
}
