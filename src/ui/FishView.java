package ui;

import fish.Fish;
import javafx.scene.image.ImageView;

/**
 * @author Ian Murphy - 20057028
 */
public class FishView extends ImageView {

    private final int SIZE = 25;
    private Fish fish;

    public FishView(Fish fish) {
        this.fish = fish;
        setImage(fish.getImage());
        setSize();
    }

    private void setSize() {
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE + 5);
    }
}
