package ui.controllers;

import fish.Fish;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.dialog.Dialogs;
import tree.Node;
import ui.Main;
import ui.icons.FieldView;
import ui.icons.FishView;
import ui.icons.Link;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Ian Murphy - 20057028
 *         Date: 21/04/2014
 */
public class GCController implements Initializable{
    private Main app;

    @FXML
    private Button gcButton;

    public Pane getGcPane() {
        return gcPane;
    }

    @FXML
    private Pane gcPane;

    public void setApp(Main app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gcButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        gcButton.setEffect(new DropShadow());
                    }
                });
        //Removing the shadow when the mouse cursor is off
        gcButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        gcButton.setEffect(null);
                    }
                });

        gcButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                app.getGC().copy();
                if (!app.getGC().objectsIsEmpty()) {
                    highlightFish();
                    Dialogs.create()
                            .title("Garbage Collection")
                            .masthead(null)
                            .message("The fish in black will be deleted")
                            .showInformation();
                    app.getMemc().getObjects().setItems(app.getMemc().convertList(app.getGC().getObjects()));
                    app.getMemc().getMemory().setItems(app.getMemc().convertList(app.getGC().getFromSpace()));
                } else {
                    Dialogs.create()
                            .title("Garbage Collection")
                            .masthead(null)
                            .message("All Objects Will Be Deleted")
                            .showInformation();
                }
                redraw();
            }
        });
    }

    private void redraw() {
        app.getRefc().removeFish();
        app.getRefc().drawFish();
        drawFish();
    }

    private void clearHandlers(List<FishView> views) {
        for (FishView fv : views)
            fv.clearEventHadler();
    }

    public void drawFish() {
        drawFields();
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
                FishView jim = new FishView(node, app);
                jim.setY(Math.random() * gcPane.getHeight());
                jim.setX(Math.random() * gcPane.getWidth());
                fishImages.add(jim);
                gcPane.getChildren().add(jim);
            }
        }
        drawLinks(fishImages);
        clearHandlers(fishImages);
    }

    private void highlightFish() {
        List<Node<Fish>> fishes = new ArrayList<>(app.getGC().getLiveSet());
        for (FishView fv : app.getRefc().getFishes()) {
            boolean found = false;
            for (Node<Fish> f : fishes)
                if (fv.hasFish(f)) found = true;

            if (!found) {
                fv.getFish().getData().setAsBlack();
                fv.setImage(fv.getFish().getData().getImage());
            }
        }
        drawFish();
    }

    private void drawFields() {
        List<FishView> localFish = app.getRefc().getLocals();
        Node[] fishes = {
                app.getGC().getRedRoot(),
                app.getGC().getBlueRoot(),
                app.getGC().getYellowRoot()
        };
        gcPane.getChildren().removeAll(localFish);
        gcPane.getChildren().addAll(localFish);
        int i = 1;
        for (Node<Fish> node : fishes) {
            boolean drawn = false;
            if (node == null) break;
            for (FishView fv : localFish) {
                if (fv.hasFish(node)) {
                    drawn = true;
                    break;
                }
            }
            if (!drawn) {
                FieldView jim = new FieldView(node, app, 50, i * 50 + 50);
                jim.setScaleX(1.5);
                jim.setScaleY(1.5);
                localFish.add(jim);
                gcPane.getChildren().add(jim);
                i++;
            }
        }
        drawLinks(localFish);
        clearHandlers(localFish);
    }

    private void drawLinks(List<FishView> fishImages) {
        for (FishView fv : fishImages) {
            gcPane.getChildren().removeAll(fv.getSrcLinks());
            for (Link l : fv.getSrcLinks()) {
                gcPane.getChildren().add(l);
                l.toBack();
            }
        }
    }
}
