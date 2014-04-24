package fish;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class RedFish extends Fish {
    RedFish myFriend;
    BlueFish myLunch;
    YellowFish mySnack;

    /**
     * Contructor for objects of type RedFish
     */
    public RedFish() {
        this.type = FishType.RED;
        this.setImage();
    }

    /**
     * Method that returns this instances friend
     * @return Instance's friend
     */
    public RedFish getMyFriend() {
        return myFriend;
    }

    /**
     * Method that sets this instance friend
     * @param myFriend Fish to be set as friend
     */
    public void setMyFriend(RedFish myFriend) {
        this.myFriend = myFriend;
    }

    /**
     * Method that returns this instances lunch
     * @return Instance's lunch
     */
    public BlueFish getMyLunch() {
        return myLunch;
    }

    /**
     * Method that sets this instance friend
     * @param myLunch Fish to be set as lunch
     */
    public void setMyLunch(BlueFish myLunch) {
        this.myLunch = myLunch;
    }

    /**
     * Method that returns this instances snack
     * @return Instance's snack
     */
    public YellowFish getMySnack() {
        return mySnack;
    }

    /**
     * Method that sets this instance snack
     * @param mySnack Fish to be set as snack
     */
    public void setMySnack(YellowFish mySnack) {
        this.mySnack = mySnack;
    }

    /**
     * @see fish.Fish#linkable(Fish)
     */
    @Override
    public boolean linkable(Fish fish) {
        return (fish instanceof RedFish) || (fish instanceof BlueFish) || (fish instanceof YellowFish);
    }
}
