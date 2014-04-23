package ui.icons;

import fish.Fish;
import tree.Node;
import ui.Main;

/**
 * @author Ian Murphy - 20057028
 *         Date: 23/04/2014
 */
public class FieldView extends FishView {
    private FishView var;

    public FieldView(Node<Fish> fish, Main app) {
        super(fish, app);
    }

    public FieldView(Node<Fish> fish, Main app, double x, double y) {
        super(fish, app, x, y);
    }

    public void setLocalVar(FishView var) {
        this.var = var;
    }

}
