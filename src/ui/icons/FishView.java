package ui.icons;

import fish.Fish;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tree.Node;

/**
 * @author Ian Murphy - 20057028
 */
public class FishView extends ImageView {

    private final int SIZE = 25;
    private Node<Fish> fish;

    public FishView(Node<Fish> fish) {
        this.fish = fish;
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
}
