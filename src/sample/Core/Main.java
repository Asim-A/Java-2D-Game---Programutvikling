package sample.Core;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Tools.StateManager;

public class Main extends Application {

    public final static  double SCREEN_WIDTH = 640.0;
    public final static  double SCREEN_HEIGHT = 480.0;
    public final static String GAME_NAME = "Tiyareed";
    private static StateManager stateManager = new StateManager();

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);
        primaryStage.setTitle(GAME_NAME);

        primaryStage.setScene(StateManager.update());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static StateManager getStateManager(){
        return stateManager;
    }
}

