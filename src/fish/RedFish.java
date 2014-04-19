package fish;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class RedFish extends Fish {
    RedFish myFriend;
    BlueFish myLunch;
    YellowFish mySnack;

    public RedFish getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(RedFish myFriend) {
        this.myFriend = myFriend;
    }

    public BlueFish getMyLunch() {
        return myLunch;
    }

    public void setMyLunch(BlueFish myLunch) {
        this.myLunch = myLunch;
    }

    public YellowFish getMySnack() {
        return mySnack;
    }

    public void setMySnack(YellowFish mySnack) {
        this.mySnack = mySnack;
    }
}
