package ui.icons;

import fish.Fish;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import tree.Node;
import ui.Main;

/**
 * Class that handle the links between images in the applicattion
 *
 * @author Ian Murphy - 20057028
 *         Date: 22/04/2014
 */
public class Link extends Line {
    // Source and target images of link
    //Link's width
    private final double STROKE_WIDTH = 1.5d;
    // Width of link when highlighted
    private final double HIGHLIGHTED_STROKE_WIDTH = 3.5d;

    private Main app;
    private FishView src, trg;

    /**
     * Contructor for objects of type link
     * @param p1 starting point
     * @param p2 end point
     * @param src link's source image
     * @param trg link's target image
     * @param app App link belongs to
     */
    public Link(Point2D p1, Point2D p2, FishView src, FishView trg, Main app) {
        this.app = app;
        this.src = src;
        this.trg = trg;
        this.setStartX(p1.getX());
        this.setStartY(p1.getY());
        this.setEndX(p2.getX());
        this.setEndY(p2.getY());
        this.setStrokeWidth(STROKE_WIDTH);
        makeLink(src, trg);
    }

    /**
     * Method that creates a link between two images
     * @param src Source image
     * @param trg  Target Image
     * @return true if link made, false otherwise
     * @see gc.GarbageCollection#addRef(tree.Node, tree.Node)
     */
    private boolean makeLink(FishView src, FishView trg) {
        if (app.getGC().addRef(src.getFish(), trg.getFish())) {
            this.src = src;
            this.trg = trg;
            return true;
        }
        return false;
    }

    /**
     * Method that checks if a link is linked to class of the same type
     * @param trgNode The node being checked
     * @return true if linked to same type, false otherwise
     */
    public boolean linkedToType(Node<Fish> trgNode) {
        return trg.getFish().getData().getClass() == trgNode.getData().getClass();
    }

    /**
     * Method that deletes a link between two images
     * @see gc.GarbageCollection#removeRef(tree.Node, tree.Node)
     */
    public void delete() {
        app.getGC().removeRef(src.getFish(), trg.getFish());
        src.removeLink(this);
        trg.removeLink(this);
    }

    /**
     * Method that sets the handlers and behaviour of links
     * in Link Mode
     */
    public void setLinkMode() {
        // Delete link if it's clicked and remove it from the
        // source image and target image list of links
        // and remove it from the tab
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Link l = (Link) mouseEvent.getTarget();
                Pane p = (Pane) l.getParent();
                p.getChildren().remove(l);
                src.removeLink(l);
                trg.removeLink(l);
            }
        });

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
    }

    /**
     * Method that removes handlers of this instance
     * if it's not in link mode
     */
    public void unsetLinkMode() {
        this.setOnMouseClicked(null);
        this.setOnMouseEntered(null);
        this.setOnMouseExited(null);
    }

    /**
     * Get the source image of this link
     * @return src
     */
    public FishView getSrc() {
        return src;
    }

    /**
     * Get the target image of this link
     * @return trg
     */
    public FishView getTrg() {
        return trg;
    }

}
