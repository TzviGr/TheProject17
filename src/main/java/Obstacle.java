import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Obstacle extends Thread {
    private int x;
    private int y;
    public static final int SIZE = 30;
    public static boolean boolSpeed = false;
    private Color color;
    private int width;
    private int height;
    private ImageIcon imageIcon;

    public Obstacle(int x, int y, ImageIcon image) {//,Color color,int width,int height) {
        this.x = x;
        this.y = y;
        this.imageIcon = new ImageIcon(image.getImage());
//        this.color=color;
//        this.width=width;
//        this.height=height;
    }

    public boolean getBoolSpeed() {
        return this.boolSpeed;
    }

    public void run() {
        int speed1 = 3;
        int speed2 = 30;
        int s1 = 10;
        int s2 = 2;
        Random random = new Random();
        boolean goingUp = random.nextBoolean();
        int speed = random.nextInt(speed1, speed2);
        int r = random.nextInt(1, 3);
        while (true) {
            if (!goingUp && r == 1) {
                this.x++;
                this.y++;
            } else if (goingUp && r == 1) {
                this.x--;
                this.y--;
            }
            if (!goingUp && r == 2) {
                this.y++;
            } else if (goingUp && r == 2) {
                this.y--;
            }
            if (boolSpeed && speed > 10) {
                speed = speed - s1;
                boolSpeed = false;
            } else if (boolSpeed && speed < 10 && speed > 2) {
                speed = speed - s2;
                boolSpeed = false;
            }
            Utils.sleep(speed);
            if (this.y + SIZE + SIZE > Window.WINDOW_HEIGHT || this.x + SIZE + SIZE > Window.WINDOW_WIDTH) {
                goingUp = true;
            }
            if (this.y < 0 || this.x < 0) {
                goingUp = false;
            }
        }
    }

    public void paint(Graphics graphics) {
        imageIcon.paintIcon(null, graphics, this.x, this.y);


    }

    public Rectangle calculateRectangle() {
        return new Rectangle(this.x, this.y, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }
}

