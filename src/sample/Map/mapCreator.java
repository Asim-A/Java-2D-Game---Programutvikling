package sample.Map;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.Entity.Enemy;
import sample.Entity.EntityCreator;
import sample.Entity.Player;

import java.util.ArrayList;
import java.util.BitSet;

import static javafx.scene.paint.Color.DARKGREEN;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.color;


public class mapCreator{

    private static String[] LEVEL2MAP;
    private static String[] LEVEL3MAP;
    private static String[] LEVEL4MAP;

    private int scalar = 35;
    private int widthscalar = 70;

    private EntityCreator ec = new EntityCreator();

    private static final String Dit = "file:ressurser\\\\Dirt.png";
    Image D = new Image(Dit);
    final private ImagePattern Dirt = new ImagePattern(D);

    private static final String testPic = "file:ressurser\\\\Grass.png";
    Image img = new Image(testPic);
    final private ImagePattern Grass = new ImagePattern(img);

    public ArrayList<Rectangle> map=new ArrayList<>();
    public ArrayList<Enemy> Emap=new ArrayList<>();


    public static final String[] LEVEL1MAP = new String[] {
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000000000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000003000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000003000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000003000000000000000000000333000000000000000000000000000000000000000000000000000000000000000",
            "0000000000000000300000000000000040000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000000111110000400110001110000333000000000000000000000000000000000000000000000000000000000000000",
            "00000000000400000000001111130003330000000000000000000000000000400000000000000000000000000000000000000000",
            "000004000111000001111133330001130000000000000110000011110011111111001111111111111110111111111110000000",
            "11111110011311000003333333330003330000040000111330333033300000000000033033300000000000003330000000000000",
            "33333330033333222223333333332223331100111100003330333033311111111111133033311111111111103331111111111111"
            //bredde: 69 høyde:15
            //Hvis dere lager maps sørg for at de er rektangel formet slik  0000  ikke 000
            //        
    };

    public void initMap(Pane pe){

        for (int i = 0; i < mapCreator.LEVEL1MAP.length; i++) {
            String line = mapCreator.LEVEL1MAP[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Rectangle mapPart1 = mapMaker1(j*widthscalar,i*scalar,widthscalar,scalar, pe);
                       // mapPart1.setFill(Color.DARKGREEN);
                         mapPart1.setFill(Grass);
                        map.add(mapPart1);
                        break;
                    case '2':
                        mapPart1 = mapMaker1(j*widthscalar,i*scalar,widthscalar,scalar, pe);
                        mapPart1.setFill(color(0.0,0.20,0.50));
                        map.add(mapPart1);
                        break;
                    case '3':
                        mapPart1 = mapMaker1(j*widthscalar,i*scalar,widthscalar,scalar, pe);
                        //mapPart1.setFill(Color.rgb(97, 63, 16));
                        mapPart1.setFill(Dirt);
                       // map.add(mapPart1);
                        break;
                    case '4':
                        Enemy enemy = (Enemy) ec.getEntity("ENEMY");
                        enemy.setPosX(j*widthscalar);
                        enemy.setPosY(i*scalar);
                        enemy.initEnemy(pe);
                        Emap.add(enemy);
                }
            }
        }
    }

    public ArrayList<Enemy>  getEMap() {return Emap;}

    public Rectangle mapMaker1(int x, int y, int w, int h, Pane pe) {
        Rectangle rect = new Rectangle(x,y,w,h);
        pe.getChildren().add(rect);
        return rect;
    }

    public ArrayList<Rectangle> getMap() {
        return map;
    }

    //tar inn parameter som svarer til map 1,2 3 etc.. og returnerer lengden til mappet i piksler
    //tenkte å ha denne i en mer generel map klasse som inneholder string arrayene til de ulike levelene
    // har for nå bare adda det her
    public int getmapLength(int i) {
        int length=0;
        switch (i){
            case 1:
                length=widthscalar*LEVEL1MAP[0].length();
                break;
            case 2:
                length= widthscalar*LEVEL2MAP[0].length();
                break;
            case 3:
                length= widthscalar*LEVEL3MAP[0].length();
                break;
            case 4:
                length= widthscalar*LEVEL4MAP[0].length();
                break;
        }
        return length;
    }
}
