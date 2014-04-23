package fish;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class YellowFish extends Fish {
    YellowFish myFriend;

    public YellowFish() {
        this.type = FishType.YELLOW;
        this.setImage();
    }

    public YellowFish getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(YellowFish myFriend) {
        this.myFriend = myFriend;
    }

    @Override
    public boolean linkable(Fish fish) {
        return (fish instanceof YellowFish);
    }
}
