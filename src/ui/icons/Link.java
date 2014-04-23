package ui.icons;

import fish.Fish;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import tree.Node;
import ui.Main;

/**
 * @author Ian Murphy - 20057028
 *         Date: 22/04/2014
 */
public class Link extends Line {
    private Main app;
    private FishView src, trg;
    private final double STROKE_WIDTH = 1.5d;
    private final double HIGHLIGHTED_STROKE_WIDTH = 3.5d;

    public Link(Point2D p1, Point2D p2, FishView src, FishView trg, Main app) {
        this.app = app;
        this.src = src;
        this.trg = trg;

        this.setStartX(p1.getX());
        this.setStartY(p1.getY());

        this.setEndX(p2.getX());
        this.setEndY(p2.getY());

        this.setStrokeWidth(STROKE_WIDTH);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Link l = (Link) mouseEvent.getTarget();
                l.setStrokeWidth(HIGHLIGHTED_STROKE_WIDTH);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Link l = (Link) mouseEvent.getTarget();
                l.setStrokeWidth(STROKE_WIDTH);
            }
        });
        makeLink(src, trg);
    }

    private boolean makeLink(FishView src, FishView trg) {
        if (app.getGC().addRef(src.getFish(), trg.getFish())) {
            this.src = src;
            this.trg = trg;
            return true;
        }
        return false;
    }

    public boolean linkedToType(Node<Fish> trgNode) {
        return trg.getFish().getData().getClass() == trgNode.getData().getClass();
    }

    public void delete() {
        app.getGC().removeRef(src.getFish(), trg.getFish());
        src.removeLink(this);
        trg.removeLink(this);
    }
}
