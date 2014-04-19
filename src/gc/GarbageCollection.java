package gc;


import fish.BlueFish;
import fish.Fish;
import fish.RedFish;
import fish.YellowFish;
import tree.Node;
import tree.Order;
import tree.Tree;

import java.util.ArrayList;
import java.util.List;

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
    private List<Node<Fish>> liveSet;

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
        liveSet = new ArrayList<Node<Fish>>();
        liveSet.addAll(redTree.build(Order.PRE_ORDER));
        liveSet.addAll(blueTree.build(Order.PRE_ORDER));
        liveSet.addAll(yellowTree.build(Order.PRE_ORDER));
    }
}
