package ui.controllers;
import fish.Fish;
import fish.FishType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
    @FXML
    AnchorPane mainPane;

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
            objects.setItems(convertList(app.getGC().getObjects()));
            memory.setItems(convertList(app.getGC().getFromSpace()));
            drawLines();
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

    public void drawLines() {
        List<javafx.scene.Node> lines = new ArrayList<>();
        for (javafx.scene.Node n : mainPane.getChildren()) {
            if (n instanceof Line || n instanceof Circle) lines.add(n);
        }
        mainPane.getChildren().removeAll(lines);
        List<Node<Fish>> objectList = Arrays.asList(app.getGC().getObjects());
        List<Node<Fish>> memoryList = Arrays.asList(app.getGC().getFromSpace());

        for (Node<Fish> fObj : objectList) {
            for (Node<Fish> f : memoryList) {
                if ((fObj == f) && f != null ) {
                    int i = objectList.indexOf(fObj);
                    Rectangle rect = objects.getItems().get(i);
                    Point2D p1 = rect.localToScene(rect.getLayoutBounds().getMaxX(), -28);
                    Circle c1 = new Circle(p1.getX(), p1.getY(), 1);
                    mainPane.getChildren().add(c1);

                    i = memoryList.indexOf(f);
                    Rectangle rect2 = memory.getItems().get(i);
                    Point2D p2 = rect2.localToScene(rect2.getLayoutBounds().getMinX(), -28);
                    Circle c2 = new Circle(p2.getX(), p2.getY(), 1);
                    mainPane.getChildren().add(c2);
                    Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    mainPane.getChildren().add(line);
                }
            }
        }
    }
}
