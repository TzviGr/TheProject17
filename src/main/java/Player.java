import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player{
    private int x;
    private int y;
    public static final int SIZE = 50;
    private boolean alive;

    public  int three = 3;
    private static final int two = 2;
    private static final int own = 1;
    public int life = three;
    public   int level1 = 1;
    private static final int level2 = 2;
    private static final int level3 = 3;
    private static final int level4 = 4;
    private static final int level5 = 5;
    private static final int level6 = 6;
    public int level = 1;


    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    public int getLevel() {
        return level;
    }

    public void changeLife() {
        if (this.life == three) {
            this.life = two;
        } else if (this.life == two) {
            this.life = own;
        } else {
            this.life = 0;
        }
    }

    public void paint(Graphics graphics) {
        if (this.alive) {
            ImageIcon imageIcon=new ImageIcon("C:\\Users\\zgryn\\Downloads\\.idea\\missile.png");
            imageIcon.paintIcon(null,graphics,this.x,this.y);
        }
    }

    public void kill() {
        this.alive = false;

    }


    public void revive() {
        this.x = 0;
        this.y = 0;
        this.alive = true;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Rectangle calculateRectangle() {
        ImageIcon imageIcon=new ImageIcon("C:\\Users\\zgryn\\Downloads\\.idea\\missile.png");
        return new Rectangle(this.x, this.y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void overStepsTheBounds() {
        if (this.x > Window.WINDOW_WIDTH) {
            kill();
            revive();
            Obstacle.boolSpeed = true;
            if (this.level==level1) {
                this.level=level2;
            }
            else if (this.level==level2){
                this.level=level3;
            }
            else if (this.level==level3){
                this.level=level4;
            }
            else if (this.level==level4){
                this.level=level5;
            }
            else if (this.level==level5){
                this.level=level6;
            }
            else {
                this.level=7;
            }
            GameScene.clock=30;
        }
    }
    public void resetPosition() {
        this.x = 0;
        this.y = 0;
    }
}
