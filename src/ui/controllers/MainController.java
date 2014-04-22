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

    public void setApp(Main app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if(tab2 == refTab) drawFish(refTab);
                if(tab2 == GCTab) drawFish(GCTab);
            }
        });
        System.out.println("Main");
    }

    @FXML
    TabPane tabPane;

    @FXML
    Tab memTab, refTab, GCTab;

    @FXML
    public void drawFish(Tab tab) {
        if (tab == refTab) {
            app.getRefc().changeMode(RefMode.MOVE);
            app.getRefc().drawFish();
        } else if (tab == GCTab) {
            app.getGcCon().drawFish();
        }
    }


}
