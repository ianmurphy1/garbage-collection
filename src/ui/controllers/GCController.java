package ui.controllers;

import javafx.fxml.Initializable;
import ui.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class GCController implements Initializable{
    Main app;

    public void setApp(Main app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello");
    }
}
