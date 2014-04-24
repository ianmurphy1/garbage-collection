package gc;


import fish.*;
import tree.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Main Class of the application, this class handles the creation of fish objects,
 * creation of links between the tree nodes. The building of the liveSet. And keeping
 * track of the objects created and how they fit into memory. It is also the place where
 * the algorithm of the garbage collection takes place.
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class GarbageCollection {
    private final int MEMORY_SIZE = 50; //Memory size of the project


    private Node<Fish>[] toSpace = new Node[MEMORY_SIZE];
    private Node<Fish>[] fromSpace = new Node[MEMORY_SIZE];
    private Node<Fish>[] objects = new Node[MEMORY_SIZE];

    // Trees for the children of the coloured roots
    private Tree<Fish> redTree = new Tree<Fish>();
    private Tree<Fish> blueTree = new Tree<Fish>();
    private Tree<Fish> yellowTree = new Tree<Fish>();
    //Container for the liveset, using set as it handles multiples automatically
    private Set<Node<Fish>> liveSet;

    //The instance variables of the garbage collector
    private RedFish rFish;
    private BlueFish bFish;
    private YellowFish yFish;

    //The roots for the trees
    private Node<Fish> redRoot;
    private Node<Fish> blueRoot;
    private Node<Fish> yellowRoot;

    /**
     * Method that returns the root of the red tree
     * @return redRoot
     */
    public Node<Fish> getRedRoot() {
        return redRoot;
    }

    /**
     * Method that returns the root of the blue tree
     * @return redRoot
     */
    public Node<Fish> getBlueRoot() {
        return blueRoot;
    }

    /**
     * Method that returns the root of the yellow tree
     * @return yelloRoot
     */
    public Node<Fish> getYellowRoot() {
        return yellowRoot;
    }

    /**
     * Method that returns the object list
     * @return objects
     */
    public Node<Fish>[] getObjects() {
        return objects;
    }

    /**
     * Method that returns the fromSpace (Memory)
     * @return fromSpace
     */
    public Node<Fish>[] getFromSpace() {
        return fromSpace;
    }

    /**
     * Method that returns the GC's liveset
     * @return liveSet
     */
    public Set<Node<Fish>> getLiveSet() {
        return liveSet;
    }

    /**
     * Constructor for Garbage Collection Class
     */
    public GarbageCollection() {
        this.rFish = new RedFish();
        this.bFish = new BlueFish();
        this.yFish = new YellowFish();

        this.redRoot = new Node<Fish>(rFish);
        this.blueRoot = new Node<Fish>(bFish);
        this.yellowRoot = new Node<Fish>(yFish);

        redTree.setRoot(redRoot);
        blueTree.setRoot(blueRoot);
        yellowTree.setRoot(yellowRoot);
        buildLiveSet();
    }

    /**
     * Method that builds each tree from its root and
     * then adds all the nodes to a set.
     */
    private void buildLiveSet() {
        liveSet = new HashSet<Node<Fish>>();
        liveSet.addAll(redTree.build(Order.PRE_ORDER));
        liveSet.addAll(blueTree.build(Order.PRE_ORDER));
        liveSet.addAll(yellowTree.build(Order.PRE_ORDER));
    }

    /**
     * Main algorithm for the Garbage Collector.
     * Builds the liveset and then takes the objects that are in the
     * liveset and copies them over to the toSpace. The toSpace is then
     * set as the fromSpace and a new toSpace is set up.
     */
    public void copy() {
        buildLiveSet();
        clearObjects();
        for (Node<Fish> node : liveSet) {
            if (!isRoot(node)) {
                addToToSpace(node);
                addToObjects(node);
            }
        }
        fromSpace = toSpace;
        toSpace = new Node[MEMORY_SIZE];
    }

    /**
     * Method that removes all objects from the objects list
     */
    private void clearObjects() {
        for (int i = 0; i < objects.length; i++)
            objects[i] = null;
    }

    /**
     * Method that checks is a node is the root of any tree.
     *
     * @param node
     * @return true if node is root, false otherwise
     */
    private boolean isRoot(Node<Fish> node) {
        return node.equals(redRoot) || node.equals(blueRoot) || node.equals(yellowRoot);
    }

    /**
     * Method that adds a reference to a fish and sets up a link between
     * between two nodes. It will delete a link if there is one in the
     * oppposite direction first.
     *
     * @param parentNode
     * @param childNode
     * @return true if ref established, false otherwise
     */
    public boolean addRef(Node<Fish> parentNode, Node<Fish> childNode) {
        if (childNode.hasChildren() && !isRoot(childNode))
            removeChild(childNode, parentNode);

        /**
         * Keep instance variables as root of trees
         */
        if (isRoot(childNode))
            addChild(childNode, parentNode);
        else addChild(parentNode, childNode);

        Fish parent = parentNode.getData();
        Fish child = childNode.getData();

        if (parent instanceof RedFish) {
            RedFish redFish = (RedFish) parent;
            if (child instanceof RedFish) {
                redFish.setMyFriend((RedFish) child);
                return true;
            } else if (child instanceof BlueFish) {
                redFish.setMyLunch( (BlueFish) child);
                return true;
            }else if (child instanceof YellowFish) {
                redFish.setMySnack( (YellowFish) child);
                return true;
            }
        } else if (parent instanceof BlueFish) {
            BlueFish blueFish = (BlueFish) parent;
           if (child instanceof BlueFish) {
               blueFish.setMyFriend( (BlueFish) child);
               return true;
           } else if (child instanceof YellowFish) {
               blueFish.setMyLunch( (YellowFish) child);
               return true;
           }
        } else if (parent instanceof YellowFish) {
            YellowFish yellowFish = (YellowFish) parent;
            if (child instanceof YellowFish) {
                yellowFish.setMyFriend( (YellowFish) child);
                return true;
            }
        }
        return false;
    }

    /**
     * Method that removes a reference to a fish and deletes a link between
     * two nodes.
     *
     * @param parentNode
     * @param childNode
     * @return true if ref ref removed, false otherwise
     */
    public boolean removeRef(Node<Fish> parentNode, Node<Fish> childNode) {
        Fish parent = parentNode.getData();
        Fish child = childNode.getData();
        removeChild(parentNode, childNode);
        if (parent instanceof RedFish) {
            RedFish redFish = (RedFish) parent;
            if (child instanceof RedFish) {
                redFish.setMyFriend(null);
                return true;
            } else if (child instanceof BlueFish) {
                redFish.setMyLunch(null);
                return true;
            }else if (child instanceof YellowFish) {
                redFish.setMySnack(null);
                return true;
            }
        } else if (parent instanceof BlueFish) {
            BlueFish blueFish = (BlueFish) parent;
            if (child instanceof BlueFish) {
                blueFish.setMyFriend(null);
                return true;
            } else if (child instanceof YellowFish) {
                blueFish.setMyLunch(null);
                return true;
            }
        } else if (parent instanceof YellowFish) {
            YellowFish yellowFish = (YellowFish) parent;
            if (child instanceof YellowFish) {
                yellowFish.setMyFriend(null);
                return true;
            }
        }
        return false;
    }

    /**
     * Method that creates a new fish object, new node with the fish in it
     * and places it in memory
     *
     * @param type The type of fish to be created
     * @return the Node created
     * @throws IllegalStateException
     * @throws IndexOutOfBoundsException
     */
    public Node<Fish> createFish(FishType type) throws IllegalStateException, IndexOutOfBoundsException {
        Node<Fish> newNode = null;

        if (type == FishType.RED)
            newNode = new Node<Fish>(new RedFish());
        else if (type == FishType.BLUE)
            newNode = new Node<Fish>(new BlueFish());
        else if (type == FishType.YELLOW)
            newNode = new Node<Fish>(new YellowFish());

        if (isFull()) throw new IllegalStateException();
        if (findSpace(fromSpace, newNode) == -1) throw new IndexOutOfBoundsException();

        addToFromSpace(newNode);
        addToObjects(newNode);
        return newNode;
    }

    /**
     * Method that adds a fish object to the objects list
     * @param data  Fish to be added
     */
    private void addToObjects(Node<Fish> data) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                objects[i] = data;
                break;
            }
        }
    }

    /**
     * Method that looks to find space in an array and on finding it
     * will return the starting index where the object can be placed
     * in the array.
     * @param fishes The array being checked
     * @param f The fish being added
     * @return Starting index if there's room, -1 otherwise
     */
    private int findSpace(Node<Fish>[] fishes, Node<Fish> f) {
        int size = f.getData().getClass().getDeclaredFields().length;

        for (int i = 0; i < fishes.length; i++) {
            if (fishes[i] == null) {
                int moreRequired = size - 1;
                for (int j = i + 1; j < fishes.length && moreRequired > 0 && fishes[j] == null; j++) {
                    moreRequired--;
                }
                if (moreRequired == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Method that adds a node to the fromSpace
     * @param data Fish to be added
     */
    private void addToFromSpace(Node<Fish> data) {
        int size = data.getData().getClass().getDeclaredFields().length;
        int start = findSpace(fromSpace, data);
        for (int i = 0; i < size; i++) {
            fromSpace[start + i] = data;
        }
    }

    /**
     * Method that adds a child to a node.
     * @param parent The parent node
     * @param child  Child node
     */
    private void addChild(Node<Fish> parent, Node<Fish> child) {
        parent.addChild(child);
    }

    /**
     * Method that removes a child node from a parent
     * @param child Fish to be added
     */
    private void removeChild(Node<Fish> parent, Node<Fish> child) {
        parent.removeChild(child);
    }

    /**
     * Method that adds a node to the toSpace
     * @param data Fish to be added
     */
    private void addToToSpace(Node<Fish> data) {
        int size = data.getData().getClass().getDeclaredFields().length;
        int start = findSpace(toSpace, data);
        for (int i = 0; i < size; i++) {
            toSpace[start + i] = data;
        }
    }

    /**
     * Method that checks if the fromSpace is full
     * @return true if full, false otherwise
     */
    private boolean isFull() {
        for (int i = 0; i < fromSpace.length; i++)
            if (fromSpace[i] == null) return false;

        return true;
    }

    /**
     * Method that checks if the objects list is empty
     * @return true if empty, false otherwise
     */
    public boolean objectsIsEmpty() {
        return objects[0] == null;
    }
}
