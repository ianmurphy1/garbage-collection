package fish;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class YellowFish extends Fish {
    YellowFish myFriend;

    /**
     * Contructor for objects of type YellowFish
     */
    public YellowFish() {
        this.type = FishType.YELLOW;
        this.setImage();
    }

    /**
     * Method that returns this instance friend
     * @return myFriend
     */
    public YellowFish getMyFriend() {
        return myFriend;
    }

    /**
     * Method that sets this instance friend
     * @param myFriend Fish to be set as friend
     */
    public void setMyFriend(YellowFish myFriend) {
        this.myFriend = myFriend;
    }

    /**
     * @see fish.Fish#linkable(Fish)
     */
    @Override
    public boolean linkable(Fish fish) {
        return (fish instanceof YellowFish);
    }
}
