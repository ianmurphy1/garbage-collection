package ui;

import fish.FishType;
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

    public Scene getScene() {
        return scene;
    }

    public void setGc() {
        gc = new GarbageCollection();
    }

    public MainController getMc() {
        return mc;
    }

    public MemoryController getMemc() {
        return memc;
    }

    public RefController getRefc() {
        return refc;
    }

    public GCController getGcCon() {
        return gcCon;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainFxmlLoader = new FXMLLoader(Main.class.getResource("layout/main.fxml"));
        Parent root = (Parent) mainFxmlLoader.load();
        mc = (MainController) mainFxmlLoader.getNamespace().get("controller");
        mc.setApp(this);

        memc = (MemoryController) mainFxmlLoader.getNamespace().get("createtabController");
        memc.setApp(this);

        refc = (RefController) mainFxmlLoader.getNamespace().get("reftabController");
        refc.setApp(this);

        gcCon = (GCController) mainFxmlLoader.getNamespace().get("gctabController");
        gcCon.setApp(this);

        //assignRefController = (AssignReferencesController) mainFxmlLoader.getNamespace().get("assignRefsTabController");
        //assignRefController.setApp(this);

        //garbCollController = (GarbageCollectController) mainFxmlLoader.getNamespace().get("garbageCollectTabController");
        //garbCollController.setApp(this);

       // mainController = (MainController) mainFxmlLoader.getNamespace().get("controller");
        //mainController.setApp(this);

        //customized css stylesheet
        scene = new Scene(root, 700, 450);
        String stylesheet = getClass().getResource("layout/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
        setGc();
    }

    public GarbageCollection getGC() {
        return gc;
    }
}
