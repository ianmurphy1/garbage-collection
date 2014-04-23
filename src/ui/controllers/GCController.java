package ui.controllers;

import fish.Fish;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import tree.Node;
import ui.icons.FishView;
import ui.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class GCController implements Initializable{
    private Main app;

    @FXML
    private Pane gcPane;

    public void setApp(Main app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Hello");
    }

    public void drawFish() {
        List<FishView> fishImages = app.getRefc().getFishes();
        gcPane.getChildren().removeAll(app.getRefc().getFishes());
        gcPane.getChildren().addAll(app.getRefc().getFishes());
        Node<Fish>[] fishes = app.getGC().getFromSpace();
        for (Node<Fish> node : fishes) {
            boolean drawn = false;
            if (node == null) break;
            for (FishView fv : fishImages) {
                if (fv.hasFish(node)) {
                    drawn = true;
                    break;
                }
            }
            if (!drawn) {
                FishView jim = new FishView(node);
                jim.setY(Math.random() * gcPane.getHeight());
                jim.setX(Math.random() * gcPane.getWidth());
                fishImages.add(jim);
                gcPane.getChildren().add(jim);
            }
        }
    }
}
