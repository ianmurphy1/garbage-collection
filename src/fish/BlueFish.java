package fish;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class BlueFish extends Fish {
    BlueFish myFriend;
    YellowFish myLunch;

    public BlueFish() {
        this.type = FishType.BLUE;
        this.setImage();
    }

    public BlueFish getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(BlueFish myFriend) {
        this.myFriend = myFriend;
    }

    public YellowFish getMyLunch() {
        return myLunch;
    }

    public void setMyLunch(YellowFish myLunch) {
        this.myLunch = myLunch;
    }
}
