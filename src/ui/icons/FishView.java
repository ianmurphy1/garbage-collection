package ui.icons;

import fish.Fish;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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

    public FishView(Node<Fish> fish, Main app) {
        this.fish = fish;
        this.app = app;
        setImage(fish.getData().getImage());
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
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }

    public void setUnlinkMode() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }

    public void setLinkMode() {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }

    public void createLink(FishView src, FishView trg) {
        Node<Fish> srcNode = src.getFish();
        Node<Fish> trgNode = src.getFish();

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
        }
    }

    private Point2D getLocation(FishView src) {
        int offset = 28;
        double x = (src.getBoundsInLocal().getMinX() + src.getBoundsInLocal().getMaxX()) / 2;
        double y = ((src.getBoundsInLocal().getMinY() + src.getBoundsInLocal().getMaxY()) / 2) - offset;

        return src.localToScene(x, y);
    }

    public void removeLink(Link l) {
        srcLinks.remove(l);
        trgLinks.remove(l);
    }
}
