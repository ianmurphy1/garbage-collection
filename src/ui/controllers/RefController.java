package ui.controllers;

import fish.Fish;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import tree.Node;
import ui.FishView;
import ui.Main;

import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class RefController implements Initializable {

    private Main app;

    @FXML
    private Pane refPane;

    @FXML
    private ChoiceBox<String> modeCombo = new ChoiceBox<String>();
    @FXML
    private List<FishView> fishImages = new ArrayList<>(50);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeCombo.getItems().setAll("Move", "Link", "Unlink");
        modeCombo.setValue("Move");

        modeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                modeCombo.setValue(s2);
                changeMode(RefMode.valueOf(s2.toUpperCase()));
            }
        });



        System.out.println("Hello");
    }

    public void changeMode(RefMode mode) {
        switch (mode) {
            case MOVE :
                setMoveMode();
                break;
            case LINK :
                setLinkMode();
                break;
            case UNLINK :
                setUnlinkMode();
                break;
        }
    }

    private void setUnlinkMode() {
        System.out.println("Unlink Mode");
        for (FishView fv : fishImages)
            fv.setUnlinkMode();
    }

    private void setLinkMode() {
        System.out.println("Link Mode");
        for (FishView fv : fishImages)
            fv.setLinkMode();
    }

    private void setMoveMode() {
        System.out.println("Move Mode");
        for (FishView fv : fishImages)
            fv.setMoveMode();
    }

    public void setApp(Main app) {
        this.app = app;
    }

    public void drawFish() {
        refPane.getChildren().removeAll(fishImages);
        refPane.getChildren().addAll(fishImages);
        Node<Fish>[] fishes = app.getGC().getFromSpace();
        for (Node<Fish> node : fishes) {
            boolean drawn = false;
            if (node == null) break;
            for (FishView fv : fishImages) {
                if (fv.hasFish(node.getData())) {
                    drawn = true;
                    break;
                }
            }
            if (!drawn) {
                FishView jim = new FishView(node.getData());
                jim.setY(Math.random() * refPane.getHeight());
                jim.setX(Math.random() * refPane.getWidth());
                fishImages.add(jim);
                refPane.getChildren().add(jim);
            }
        }
    }
}
