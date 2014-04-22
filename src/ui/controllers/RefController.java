package ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import ui.FishView;
import ui.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class RefController implements Initializable {

    private enum Mode {
        MOVE,
        LINK,
        UNLINK
    }

    private Main app;

    @FXML
    private ChoiceBox<String> modeCombo = new ChoiceBox<String>();
    @FXML
    private List<FishView> fishes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMoveMode();
        modeCombo.getItems().setAll("Move", "Link", "Unlink");
        modeCombo.setValue("Move");

        modeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                modeCombo.setValue(s2);
                changeMode(Mode.valueOf(s2.toUpperCase()));
            }
        });

        System.out.println("Hello");
    }

    private void changeMode(Mode mode) {
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

    }

    private void setLinkMode() {

    }

    private void setMoveMode() {

    }

    public void setApp(Main app) {
        this.app = app;
    }
}
