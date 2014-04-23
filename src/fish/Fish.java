package fish;

import javafx.scene.image.Image;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public abstract class Fish {
    FishType type;
    Image image;

    public Image loadImage(FishType type) {
        return new Image("\\images\\" + type.name().toLowerCase() + ".png");
    }

    public FishType getType() {
        return type;
    }

    public void setType(FishType type) {
        this.type = type;
    }

    public void setImage() {
        this.image = loadImage(this.getType());
    }

    public Image getImage() {
        return image;
    }

    public abstract boolean linkable(Fish fish);
}
