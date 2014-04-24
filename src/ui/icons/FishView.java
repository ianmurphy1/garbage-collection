package ui.icons;

import com.sun.javafx.css.converters.CursorConverter;
import fish.Fish;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import org.controlsfx.dialog.Dialogs;
import tree.Node;
import ui.Main;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to display fish images in the GUI.
 * Holds a fish and lists that contain the links to and from
 * other images.
 *
 * @author Ian Murphy - 20057028
 */
public class FishView extends ImageView {
    private final int SIZE = 25;
    Main app;
    private Node<Fish> fish;
    private List<Link> srcLinks = new ArrayList<>();
    private List<Link> trgLinks = new ArrayList<>();

    /**
     * Helper class that is used to track the changes in coordinates
     * of an image when moved.
     */
    private class Delta {
        double x, y;
    }

    /**
     * Constructor for fish view class
     * @param fish The Node to be set as this objects fish
     * @param app  The application the view belongs to
     */
    public FishView(Node<Fish> fish, Main app) {
        this.fish = fish;
        this.app = app;
        setImage(fish.getData().getImage());
        setSize();
        setMoveMode();
    }

    /**
     * Constructor for fish view class
     * @param fish The Node to be set as this objects fish
     * @param app  The application the view belongs to
     * @param x The x location of the view
     * @param y The y location of the view
     */
    public FishView(Node<Fish> fish, Main app, double x, double y) {
        this.fish = fish;
        this.app = app;
        this.setImage(fish.getData().getImage());
        this.setX(x);
        this.setY(y);
        setSize();
    }

