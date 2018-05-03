package sample.Core;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.Entity.*;
import sample.Map.mapCreator;
import sample.helper.StateManager;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class gameController implements Initializable, EventHandler<KeyEvent>, Serializable {

    @FXML Pane gamePane;
    @FXML Pane gpWrap;

    private EntityCreator ec = new EntityCreator();
    private Player mainPlayer = (Player) ec.getEntity("PLAYER");
    private mapCreator mc = new mapCreator();
    private AnimationTimer timer;

    BackgroundImage BI= new BackgroundImage(new Image("file:ressurser\\\\Hills.png",805,525,false,true),BackgroundRepeat.REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyHandlerInit(gamePane);
        mainPlayer.init(gamePane);
        mc.initMap(gamePane);

        gamePane.setBackground(new Background(BI));

        System.out.println("stage: " + gpWrap.getWidth());
        System.out.println("scene: " + gamePane.getWidth());

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if(mainPlayer.getPosX()>300 && mainPlayer.getPosX()<gamePane.getWidth()-505) {
                    gamePane.setLayoutX(-mainPlayer.getPosX()+300);
                } else if (mainPlayer.getPosX()<300) {
                    gamePane.setLayoutX(0);
                }

               // System.out.println(mainPlayer.getPosX());
                mainPlayer.updatePlayerState();
                mainPlayer.renderPlayer();
                mc.playerMapCollisionChecker(mainPlayer);
            }
        };

        timer.start();

    }

    public void keyHandlerInit(Pane p){
        p.setFocusTraversable(true);
        p.requestFocus();
        p.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    System.out.println(e.toString());
                    break;
                case A:
                case LEFT:
                    mainPlayer.setDirection(0);
                    break;
                case D:
                case RIGHT:
                    mainPlayer.setDirection(1);
                    break;
                case S:
                case DOWN:
                    mainPlayer.setPosY(5);
                    break;
                case W:
                case UP:
                    mainPlayer.setPosY(-20);
                    break;
                case F1:
                    mainPlayer.setPosX(320 - mainPlayer.getPosX());
                    mainPlayer.setPosY(240 - mainPlayer.getPosY());
                    mainPlayer.setySpeed(0);
                    break;
                case F2:
                    try {
                        playerSave(mainPlayer);
                        System.out.println(mainPlayer.toString());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case F3:
                    try{
                        loadSave();
                        mainPlayer.renderPlayer();
                        System.out.println(mainPlayer.toString());
                    }catch (Exception e2){
                        System.out.println(e);
                    }
                    break;
                case ESCAPE:
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                    StateManager.setState(StateManager.GameState.MAINMENU);
                    stage.setScene(StateManager.update());

                    stage.setResizable(false);
                    stage.show();
                    break;
            }
        });

        p.setOnKeyReleased(e -> {
            switch(e.getCode()){
                case A:
                    mainPlayer.setDirection(5);
                    break;
                case D:
                    mainPlayer.setDirection(5);
                    break;
            }
        });
    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }

    public void playerSave(Player p) throws IOException {
        FileOutputStream fo = new FileOutputStream("playersave.save");
        ObjectOutputStream out = new ObjectOutputStream(fo);
        out.writeObject(p);
        out.close();
        fo.close();
        System.out.println("Save Complete");
    }

    public void loadSave() throws Exception{
        gamePane.getChildren().remove(mainPlayer);
        FileInputStream fi = new FileInputStream("playersave.save");
        ObjectInputStream in = new ObjectInputStream(fi);
        mainPlayer = (Player) in.readObject();
        in.close();
        fi.close();
        mainPlayer.init(gamePane);
        System.out.println("Load Complete");
    }

}
