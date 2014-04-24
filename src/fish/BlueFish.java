package fish;

/**
 * Class for instances of objects of type BlueFish
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class BlueFish extends Fish {
    BlueFish myFriend;
    YellowFish myLunch;

    /**
     * Constructor for class BlueFish
     */
    public BlueFish() {
        this.type = FishType.BLUE;
        this.setImage();
    }

    /**
     * Method that returns this instances friend
     * @return Instance's friend
     */
    public BlueFish getMyFriend() {
        return myFriend;
    }

    /**
     * Method that sets this instance friend
     * @param myFriend Fish to be set as friend
     */
    public void setMyFriend(BlueFish myFriend) {
        this.myFriend = myFriend;
    }

    /**
     * Method that returns this instances friend
     * @return Instance's friend
     */
    public YellowFish getMyLunch() {
        return myLunch;
    }

    /**
     * Method that sets this instance lunch
     * @param myLunch Fish to be set as lunc
     */
    public void setMyLunch(YellowFish myLunch) {
        this.myLunch = myLunch;
    }

    /**
     * @see fish.Fish#linkable(Fish)
     */
    @Override
    public boolean linkable(Fish fish) {
        return (fish instanceof BlueFish) || (fish instanceof YellowFish);
    }
}
