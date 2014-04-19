package tree;

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


}
