package fish;

import javafx.scene.image.Image;

/**
 * Abstract class of the objects used in this application.
 *
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public abstract class Fish {
    FishType type;
    Image image;

    /**
     * Method that returns this fish's type
     * @return Fish's type
     */
    public FishType getType() {
        return type;
    }

    /**
     * Method that returns a new Image for the fish
     * @param type the type of fish this is
     * @return Fish's Image
     */
    public Image loadImage(FishType type) {
        return new Image("\\images\\" + type.name().toLowerCase() + ".png");
    }

    /**
     * Method that sets the type of this fish
     * @param type The type the fish is to be set to
     */
    public void setType(FishType type) {
        this.type = type;
    }

    /**
     * Method that sets this fish's imaage
     */
    public void setImage() {
        this.image = loadImage(this.getType());
    }

    /**
     * Method that returns this fish's image
     * @return Fish's image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Method that checks if this fish can be
     * linked to by another fish
     * @param fish the fish to be checked if a
     *             link can occur
     * @return true if link can happen, false otherwise
     */
    public abstract boolean linkable(Fish fish);

    /**
     * Set This fish's image to be blacked out
     */
    public void setAsBlack() {
        this.image = loadImage();
    }

    /**
     * Method that returns the blacked out image of the fish
     * @return Fish's image
     */
    private Image loadImage() {
        return new Image("\\images\\black" + this.type.name().toLowerCase() + ".png");
    }
}
