package gc;


import fish.*;
import tree.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class GarbageCollection {
    private final int MEMORY_SIZE = 50;

    private Fish[] toSpace = new Fish[MEMORY_SIZE];
    private Fish[] fromSpace = new Fish[MEMORY_SIZE];

    private Tree<Fish> redTree = new Tree<Fish>();
    private Tree<Fish> blueTree = new Tree<Fish>();
    private Tree<Fish> yellowTree = new Tree<Fish>();
    private Set<Node<Fish>> liveSet;

    private RedFish rFish;
    private BlueFish bFish;
    private YellowFish yFish;

    public GarbageCollection() {
        this.bFish = new BlueFish();
        this.rFish = new RedFish();
        this.yFish = new YellowFish();

        redTree.setRoot(new Node<Fish>(rFish));
        blueTree.setRoot(new Node<Fish>(bFish));
        yellowTree.setRoot(new Node<Fish>(yFish));
        buildLiveSet();
    }

    private void buildLiveSet() {
        liveSet = new HashSet<Node<Fish>>();
        liveSet.addAll(redTree.build(Order.PRE_ORDER));
        liveSet.addAll(blueTree.build(Order.PRE_ORDER));
        liveSet.addAll(yellowTree.build(Order.PRE_ORDER));
    }

    public void copy() {
        buildLiveSet();
        int i = 0;
        for (Node<Fish> node : liveSet) {
            fromSpace[i] = node.getData();
            i++;
        }
        toSpace = fromSpace;
        fromSpace = new Fish[MEMORY_SIZE];
    }

    public boolean addRef(Node<Fish> parentNode, Node<Fish> childNode) {
        if (childNode.hasChildren()) removeChild(childNode, parentNode);
        addChild(parentNode, childNode);

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

    public Node<Fish> createFish(FishType type) {

        if (isFull()) throw new IllegalStateException();

        Node<Fish> newNode = null;

        if (type == FishType.RED)
            newNode = new Node<Fish>(new RedFish());
        else if (type == FishType.BLUE)
            newNode = new Node<Fish>(new BlueFish());
        else if (type == FishType.YELLOW)
            newNode = new Node<Fish>(new YellowFish());

        addToToSpace(newNode.getData());

        return newNode;
    }

    private void addToToSpace(Fish data) {
        for (int i = 0; i < toSpace.length; i++) {
            if (toSpace[i] == null) {
                toSpace[i] = data;
                break;
            }
        }
    }

    private void addChild(Node<Fish> parent, Node<Fish> child) {
        parent.addChild(child);
    }

    private void removeChild(Node<Fish> parent, Node<Fish> child) {
        int i = 0;
        for (Node<Fish> pChild : parent.getChildren()) {
            if (pChild.equals(child)) {
                parent.removeChildAt(i);
                break;
            }
            i++;
        }
    }

    private boolean isFull() {
        for (int i = 0; i < toSpace.length; i++)
            if (toSpace[i] == null) return false;

        return true;
    }
}
