package tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Tree<T> {
    private Node<T> root;

    public Tree() {}

    public Node<T> getRoot() {
        return this.root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public int getNumberOfNode() {
        int nmrNodes = 0;

        if (root != null)
            nmrNodes = getNumberOfChildNodes(root) + 1;

        return nmrNodes;
    }

    private int getNumberOfChildNodes(Node<T> node) {
        int nmrNodes = node.getNumberOfChildren();

        for (Node<T> child : node.getChildren())
            nmrNodes += getNumberOfChildNodes(child);

        return nmrNodes;
    }

    public boolean exists(Node<T> node) {
        return find(node) != null;
    }

    public Node<T> find(Node<T> node) {
        Node<T> returnNode = null;

        if (root != null)
            returnNode = deepFind(root, node);

        return returnNode;
    }

    private Node<T> deepFind(Node<T> currentNode, Node<T> nodeToFind) {
        Node<T> returnNode = null;
        int i = 0;

        if (currentNode.equals(nodeToFind))
            returnNode = currentNode;
        else if (currentNode.hasChildren()) {
            i = 0;
            while(returnNode == null && i < currentNode.getNumberOfChildren()) {
                returnNode = deepFind(currentNode.getChildAt(i), nodeToFind);
            }
        }
        return returnNode;
    }

    public boolean isEmpty() { return root == null; }

    public List<Node<T>> build(Order traversalOrder) {
        List<Node<T>> result = null;
        if (root != null)
            result = build(root, traversalOrder);

        return result;
    }


    public List<Node<T>> build(Node<T> node, Order traversalOrder) {
        List<Node<T>> result = new ArrayList<Node<T>>();

        if (traversalOrder == Order.PRE_ORDER)
            buildPreOrder(node, result);
        else if (traversalOrder == Order.POST_ORDER)
            buildPostOrder(node, result);

        return result;
    }

    private void buildPreOrder(Node<T> node, List<Node<T>> traversalResult) {
        traversalResult.add(node);
        for (Node<T> child : node.getChildren())
            buildPreOrder(child, traversalResult);
    }

    private void buildPostOrder(Node<T> node, List<Node<T>> traversalResult) {
        for (Node<T> child : node.getChildren())
            buildPostOrder(child, traversalResult);

        traversalResult.add(node);
    }

    public Map<Node<T>, Integer> buildWithDepth(Order traversalOrder) {
        Map<Node<T>, Integer> result = null;
        if (root != null)
            result = buildWithDepth(root, traversalOrder);
        return result;
    }

    public Map<Node<T>, Integer> buildWithDepth(Node<T> node, Order traversalOrder) {
        Map<Node<T>, Integer> traversalResult = new LinkedHashMap<Node<T>, Integer>();
        if (traversalOrder == Order.PRE_ORDER)
            buildPreOrderWithDepth(node, traversalResult, 0);
        else if (traversalOrder == Order.POST_ORDER)
            buildPostOrderWithDepth(node, traversalResult, 0);

        return traversalResult;
    }

    private void buildPreOrderWithDepth(Node<T> node, Map<Node<T>, Integer> traversalResult, int depth) {
        traversalResult.put(node, depth);
        for (Node<T> child : node.getChildren())
            buildPreOrderWithDepth(child, traversalResult, depth + 1);
    }

    private void buildPostOrderWithDepth(Node<T> node, Map<Node<T>, Integer> traversalResult, int depth) {
        for (Node<T> child : node.getChildren())
            buildPostOrderWithDepth(child, traversalResult, depth + 1);
        traversalResult.put(node, depth);
    }

    public String toString() {
        String str = "";
        if (root != null)
            str = build(Order.PRE_ORDER).toString();
        return str;
    }

    public String toStringWithDepth() {
        String str = "";
        if (root != null)
            str = buildWithDepth(Order.PRE_ORDER).toString();
        return str;
    }

}
