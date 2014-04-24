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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Memory Tab of this application
 *
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
    }

    /**
     * Method that tries to creates a fish object.
     * @param event Which button was pressed
     * @see gc.GarbageCollection#createFish(fish.FishType)
     */
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
            Dialogs.create()
                   .title("Memory")
                   .masthead("Out of Memory!")
                   .message("Memory is Full!")
                   .showError();
        } catch (IndexOutOfBoundsException e) {
            Dialogs.create()
                    .title("Memory")
                    .masthead("Out of Memory!")
                    .message("Not Enough Room for this type of fish!")
                    .showError();
        }
    }

    /**
     * Method that converts the items in the memory to rectangles
     * so that they can be shown on the memorytab
     * @param arr The array to be iterated through
     * @return List of Rectangles to be displayed
     */
    ObservableList<Rectangle> convertList(Node<Fish>[] arr) {
        List<Rectangle> rects = new ArrayList<>();
        double width = objects.getWidth();

        Rectangle r = null;
        for (Node<Fish> node : arr) {
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
    }

    /**
     * Method that returns this tabs list of rectangles
     * that represent memory
     * @return Memory List
     */
    public ListView<Rectangle> getMemory() {
        return memory;
    }

    /**
     * Method that returns this tabs list of rectangles
     * that represent objects
     * @return Object List
     */
    public ListView<Rectangle> getObjects() {
        return objects;
    }

    /**
     * Method that draws lines between the objects and memory list
     * and shows how the objects take up space in memory
     */
    public void drawLines() {
        List<javafx.scene.Node> lines = new ArrayList<>();
        // Find all the lines or circles that are contained within the
        // tab
        for (javafx.scene.Node n : mainPane.getChildren()) {
            if (n instanceof Line || n instanceof Circle) lines.add(n);
        }
        mainPane.getChildren().removeAll(lines);
        List<Node<Fish>> objectList = Arrays.asList(app.getGC().getObjects());
        List<Node<Fish>> memoryList = Arrays.asList(app.getGC().getFromSpace());

        // Go through all objects in the object list and compare them to
        // those in the memory list. If they match then draw the lines
        // between them and display them on the tab.
        for (Node<Fish> fObj : objectList) {
            for (Node<Fish> f : memoryList) {
                if ((fObj == f) && f != null ) {
                    int i = objectList.indexOf(fObj);
                    Rectangle rect = objects.getItems().get(i);
                    Point2D p1 = rect.localToScene(rect.getLayoutBounds().getMaxX(), -28);
                    Circle c1 = new Circle(p1.getX() - 4, p1.getY(), 1);
                    mainPane.getChildren().add(c1);

                    i = memoryList.indexOf(f);
                    Rectangle rect2 = memory.getItems().get(i);
                    Point2D p2 = rect2.localToScene(rect2.getLayoutBounds().getMinX(), -28);
                    Circle c2 = new Circle(p2.getX(), p2.getY(), 1);
                    mainPane.getChildren().add(c2);
                    Line line = new Line(p1.getX() - 4 , p1.getY(), p2.getX(), p2.getY());
                    mainPane.getChildren().add(line);
                }
            }
        }
    }
}
