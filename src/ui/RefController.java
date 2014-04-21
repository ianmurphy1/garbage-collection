package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class RefController implements Initializable {

    @FXML
    private ComboBox<String> modeCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeCombo.getItems().setAll("Move", "Link", "Unlink");

        modeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {

            }
        });
    }
}
