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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sample.Entity.*;
import sample.Map.mapCreator;
import sample.helper.StateManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.MAGENTA;
import static javafx.scene.paint.Color.color;

public class gameController implements Initializable, EventHandler<KeyEvent> {

    @FXML
    Pane gamePane;
    @FXML
    Pane gpWrap;

    private EntityCreator ec = new EntityCreator();
    private Player mainPlayer = (Player) ec.getEntity("PLAYER");
    private AnimationTimer timer;
    private mapCreator mc = new mapCreator();

    private boolean left = false;
    private boolean right = false;

    public int i = 0;
    public int d = 0;


    BackgroundImage BI = new BackgroundImage(new Image("file:ressurser\\\\Hills.png", 805, 525, false, true), BackgroundRepeat.REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyHandlerInit(gamePane);
        mainPlayer.init(gamePane);
        setGamePaneWidth();
        mc.initMap(gamePane);

        gamePane.setBackground(new Background(BI));

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                i++;
                if (i%60==0)
                    System.out.println("runtime: "+ i/60);



                if (!gravitycheck(mainPlayer)) {
                    mainPlayer.gravity();
                }

                if (left) {
                    mainPlayer.MoveLeft();
                }

                if (right) {
                    mainPlayer.MoveRight();
                }
                toolowidk(mainPlayer,gamePane);
                
                mainPlayer.updatePlayerState();
                mainPlayer.renderPlayer();

                view(mainPlayer, gamePane);
                playerMapCollisionChecker2(mainPlayer);

            }
        };

        timer.start();

    }


    /*private int left = 0;
    private int right = 1;*/
    public void keyHandlerInit(Pane p) {
        p.setFocusTraversable(true);
        p.requestFocus();
        p.setOnKeyPressed(this::handle);
        p.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                //   mainPlayer.setDirection(5);
                this.left = false;
            } else if (e.getCode() == KeyCode.D) {
                //  mainPlayer.setDirection(5);
                this.right = false;
                System.out.println("BAAAAAAAAAAls");
            }
        });
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            System.out.println(keyEvent.toString());
        } else if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {
             this.left = true;

        } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {
             this.right = true;

        } else if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) {
            mainPlayer.setySpeed(2);

        } else if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) {
            if (gravitycheck(mainPlayer)) {
                // mainPlayer.setPosY(mainPlayer.getPosY()-4);
                mainPlayer.setySpeed(-7.5);
            }
        } else if (keyEvent.getCode() == KeyCode.F1) {
            mainPlayer.setPosX(350);
            mainPlayer.setPosY(350);
            mainPlayer.setySpeed(0);
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();

            StateManager.setState(StateManager.GameState.MAINMENU);
            stage.setScene(StateManager.update());

            stage.setResizable(false);
            stage.show();
        }
    }

    //Vet ikke lenger, men tror det eneste formålet til metoden er for å sjekke om gravity()
    //skal kjøres, derfor er Width og Height en pixel større enn metoden under.
    public boolean gravitycheck(Player p) {
        for (Rectangle mapPart : mc.getMap()) {
            if (mapPart.intersects(p.getX(), p.getY(), p.getWidth() + 0.5, p.getHeight() + 1)) {
                /*System.out.println(mapPart.intersects(p.getX(),p.getY(),p.getWidth()+0.5,p.getHeight()+1));
                System.out.println("MAPPART X: "+mapPart.getX());
                System.out.println("PLAYER X: "+p.getX());

                System.out.println("MAPPART Y: "+mapPart.getY());
                System.out.println("PLAYER Y: "+p.getY());*/
                //p.setySpeed(0);
                //System.out.println("PLAYER Y: "+p.getySpeed());

                return true;
            }
        }
        return false;
    }

    //kan forbedres ved å adde en for loop her, metoden må da ta inn en parameter som tilsvarer
    //xspeed. Antall iterasjoner i for løkken avhenger av denne parameteren med mer
    //dette kan gjøre at vi kan skjekke collision for hver piksel
    //TODO
    public void playerMapCollisionChecker2(Player p) {
        for (Rectangle mapPart : mc.getMap()) {
            if (mapPart.intersects(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight())) {

                // Collision Ovenifra
                if ((p.getPosY() + p.getHeight()) < (mapPart.getY() + 7.1) && p.getySpeed() > 0) {
                    p.setySpeed(0);
                    p.setPosY(mapPart.getY() - p.getHeight() - 1);
                    return;
                }

                // Collision Nedenifra
                if ((p.getPosY()) > (mapPart.getY() + mapPart.getHeight() / 2) && p.getySpeed() < 0) {
                    p.setySpeed(0);
                    p.setPosY(mapPart.getY() + mapPart.getHeight() + 1);
                    return;
                }

                // Bevegelseretning høyre
                if ((p.getPosX() + p.getWidth()) < (mapPart.getX() + mapPart.getWidth() / 2)) {
                    p.setxSpeed(0);
                    this.right=false;
                    p.setPosX(mapPart.getX() - p.getWidth() - 1);
                }

                // Bevegelseretning venstre
                if ((p.getPosX()) > (mapPart.getX() + mapPart.getWidth() / 2)) {
                    p.setxSpeed(0);
                    this.left=false;
                    p.setPosX(mapPart.getX() + mapPart.getWidth() + 1);
                }
            }
        }
    }

    //må kalles hver gang vi endrer map, dersom map størrelsene skal være forskjellige.
    //Setter gamePane witdh lik maplengden
    public void setGamePaneWidth() {
           gamePane.setPrefWidth(mc.getmapLength(1));
           System.out.println(mc.getmapLength(1));
           System.out.println(gamePane.getWidth());
    }

    //endrer visningfeltet
    public void view(Player p, Pane pa) {
        if (p.getPosX() > 300 && p.getPosX() < pa.getWidth() - 505) {
            pa.setLayoutX(-p.getPosX() + 300);
        } else if (p.getPosX() < 300) {
            pa.setLayoutX(0);
        }
    }

    //checker om mainplayer har falt ned i høøøøl
    public void toolowidk(Player p, Pane pa) {
        if (p.getPosY() > pa.getHeight() - 65) {
            p.setPosX(300);
            p.setPosY(300);
        }
    }
}
