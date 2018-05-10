package sample.Map;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Entity.Player;
import java.util.ArrayList;
import static javafx.scene.paint.Color.color;

public class mapCreator{

    private int scalar = 35;
    public  int levelWidth;
    public ArrayList<Rectangle> map=new ArrayList<>();

    private String[] LEVELARRAY = Map.getMapArray();

    public void initMap(Pane pe){
        levelWidth = LEVELARRAY[0].length() * scalar;
        for (int i = 0; i < LEVELARRAY.length; i++) {
            String line = LEVELARRAY[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Rectangle mapPart1 = mapMaker1(j*scalar,i*scalar,scalar,scalar, pe);
                        mapPart1.setFill(Color.rgb(randomColor(),randomColor(),randomColor()));
                        map.add(mapPart1);
                        break;
                    case '2':
                        mapPart1 = mapMaker1(j*scalar,i*scalar,scalar,scalar, pe);
                        mapPart1.setFill(color(0.0,0.20,0.50));
                        map.add(mapPart1);
                        break;
                }
            }
        }

    }
    public Rectangle mapMaker1(int x, int y, int w, int h, Pane pe) {
        Rectangle rect = new Rectangle(x,y,w,h);
        pe.getChildren().add(rect);
        return rect;
    }

   public void playerMapCollisionChecker(Player p){
       for(Rectangle mapPart:map){
           if(p.intersects(mapPart.getBoundsInLocal())){
               p.setySpeed(0);
               p.setxSpeed(0);
               p.setDirection(5);
               System.out.println("h");
               return;
           }
       }
   }

   public int randomColor(){
       return (int) Math.floor(Math.random() * 256);
   }

    //tar inn parameter som svarer til map 1,2 3 etc.. og returnerer lengden til mappet i piksler
    //tenkte å ha denne i en mer generel map klasse som inneholder string arrayene til de ulike levelene
    // har for nå bare adda det her
    public int getmapLength() {
        return scalar*LEVELARRAY[0].length();
    }

    public ArrayList<Rectangle> getMap() {
        return map;
    }

    public void setMap(ArrayList<Rectangle> map) {
        this.map = map;
    }
}



        /*
        switch (i){
            case 1:
                length= scalar*LEVEL1MAP[0].length();
                break;
            case 2:
                length= scalar*LEVEL2MAP[0].length();
                break;
            case 3:
                length= scalar*LEVEL3MAP[0].length();
                break;
            case 4:
                length= scalar*LEVEL4MAP[0].length();
                break;
        }
        return length;
    }

    private static String[] LEVEL2MAP;
    private static String[] LEVEL3MAP;
    private static String[] LEVEL4MAP;
    */