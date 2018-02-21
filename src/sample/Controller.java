package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static sample.Main.*;

public class Controller implements Runnable{
    @FXML public Button startButton;
    @FXML public Button hjelpButton;
    @FXML public Button exitButton;
    @FXML public Canvas canvas;

    private Thread thread;
    String gameScreen = "gameScene.fxml";
    String hjelpScreen = "hjelpScene.fxml";
    String screenCompare = "";
    private boolean running;
    private int FPS = 60;
    private double averageFPS;

    public void setStartScene(ActionEvent e) throws IOException {
        String source1 = e.getSource().toString();
        String source2 = getId(source1,11);
        System.out.println(source2);

        Parent root = FXMLLoader.load(getClass().getResource(gameScreen));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene startScene = new Scene(root,SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setResizable(false);
        stage.setTitle(GAME_NAME);
        stage.setScene(startScene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            exitScreen();
        });

        threadStart();

    }

    //DIAGNOSE VERKTØY, SJEKKER ID TIL ELEMENT//
    public String getId(String a, int word){
        char[] result = new char[word];
        char[] chars = a.toCharArray();
        int counter = 0;
        int x = 0;

        for(int i = 0 ; i < 21 ; i++){

            if(chars[i] == ','){
                x = 0;
                break;
            }

            if(x == 1){
                result[counter] = chars[i];
                counter++;
            }

            if(chars[i] == '='){
                x = 1;
            }


        }
        String b = new String(result);
        return b;
    }

    public void exitScreen(){
        System.exit(0);
    }

    public void threadStart(){
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }

        else{
            return;
        }
    }

    @Override
    public void run() {
        System.out.println("thread er laget");
        running = true;

        //initialize();

        //Variabler for å måle tid før og etter update() og render() har kjørt
        long startTid;
        long prosessTid;
        long venteTid;
        long totalTid = 0;

        int frameCount = 0;
        int maksFrameCount = FPS;

        //tiden i millisekunder som man må for å få riktig FPS
        long prefTid = 1000/FPS;

        // GAME LOOP
        while(running){

            startTid = System.nanoTime();


            update();
            render();

            prosessTid = (System.nanoTime()-startTid)/(1000000); //Tiden det tok å kjøre update og render
            venteTid = prefTid - prosessTid; //hvor mye du er nødt til å vente for å få ca. 60 fps

            try{
                Thread.sleep(venteTid);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            totalTid += System.nanoTime() - startTid;
            frameCount++;
            if(frameCount == maksFrameCount){
                averageFPS = 1000.0 /  ((totalTid/frameCount)/1000000.0);
                frameCount = 0;
                totalTid = 0;
            }

        }
    }

    private void initialize(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(50,50,50,50);
    }

    private void update(){
        System.out.println("FPS: " + (int)averageFPS);
    }

    private void render(){

    }

    }
