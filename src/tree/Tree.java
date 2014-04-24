package tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of tree datastructor. The tree is formed by nodes and it holds a reference
 * to it's root node. Whenever the tree is to be built it uses this root
 * as a starting and iterates over all of it's children to return a list of the
 * nodes in a specified order. The tree may be built in either pre or post order
 * form.
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Tree<T> {
    // Root of the tree
    private Node<T> root;

    public Tree() {}

    /**
     * Method that returns the root of the tree
     * @return root
     */
    public Node<T> getRoot() {
        return this.root;
    }

    /**
     * Method that sets the root of the tree
     * @param root The node to be set as the root
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     * Method that returns the number of nodes in a tree
     * @return 0 if empty, greater than 0 otherwise
     */
    public int getNumberOfNodes() {
        int nmrNodes = 0;

        if (root != null)
            nmrNodes = getNumberOfChildNodes(root) + 1;

        return nmrNodes;
    }

    /**
     * Method that goes through all nodes in the tree
     * and returns the number nodes in it
     * @param node The node which children is to be counted
     * @return amount of childnodes a node has
     */
    private int getNumberOfChildNodes(Node<T> node) {
        int nmrNodes = node.getNumberOfChildren();

        for (Node<T> child : node.getChildren())
            nmrNodes += getNumberOfChildNodes(child);

        return nmrNodes;
    }

    /**
     * Method that checks if a node is in a tree
     * @param node Node to find
     * @return true if found, false otherwise
     */
    public boolean exists(Node<T> node) {
        return find(node) != null;
    }

    /**
     * Method that tries to find if a tree contains a node
     * @param node the node to find
     * @return null if not found, the node to find otherwise
     */
    public Node<T> find(Node<T> node) {
        Node<T> returnNode = null;

        if (root != null)
            returnNode = deepFind(root, node);

        return returnNode;
    }

    /**
     * Method that iterates through a nodes children to see if
     * it contains a node
     * @param currentNode The node whose children is to be checked
     * @param nodeToFind The node to find
     * @return null if not found, the node to find otherwise
     */
    private Node<T> deepFind(Node<T> currentNode, Node<T> nodeToFind) {
        Node<T> returnNode = null;
        int i = 0;

        if (currentNode.equals(nodeToFind))
            returnNode = currentNode;
        else if (currentNode.hasChildren()) {
            i = 0;
            while(returnNode == null && i < currentNode.getNumberOfChildren()) {
                returnNode = deepFind(currentNode.getChildAt(i), nodeToFind);
                i++;
            }
        }
        return returnNode;
    }

    /**
     * Method that checks if a tree is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() { return root == null; }

    /**
     * Method that starts the build of a tree
     * @param traversalOrder The order of the tree
     * @return null if empty, the tree in list form otherwise
     */
    public List<Node<T>> build(Order traversalOrder) {
        List<Node<T>> result = null;
        if (root != null)
            result = build(root, traversalOrder);

        return result;
    }

    /**
     * Method that starts the next stage of the build by taking in a
     * node and deciding what order it is to build.
     * @param node Starting node
     * @param traversalOrder The order of the tree
     * @return Tree in list form
     */
    public List<Node<T>> build(Node<T> node, Order traversalOrder) {
        List<Node<T>> result = new ArrayList<Node<T>>();

        if (traversalOrder == Order.PRE_ORDER)
            buildPreOrder(node, result);
        else if (traversalOrder == Order.POST_ORDER)
            buildPostOrder(node, result);

        return result;
    }

    /**
     * Method that builds the tree in pre-order. It takes in a
     * node, adds it to a list and then gets the children of that node
     * and recursively adds each child to the list until there are no more
     * nodes to add. Pre-Order adds the root of the tree first.
     *
     * @param node Node to add to tree and get chi
     * @param traversalResult list to add the nodes to
     */
    private void buildPreOrder(Node<T> node, List<Node<T>> traversalResult) {
        traversalResult.add(node);
        for (Node<T> child : node.getChildren())
            buildPreOrder(child, traversalResult);
    }

    /**
     * @see #buildPreOrder(Node, java.util.List)
     * Same method except it builds the tree in post-order.
     * The last child is first added to the list the root is
     * added lastly.
     */
    private void buildPostOrder(Node<T> node, List<Node<T>> traversalResult) {
        for (Node<T> child : node.getChildren())
            buildPostOrder(child, traversalResult);

        traversalResult.add(node);
    }

    /**
     * @see #build(Order)
     * Same method except instead of giving a list this gives a
     * map with a value that specifies the depth that the node is.
     */
    public Map<Node<T>, Integer> buildWithDepth(Order traversalOrder) {
        Map<Node<T>, Integer> result = null;
        if (root != null)
            result = buildWithDepth(root, traversalOrder);
        return result;
    }

    /**
     * @see #build(Node, Order)
     * Similar to the above.
     */
    public Map<Node<T>, Integer> buildWithDepth(Node<T> node, Order traversalOrder) {
        Map<Node<T>, Integer> traversalResult = new LinkedHashMap<Node<T>, Integer>();
        if (traversalOrder == Order.PRE_ORDER)
            buildPreOrderWithDepth(node, traversalResult, 0);
        else if (traversalOrder == Order.POST_ORDER)
            buildPostOrderWithDepth(node, traversalResult, 0);

        return traversalResult;
    }

    /**
     * @see #buildPreOrder(Node, java.util.List)
     * Again the method starts at the top of the tree
     * and adds it to the map along whith the depth the node is located.
     */
    private void buildPreOrderWithDepth(Node<T> node, Map<Node<T>, Integer> traversalResult, int depth) {
        traversalResult.put(node, depth);
        for (Node<T> child : node.getChildren())
            buildPreOrderWithDepth(child, traversalResult, depth + 1);
    }

    /**
     * @see #buildPostOrder(Node, java.util.List)
     * This method is similar to the one without depth
     * as it starts at the top of the tree and goes
     * to the last node before is start adding it to
     * the map along whith the depth the node is located.
     */
    private void buildPostOrderWithDepth(Node<T> node, Map<Node<T>, Integer> traversalResult, int depth) {
        for (Node<T> child : node.getChildren())
            buildPostOrderWithDepth(child, traversalResult, depth + 1);
        traversalResult.put(node, depth);
    }

    /**
     * Method that returns the tree as a string.
     * Pre-Order Assumed to be the type wanted
     * @return Tree as a String
     */
    public String toString() {
        String str = "";
        if (root != null)
            str = build(Order.PRE_ORDER).toString();
        return str;
    }

    /**
     * Method that returns the tree along with it's as a string.
     * Pre-Order Assumed to be the type wanted.
     * @return Tree as a String
     */
    public String toStringWithDepth() {
        String str = "";
        if (root != null)
            str = buildWithDepth(Order.PRE_ORDER).toString();
        return str;
    }

}
