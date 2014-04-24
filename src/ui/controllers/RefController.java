package ui.controllers;

import fish.Fish;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import tree.Node;
import ui.icons.FieldView;
import ui.icons.FishView;
import ui.Main;
import ui.icons.Link;

import java.awt.*;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    @FXML
    private List<FishView> localFish = new ArrayList<>(3);

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
        for (FishView fv : fishImages)
            fv.setUnlinkMode();
        for (FishView fv : localFish)
            fv.setUnlinkMode();
    }

    private void setLinkMode() {
        for (FishView fv : fishImages)
            fv.setLinkMode();

        for (FishView fv : localFish)
            fv.setLinkMode();
    }

    private void setMoveMode() {
        for (FishView fv : fishImages)
            fv.setMoveMode();
        for (FishView fv : localFish)
            fv.clearEventHadler();
    }

    public void setApp(Main app) {
        this.app = app;
    }

    public void drawFish() {
        drawFields();
        refPane.getChildren().removeAll(fishImages);
        refPane.getChildren().addAll(fishImages);
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
                jim.setY(Math.random() * refPane.getHeight());
                jim.setX(150 + (Math.random() * (refPane.getWidth() - 150)));
                fishImages.add(jim);
                refPane.getChildren().add(jim);
            }
        }
        drawLinks(fishImages);
    }

    void drawFields() {
        Node[] fishes = {
                app.getGC().getRedRoot(),
                app.getGC().getBlueRoot(),
                app.getGC().getYellowRoot()
        };
        refPane.getChildren().removeAll(localFish);
        refPane.getChildren().addAll(localFish);
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
            final FieldView jim = new FieldView(node, app, 50, i * 75 + 50);
            if (!drawn) {
                jim.setScaleX(2);
                jim.setScaleY(2);
                jim.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Dragboard db = jim.startDragAndDrop(TransferMode.LINK);
                        ClipboardContent c = new ClipboardContent();
                        c.putString("");
                        db.setContent(c);
                        mouseEvent.consume();
                    }
                });
                jim.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        dragEvent.acceptTransferModes(TransferMode.LINK);
                        dragEvent.consume();
                    }
                });
                localFish.add(jim);
                refPane.getChildren().add(jim);
                i++;
            }
        }
        drawLinks(localFish);
    }

    public List<FishView> getFishes() {
        return fishImages;
    }

    public List<FishView> getLocals() {
        return localFish;
    }

    private void drawLinks(List<FishView> fishImages) {
        for (FishView fv : fishImages) {
            refPane.getChildren().removeAll(fv.getSrcLinks());
            for (Link l : fv.getSrcLinks()) {
                refPane.getChildren().add(l);
                l.toBack();
            }
        }
    }

    public ChoiceBox<String> getModeCombo() {
        return modeCombo;
    }

    public void removeFish() {
        Pane gcPane = app.getGcCon().getGcPane();
        List<FishView> gone = new ArrayList<>();
        Node<Fish>[] fishes = app.getGC().getFromSpace();
        for (FishView fv : fishImages) {
            boolean found = false;
            for (Node<Fish> node : fishes)
                if (fv.hasFish(node)) found = true;
            if (!found) {

                gone.add(fv);
                refPane.getChildren().removeAll(fv.getSrcLinks());
                refPane.getChildren().removeAll(fv.getTrgLinks());
                gcPane.getChildren().removeAll(fv.getSrcLinks());
                gcPane.getChildren().removeAll(fv.getTrgLinks());
            }
        }
        refPane.getChildren().removeAll(gone);
        gcPane.getChildren().removeAll(gone);
        fishImages.removeAll(gone);

        refPane.getChildren().removeAll(fishImages);
        gcPane.getChildren().removeAll(fishImages);
        refPane.getChildren().addAll(fishImages);
        gcPane.getChildren().addAll(fishImages);
    }
}
