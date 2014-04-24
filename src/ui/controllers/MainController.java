package ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ui.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Controller of the application
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class MainController implements Initializable {
    private Main app;

    @FXML
    TabPane tabPane;

    @FXML
    Tab memTab, refTab, GCTab;

    /**
     * Method that's called when initialised. Sets up the handler that detects
     * when a tab has been changed and decides what to do besed on which tab is open.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                setUpTab(tab2);
            }
        });
    }

    /**
     * Method that takes different actions depending on the active
     * tab.
     * @param tab The tab that's just opened
     */
    @FXML
    public void setUpTab(Tab tab) {
        if (tab == refTab) {
            // Set the mode of the ref tab by getting the value the
            // combobox is set to
            app.getRefc().changeMode(RefMode.valueOf(app.getRefc().getModeCombo().getValue().toString().toUpperCase()));
            // draw fish to tab
            app.getRefc().drawFish();
        } else if (tab == GCTab) {
            // draw fish to tab
            app.getGcCon().drawFish();
        } else if (tab == memTab) {
            // draw the lines connecting the objects to the memory lists
            app.getMemc().drawLines();
        }
    }

    /**
     * Method that sets what app this controller this app belongs to
     * @param app Controller's app
     */
    public void setApp(Main app) {
        this.app = app;
    }


}
