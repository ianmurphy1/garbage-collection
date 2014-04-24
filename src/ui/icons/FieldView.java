package ui.icons;

import fish.Fish;
import tree.Node;
import ui.Main;

/**
 * Class used to store the nodes of the fish that are to represent
 * instance variables in the application.
 *
 * @author Ian Murphy - 20057028
 *         Date: 23/04/2014
 */
public class FieldView extends FishView {

    /**
     * @see ui.icons.FishView#FishView(tree.Node, ui.Main)
     */
    public FieldView(Node<Fish> fish, Main app) {
        super(fish, app);
    }

    /**
     * @see ui.icons.FishView#FishView(tree.Node, ui.Main, double, double)
     */
    public FieldView(Node<Fish> fish, Main app, double x, double y) {
        super(fish, app, x, y);
    }

}
