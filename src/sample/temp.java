package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class temp {

private Thread thread;



        @FXML
        public Canvas canvas;

        public GraphicsContext gc;

        public void render(){
            gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.BLUE);
            gc.fillRect(50,50,50,50);
        }

}