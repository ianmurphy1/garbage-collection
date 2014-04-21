package ui;

import gc.GarbageCollection;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Ian Murphy - 20057028
 *         Date: 19/04/2014
 */
public class Main extends Application{
    GarbageCollection gc;

    public Main() {
        gc = new GarbageCollection();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}
