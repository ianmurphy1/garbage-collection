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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Murphy - 20057028
 */
public class FishView extends ImageView {
    private final int SIZE = 25;
    Main app;
    private Node<Fish> fish;
    protected List<Link> srcLinks = new ArrayList<>();
    protected List<Link> trgLinks = new ArrayList<>();

    private class Delta {
        double x, y;
    }

    public FishView(Node<Fish> fish, Main app) {
        this.fish = fish;
        this.app = app;
        setImage(fish.getData().getImage());
        setSize();
        setMoveMode();
    }

    public FishView(Node<Fish> fish, Main app, double x, double y) {
        this.fish = fish;
        this.app = app;
        this.setImage(fish.getData().getImage());
        this.setX(x);
        this.setY(y);
        setSize();
    }

    private void setSize() {
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE + 5);
    }

    public Node<Fish> getFish() {
        return fish;
    }

    public boolean hasFish(Node<Fish> fish) {
        return this.fish.equals(fish);
    }

    public void setMoveMode() {
        clearEventHadler();
        final Delta dDelta = new Delta();
        final FishView fv = this;

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
                Point2D p;
                for (Link l : srcLinks) {
                    p = getLocation(fv);
                    l.setStartX(p.getX());
                    l.setStartY(p.getY());
                }
                for (Link l : trgLinks) {
                    p = getLocation(fv);
                    l.setStartX(p.getX());
                    l.setStartY(p.getY());
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
                mouseEvent.consume();
            }
        });
    }

    public void setUnlinkMode() {
        clearEventHadler();
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }

    public void setLinkMode() {
        clearEventHadler();
        final FishView fv = this;
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setCursor(Cursor.CROSSHAIR);
                fv.setScaleX(1.1);
                fv.setScaleY(1.1);
                mouseEvent.consume();
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fv.setScaleX(1);
                fv.setScaleY(1);
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

                fv.setScaleX(1.1);
                fv.setScaleY(1.1);

                mouseEvent.consume();
            }
        });
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.LINK);
                fv.setScaleX(1.1);
                fv.setScaleY(1.1);
                dragEvent.consume();
            }
        });
        this.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (fv != dragEvent.getGestureSource()) {
                    fv.setScaleX(1);
                    fv.setScaleY(1);
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
                    createLink(src, trg);
                } catch (UnsupportedOperationException e) {
                    Dialogs.create().title("Error").masthead("Unsupported").message("Cannot Link these fish").showError();
                }
            }
        });
    }

    public void createLink(FishView src, FishView trg) throws UnsupportedOperationException {
        Node<Fish> srcNode = src.getFish();
        Node<Fish> trgNode = trg.getFish();

        if (srcNode.getData().linkable(trgNode.getData())) {
            Point2D p1 = getLocation(src);
            Point2D p2 = getLocation(trg);

            Pane pane = (Pane) this.getParent();

            for (int i = 0; i < src.srcLinks.size(); i++) {
                Link l = src.srcLinks.get(i);
                boolean linked = l.linkedToType(trgNode);
                if(linked) {
                    pane.getChildren().remove(l);
                    l.delete();
                }
            }

            Link ln = new Link(p1, p2, src, trg, app);
            src.srcLinks.add(ln);
            trg.trgLinks.add(ln);
            pane.getChildren().add(ln);
            ln.toBack();
        } else throw new UnsupportedOperationException();
    }

    private Point2D getLocation(FishView src) {

        double x = (src.getBoundsInLocal().getMinX() + src.getBoundsInLocal().getMaxX()) / 2;
        double y = (src.getBoundsInLocal().getMinY() + src.getBoundsInLocal().getMaxY()) / 2;

        return src.localToScene(x, y);
    }

    public void removeLink(Link l) {
        srcLinks.remove(l);
        trgLinks.remove(l);
    }

    private void clearEventHadler() {
        this.setOnDragOver(null);
        this.setOnDragDetected(null);
        this.setOnMousePressed(null);
        this.setOnMouseReleased(null);
        this.setOnMouseEntered(null);
        this.setOnMouseDragged(null);
        this.setCursor(Cursor.DEFAULT);
    }
}