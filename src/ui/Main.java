package ui;

import gc.GarbageCollection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controllers.GCController;
import ui.controllers.MainController;
import ui.controllers.MemoryController;
import ui.controllers.RefController;

/**
 * Class which runs the app.
 *
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Main extends Application{
    GarbageCollection gc;
    MainController mc;
    MemoryController memc;
    RefController refc;
    GCController gcCon;
    Scene scene;

    /**
     * Method that returns this objects scene
     * @return App's Scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructor for the application which creates a new
     * instance of the garbage collector
     */
    public Main() {
        this.gc = new GarbageCollection();
    }

    /**
     * Method that returns the app's MainController
     * @return App's MainController
     */
    public MainController getMc() {
        return mc;
    }

    /**
     * Method that returns the app's MemoryController
     * @return App's MemoryController
     */
    public MemoryController getMemc() {
        return memc;
    }

    /**
     * Method that returns the app's reference controller
     * @return App's ReferenceController
     */
    public RefController getRefc() {
        return refc;
    }

    /**
     * Method that returns the app's garbage collection
     * controller
     * @return App's GarbageCollectionController
     */
    public GCController getGcCon() {
        return gcCon;
    }

    /**
     * Main Method of the application that starts the program
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Method that gets called when the application starts.
     * Gets the apps controllers and sets their app to be this instance of
     * Main. Sets the size of the window.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainFxmlLoader = new FXMLLoader(Main.class.getResource("layout/main.fxml"));
        Parent root = mainFxmlLoader.load();
        mc = (MainController) mainFxmlLoader.getNamespace().get("controller");
        mc.setApp(this);

        memc = (MemoryController) mainFxmlLoader.getNamespace().get("createtabController");
        memc.setApp(this);

        refc = (RefController) mainFxmlLoader.getNamespace().get("reftabController");
        refc.setApp(this);

        gcCon = (GCController) mainFxmlLoader.getNamespace().get("gctabController");
        gcCon.setApp(this);

        scene = new Scene(root, 700, 500);
        String stylesheet = getClass().getResource("layout/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setTitle("Garbage Collection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Return the app's instance of the garbage collector
     * @return Garbage Collector
     */
    public GarbageCollection getGC() {
        return gc;
    }
}
