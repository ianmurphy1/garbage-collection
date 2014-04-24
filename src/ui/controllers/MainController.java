package ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ui.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class MainController implements Initializable {
    private Main app;

    @FXML
    TabPane tabPane;

    @FXML
    Tab memTab, refTab, GCTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                drawFish(tab2);
            }
        });
    }

    @FXML
    public void drawFish(Tab tab) {
        if (tab == refTab) {
            app.getRefc().changeMode(RefMode.valueOf(app.getRefc().getModeCombo().getValue().toString().toUpperCase()));
            app.getRefc().drawFish();
        } else if (tab == GCTab) {
            app.getGcCon().drawFish();
        } else if (tab == memTab) {
            app.getMemc().drawLines();
        }
    }

    public void setApp(Main app) {
        this.app = app;
    }


}
