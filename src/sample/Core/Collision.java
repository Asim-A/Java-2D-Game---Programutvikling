package sample.Core;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.Entity.Bullet;
import sample.Entity.EnemyCircle;
import sample.Entity.EnemyRect;
import sample.Entity.Player;
import sample.Map.mapCreator;

import java.util.ArrayList;

public class Collision {

    //Vet ikke lenger, men tror det eneste formålet til metoden er for å sjekke om gravity()
    //skal kjøres, derfor er Width og Height en pixel større enn metoden under.
    public boolean gravityCheck(Player p, mapCreator mc) {
        for (Rectangle mPart : mc.getMap()) {
            if (mPart.intersects(p.getX(), p.getY(), p.getWidth() + 0.5, p.getHeight() + 0.5)) {
                return true;
            }
        }
        return false;
    }

    public void PlayerEnemyColl(Player p) {
        for (EnemyRect enemyRect : mapCreator.getERMap()) {
            if (p.intersects(enemyRect.getBoundsInParent())) {
                p.setPosX(110);
                p.setPosY(300);
            }
        }
        for (EnemyCircle enemyCircle : mapCreator.getECMap()) {
            if (p.intersects(enemyCircle.getBoundsInParent())) {
                p.setPosX(110);
                p.setPosY(300);
            }
        }
    }

    public void playerCollisionY(Player p, mapCreator mc) {
        for (Rectangle mPart : mc.getMap()) {
            if (mPart.intersects(p.getPosX(), p.getPosY(), p.getWidth(), p.getHeight())) {
                // Collision Ovenifra
                if ((p.getPosY() + p.getHeight()) < (mPart.getY() + p.getMaxySpeed()) && p.getySpeed() > 0) {
                    p.setySpeed(0);
                    p.setPosY(mPart.getY() - p.getHeight() - 0.5);
                    return;
                }
                // Collision Nedenifra
                if ((p.getPosY()) > (mPart.getY() + mPart.getHeight() / 2) && p.getySpeed() < 0) {
                    p.setySpeed(0);
                    p.setPosY(mPart.getY() + mPart.getHeight() + 0.5);
                    return;
                }
            }
        }
    }

    public void PlayerCollisionX(int x, Player p, mapCreator mc) {
        int speed = 4;
        for (int i = 1; i <= x; i++) {
            for (Rectangle mPart : mc.getMap()) {
                if (mPart.intersects(p.getPosX() + speed, p.getPosY(), p.getWidth(), p.getHeight())) {
                    // Bevegelseretning høyre
                    if (p.KeyD) {
                        speed--;
                        p.setPosX(mPart.getX() - p.getWidth() - 1);
                    }
                }

                if (mPart.intersects(p.getPosX() - speed, p.getPosY(), p.getWidth(), p.getHeight())) {
                    // Bevegelseretning venstre
                    if (p.KeyA) {
                        speed--;
                        p.setPosX(mPart.getX() + mPart.getWidth() + 1);
                    }
                }
            }
        }
        if (p.KeyD) {
            p.MoveRight(speed);
        } else if (p.KeyA) {
            p.MoveLeft(speed);
        }
    }
}
