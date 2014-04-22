package ui.controllers;
import fish.Fish;
import fish.FishType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.controlsfx.dialog.Dialogs;
import tree.Node;
import ui.Main;

import java.net.URL;
import java.util.*;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class MemoryController implements Initializable {
    private Main app;
    @FXML
    ListView<Rectangle> objects;
    @FXML
    ListView<Rectangle> memory;


    public void setApp(Main app) {
        this.app = app;
        System.out.println("App set");
    }

    @FXML
    void createFish(MouseEvent event) {
        Button btn = (Button) event.getSource();
        String id = btn.getId().toUpperCase();
        try {
            app.getGC().createFish(FishType.valueOf(id));
            objects.setItems(convertList(app.getGC().getFromSpace()));
            memory.setItems(convertList(app.getGC().getFromSpace()));
        } catch (IllegalStateException e) {
//            e.printStackTrace();
            System.out.println();
            Dialogs.create()
                   .title("Memory")
                   .masthead("Out of Memory!")
                   .message("Memory is Full!")
                   .showError();
        }
    }

    private ObservableList<Rectangle> convertList(Node<Fish>[] fromSpace) {
        List<Rectangle> rects = new ArrayList<>();
        double width = objects.getWidth();
        Rectangle r = null;
        for (Node<Fish> node : fromSpace) {
            if (node == null) break;
            switch (node.getData().getType()) {
                case RED :
                    r = new Rectangle(width, 5, Color.RED);
                    break;
                case YELLOW :
                    r = new Rectangle(width, 5, Color.YELLOW);
                    break;
                case BLUE :
                    r = new Rectangle(width, 5, Color.BLUE);
                    break;
            }
            r.setStroke(Color.BLACK);
            r.setStrokeWidth(1.5);
            rects.add(r);
        }
        return FXCollections.observableList(rects);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello");
    }
}