    /**
     * Method that sets the size of the image
     */
    private void setSize() {
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE + 5);
    }

    /**
     * Method that return the node of this image
     * @return This images node
     */
    public Node<Fish> getFish() {
        return fish;
    }

    /**
     * Returns if this image contains a node
     * @param fish The Node to be checked
     * @return true if the image contains the node, false otherwise
     */
    public boolean hasFish(Node<Fish> fish) {
        return this.fish.equals(fish);
    }

    /**
     * Method that sets the image to move mode so that it can
     * be moved around in the application
     */
    public void setMoveMode() {
        clearEventHadler();
        final Delta dDelta = new Delta();
        final FishView fv = this;
        final double originalScale = fv.getScaleX(); // Keep track of images original scale
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dDelta.x = fv.getLayoutX() - mouseEvent.getSceneX();
                dDelta.y = fv.getLayoutY() - mouseEvent.getSceneY();
                fv.setCursor(Cursor.CLOSED_HAND);
                mouseEvent.consume();
            }
        });
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setCursor(Cursor.HAND);
                Point2D p = getLocation(fv);
                // Redraw links to image
                for (Link l : srcLinks) {
                    l.setStartX(p.getX());
                    l.setStartY(p.getY());
                }
                for (Link l : trgLinks) {
                    l.setEndX(p.getX());
                    l.setEndY(p.getY());
                }
                mouseEvent.consume();
            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setLayoutX(mouseEvent.getSceneX() + dDelta.x);
                fv.setLayoutY(mouseEvent.getSceneY() + dDelta.y);
                mouseEvent.consume();
            }
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setCursor(Cursor.HAND);
                fv.setScaleX(originalScale * 1.1);
                fv.setScaleY(originalScale * 1.1);
                mouseEvent.consume();
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setCursor(Cursor.HAND);
                fv.setScaleX(originalScale);
                fv.setScaleY(originalScale);
                mouseEvent.consume();
            }
        });
    }

    /**
     * Method that clears the event handles of the image
     * and set up the handlers for the links to be deleted
     */
    public void setUnlinkMode() {
        clearEventHadler();
        for (Link l : trgLinks) {
            l.setLinkMode();
        }
    }

    /**
     * Method that sets the handlers for link mode so that
     * links can be established between fish
     */
    public void setLinkMode() {
        clearEventHadler();

        final FishView fv = this;
        final double originalScale = fv.getScaleX();
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setCursor(Cursor.CROSSHAIR);
                fv.setScaleX(originalScale * 1.1);
                fv.setScaleY(originalScale * 1.1);
                mouseEvent.consume();
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setScaleX(originalScale);
                fv.setScaleY(originalScale);
                mouseEvent.consume();
            }
        });
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dragboard db = fv.startDragAndDrop(TransferMode.LINK);
                ClipboardContent c = new ClipboardContent();
                c.putString("");
                db.setContent(c);

                fv.setScaleX(originalScale * 1.1);
                fv.setScaleY(originalScale * 1.1);

                mouseEvent.consume();
            }
        });
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.LINK);
                double originalScale = fv.getScaleX();
                fv.setScaleX(originalScale);
                fv.setScaleY(originalScale);
                dragEvent.consume();
            }
        });
        this.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (fv != dragEvent.getGestureSource()) {
                    fv.setScaleX(originalScale);
                    fv.setScaleY(originalScale);
                }
            }
        });
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                FishView src = (FishView) dragEvent.getGestureSource();
                FishView trg = (FishView) dragEvent.getGestureTarget();
                trg.setScaleX(1);
                trg.setScaleX(1);
                if (!(src instanceof FishView)) {
                    src.setScaleX(1);
                    src.setScaleY(1);
                }
                try {
                    // Set up link
                    createLink(src, trg);
                } catch (UnsupportedOperationException e) {
                    Dialogs.create()
                            .title("Error")
                            .masthead("Unsupported")
                            .message("Cannot Link these fish\nSelect Higher order Fish First")
                            .showError();
                } catch (IllegalClassFormatException e) {
                    Dialogs.create().title("Error")
                            .masthead("Unsupported")
                            .message("Local Variable must reference a fish of the same colour")
                            .showError();
                }
            }
        });
    }

    /**
     * Method that tries to create a link between two fish
     * @param src The parent fish
     * @param trg The child fish
     * @throws UnsupportedOperationException
     * @throws IllegalClassFormatException
     */
    public void createLink(FishView src, FishView trg) throws UnsupportedOperationException, IllegalClassFormatException {
        Node<Fish> srcNode = src.getFish();
        Node<Fish> trgNode = trg.getFish();
        // Check if a localvar can be set
        if (src instanceof FieldView && srcNode.getData().getClass() != trgNode.getData().getClass())
            throw new IllegalClassFormatException();

        // check if the parent fish can link to the child
        if (srcNode.getData().linkable(trgNode.getData())) {
            srcNode.addChild(trgNode);

            Point2D p1 = getLocation(src);
            Point2D p2 = getLocation(trg);

            Pane pane = (Pane) this.getParent();

            for (int i = 0; i < src.srcLinks.size(); i++) {
                Link l = src.srcLinks.get(i);
                boolean linked = l.linkedToType(trgNode);
                if(linked) { // if already drawn, remove it
                    pane.getChildren().remove(l);
                    l.delete();
                }
            }
            Link ln = new Link(p1, p2, src, trg, app);
            src.srcLinks.add(ln);
            trg.trgLinks.add(ln);
            // draw line between two fish on the pane
            // and add to pane
            pane.getChildren().add(ln);
            ln.toBack();
        } else throw new UnsupportedOperationException();
    }

    /**
     * Method that returns the location of a fish
     * @param fv The fish to be found
     * @return The point where the fish is located
     */
    private Point2D getLocation(FishView fv) {
        double x = ((fv.getBoundsInLocal().getMinX() + fv.getBoundsInLocal().getMaxX()) / 2);
        double y = ((fv.getBoundsInLocal().getMinY() + fv.getBoundsInLocal().getMaxY()) / 2);

        return fv.localToParent(x, y);
    }

    /**
     * Method that returns this objects source links
     * @return This objects source links
     */
    public List<Link> getSrcLinks() {
        return srcLinks;
    }

    /**
     * Method that returns this objects target links
     * @return This objects target links
     */
    public List<Link> getTrgLinks() {
        return trgLinks;
    }

    /**
     * Method that removes a link from one fish and another
     * and also removes a child node from the parent
     * @param l link to be removed
     */
    public void removeLink(Link l) {
        Node<Fish> src = l.getSrc().getFish();
        Node<Fish> trg = l.getTrg().getFish();
        src.removeChild(trg);
        srcLinks.remove(l);
        trgLinks.remove(l);
    }

    /**
     * Method to remove all handlers from an image
     */
    public void clearEventHadler() {
        this.unsetUnlinkMode();
        this.setOnDragOver(null);
        this.setOnDragDetected(null);
        this.setOnMousePressed(null);
        this.setOnMouseReleased(null);
        this.setOnMouseEntered(null);
        this.setOnMouseDragged(null);
        this.setCursor(Cursor.DEFAULT);
    }

    /**
     * Method that removes the handlers from the links
     */
    private void unsetUnlinkMode() {
        for (Link l : trgLinks)
            l.unsetLinkMode();
    }
}
