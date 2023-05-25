import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {

    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH = 1000;
    public static final int Button_HEIGHT = 30;
    public static final int Button_WIDTH = 200;
    public static final int Button_X = 700;
    public static final int Button_Y = 100;


    public void shoWindow() {
        this.setVisible(true);
    }

    public Window() {
        this.setResizable(false);
        GameScene gameSCen = new GameScene();
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(gameSCen);
        this.setLayout(null);
        setContentPane(gameSCen);
        gameSCen.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);



    }
}
