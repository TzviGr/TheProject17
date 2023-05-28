import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameScene extends JPanel implements KeyListener {
    private Player player;
    private boolean[] pressedKeys;
    private Obstacle[] obstacles;
    private ImageIcon[] obstacleIcons;
    public int newGame;

    private Image backgroundImage;
    private int page;
    private static final Color LEVEL_COLOR = Color.MAGENTA;
    private static final Color WIN_COLOR = Color.GREEN;
    private static final Color LOST_COLOR = Color.RED;
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int Spase = 4;
    public static final int GAME_SPEED_FAST = 3;
    public static final int GAME_SPEED_NORMAL = 10;
    public static final int GAME_SPEED_SLOW = 25;
    public static final int TOTAL_OBSTACLES = 5;
    public static final int Button_HEIGHT = 30;
    public static final int Button_WIDTH = 200;
    public static final int Button_X = 700;
    public static final int Button_Y = 100;

    public static final int HOME_PAGE = 1;
    public static final int GAME_PAGE = 2;
    public static int clock = 30;
    public static int wait=5;
    private JLabel titleLost;
    private JLabel titleWin;
    private JLabel title;
    private JButton startButton;
    private JButton seeInstructions;
    private JButton titleGame;
    //private JLabel power;
    public static int counter = 3000;;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private JTextArea instructionsTextArea;
    private JTextArea remind;
    public static Font labelFont;
    private int use=0;



    public Player getPlayer() {
        return this.player;
    }

    public GameScene() {
        this.page = HOME_PAGE;
        this.backgroundImage = Toolkit.getDefaultToolkit().getImage("C:\\Users\\zgryn\\Downloads\\.idea\\Space.png");
        this.player = new Player(0, 200);
        this.pressedKeys = new boolean[5];
        this.obstacles = new Obstacle[TOTAL_OBSTACLES];
        obstacleIcons=new ImageIcon[TOTAL_OBSTACLES];
        obstacleIcons[0] = new ImageIcon("C:\\Users\\zgryn\\OneDrive\\תמונות\\GettyImages.png");
        obstacleIcons[1] = new ImageIcon("C:\\Users\\zgryn\\OneDrive\\תמונות\\remote.png");
        obstacleIcons[2] = new ImageIcon("C:\\Users\\zgryn\\OneDrive\\תמונות\\meteor2.png");
        obstacleIcons[3] = new ImageIcon("C:\\Users\\zgryn\\OneDrive\\תמונות\\asteroid.png");
        obstacleIcons[4] = new ImageIcon("C:\\Users\\zgryn\\OneDrive\\תמונות\\Meteor1.png");
//        Color[] obstacleColors = {Color.RED, Color.GREEN, Color.white, Color.YELLOW, Color.CYAN};
//        int[] obstacleWidths = {30, 60, 40, 70, 50};
//        int[] obstacleHeights = {35, 60, 45, 70, 60};
        for (int i = 0; i < this.obstacles.length; i++) {
            Obstacle obstacle = new Obstacle(this.player.getX() + (i + 1) * 150, Window.WINDOW_HEIGHT / 2,obstacleIcons[i]);//, obstacleColors[i], obstacleWidths[i], obstacleHeights[i]);
            this.obstacles[i] = obstacle;
            obstacle.start();
        }
        this.mainGameLoop();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
        this.startTimerThread();
        this.setLayout(null);

        titleLost = new JLabel();
        labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 50);
        titleLost.setText("You lost");
        titleLost.setBounds(390, 250, 200, 50);
        titleLost.setFont(labelFont);
        titleLost.setForeground(LOST_COLOR);
        titleLost.setVisible(false);
        this.add(titleLost);


        titleWin = new JLabel();
        titleWin.setText("You won");
        titleWin.setBounds(390, 250, 200, 50);
        titleWin.setFont(labelFont);
        titleWin.setForeground(WIN_COLOR);
        titleWin.setVisible(false);
        this.add(titleWin);

        remind=new JTextArea();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        remind.setBounds(820, 0, + 150, 70);
        remind.setEditable(false);
        remind.setLineWrap(true);
        remind.setWrapStyleWord(true);
        remind.setText("You have the power!" +
                "\n\n You can use it by pressing the space bar.");
        remind.setFont(font);
        remind.setForeground(Color.BLACK);
        remind.setBackground(Color.CYAN);
        remind.setVisible(false);
        this.add(remind);

        if (this.page == HOME_PAGE) {
            JLabel titleGame = new JLabel();
            titleGame.setText("מבוכים בחלל:");
            titleGame.setBounds(350, 0, 500, 50);
            titleGame.setFont(labelFont);
            titleGame.setForeground(Color.white);
            titleGame.setVisible(true);
            this.add(titleGame);


            JTextArea instructionsTextArea = new JTextArea();
            startButton = new JButton("התחל");
            startButton.setBounds(Button_X, Button_Y, Button_WIDTH, Button_HEIGHT);
            startButton.setBackground(Color.CYAN);
            startButton.addActionListener(e -> {
                gameStarted = true;
                this.page = GAME_PAGE;
                startButton.setVisible(false);
                seeInstructions.setVisible(false);
                instructionsTextArea.setVisible(false);
                titleGame.setVisible(false);
                mainGameLoop();
            });
            this.add(startButton);

            seeInstructions = new JButton("הוראות המשחק");
            seeInstructions.setBounds(30, Button_Y, Button_WIDTH, Button_HEIGHT);
            instructionsTextArea.setBounds(30, Button_Y + 50, Button_WIDTH + 100, 400);
            instructionsTextArea.setEditable(false);
            instructionsTextArea.setLineWrap(true);
            instructionsTextArea.setWrapStyleWord(true);
            instructionsTextArea.setBackground(Color.BLACK);
            instructionsTextArea.setForeground(Color.blue);
            seeInstructions.setBackground(Color.pink);
            seeInstructions.addActionListener(e -> {
                this.add(instructionsTextArea);
                instructionsTextArea.setText("יש להזיז את הארבעת מקשי החצים כדי לברוח מהמטאורים שמרחפים בחלל החיצון.\n\n" +
                        "לפניך שלוש חיים,  כל פעם שתתנקש במטאור ירד לך חיים.\n\n" +
                        "כמו כן יש למשחק שישה שלבים, כל פעם שתצליח לברוח מהמטאורים ולעבור לצד השני תעלה שלב, והמשחק יתקשה בכל פעם שתעבור שלב כי המטאורים ירחפו מהר יותר.\n\n" +
                        "הזמן הקצוב שיש לך לעבור כל שלב הוא   30 שניות, אם לא תעבור בזמן ירד לך חיים.\n\n" +
                        "בנוסף לכך ניתן לך הכוח של הטיל. שמשמעותו הוא שבמידה והגעת לשלב השלישי ומעלה בלי להיפסל פעם אחת ינתן לך כוח מהירות גדולה יותר.\n\n"+
                        "השימוש בכוח הוא על ידי מקש הרווח עם החצים. כלומר המקש לחץ ימינה עם הרווח ינוע לך השחקן ימינה במהירות רבה יותר.\n\n"+
                        "וכך גם לשאר החצים.\n\n"+
                        "ניצחת - כאשר עברת את כל השלבים.\n\n" +
                        "הפסדת - כאשר ניגמר לך החיים.\n\n");
            });
            this.add(seeInstructions);
        }
    }
//    private void HomeGage(){
//        titleGame=new JButton("דף הבית");
//        titleGame.setBounds(Button_X, Button_Y, Button_WIDTH, Button_HEIGHT);
//        titleGame.setVisible(true);
//        titleGame.addActionListener(e -> {
//            titleGame.setVisible(false);
//            page = HOME_PAGE;
//            gameStarted = true;
//            gameOver=false;
//            startButton.setVisible(true);
//            seeInstructions.setVisible(true);
//            player.life = player.three;
//            player.level = player.level1;
//            titleLost.setVisible(false);
//            titleWin.setVisible(false);
//            clock=30;
//
//        });
//        this.add(titleGame);
//    }

    private void startTimerThread() {
        new Thread(() -> {
            while (gameStarted) {
                while (true) {
                    if (clock == 0) {
                        player.changeLife();
                        player.resetPosition();
                        clock = 30;
                        repaint();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clock--;
                }
            }
        }).start();
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        if (player.life > 0 && player.level <= 6 && clock > 0 && this.page == GAME_PAGE && this.gameStarted) {
            this.player.paint(graphics);
            for (int i = 0; i < this.obstacles.length; i++) {
                this.obstacles[i].paint(graphics);
            }
        } else if (this.page == HOME_PAGE) {
            for (int i = 0; i < this.obstacles.length; i++) {
                this.obstacles[i].paint(graphics);
            }
        }
    }
    private void mainGameLoop() {
        new Thread(() -> {
            while (gameStarted && !gameOver) {
                startTimerThread();
                labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
                title = new JLabel(player.life + "❤️   " + " level " + player.level + "      " + "Time:  " + "00" + ":" + clock);
                title.setBounds(350, 0, 500, Button_HEIGHT);
                title.setFont(labelFont);
                title.setForeground(LEVEL_COLOR);
                this.add(title);
                int waitBeforeRevive = 0;
                while (!gameOver) {
                    while (true) {
                        if (player.level > 6) {
                            titleWin.setVisible(true);
                            gameOver = true;
                            title.setVisible(false);
                            remind.setVisible(false);
                            //HomeGage();
                            break;
                        }
                        if (player.life == 0) {
                            titleLost.setVisible(true);
                            gameOver = true;
                            title.setVisible(false);
                           // HomeGage();

                            break;
                        }
                        if (player.life==3&&player.level>=3) {
                            remind.setVisible(true);
                        }
                        else {
                            remind.setVisible(false);
                        }
                        int dx = 0;
                        int dy = 0;
                        if (this.pressedKeys[DOWN] && this.player.getY() + (this.player.SIZE * 2.5 - 10) <= Window.WINDOW_HEIGHT) {
                            dy++;
                        }
                        if (this.pressedKeys[UP] && this.player.getY() >= 0) {
                            dy--;
                        }
                        if (this.pressedKeys[RIGHT]) {
                            dx++;
                        }
                        if (this.pressedKeys[LEFT] && this.player.getX() > 0) {
                            dx--;
                        }
                        if (this.pressedKeys[Spase] && this.player.getY() + (this.player.SIZE * 2.5 - 10) <= Window.WINDOW_HEIGHT&&this.pressedKeys[DOWN] && this.player.getY() + (this.player.SIZE * 2.5 - 10) <= Window.WINDOW_HEIGHT&&player.life==3&&player.level>=3) {
                            dy=dy+3;
                        }
                        if (this.pressedKeys[Spase] && this.player.getY() >= 0&&this.pressedKeys[UP] && this.player.getY() >= 0&&player.life==3&&player.level>=3) {
                            dy=dy-3;
                        }
                        if (this.pressedKeys[Spase]&&this.pressedKeys[RIGHT]&&player.life==3&&player.level>=3) {
                            dx=dx+3;
                        }
                        if (this.pressedKeys[Spase] && this.player.getX() > 0&&this.pressedKeys[LEFT] && this.player.getX() > 0&&player.life==3&&player.level>=3) {
                            dx=dx-3;
                        }
                        this.player.move(dx, dy);

                        Rectangle playerRect = this.player.calculateRectangle();
                        for (int i = 0; i < this.obstacles.length; i++) {
                            Rectangle obstacleRect = this.obstacles[i].calculateRectangle();
                            if (Utils.checkCollision(playerRect, obstacleRect)) {
                                this.player.kill();
                            }
                        }
                        if (!this.player.isAlive()) {
                            waitBeforeRevive++;
                            if (waitBeforeRevive > 300) {
                                this.player.changeLife();
                                clock = 30;
                                title.setText(player.life + "❤️   " + " level " + player.level + "      " + "Time:  " + "00" + ":" + clock);
                                this.player.revive();
                                waitBeforeRevive = 0;

                            }
                        }
                        this.player.overStepsTheBounds();
                        title.setText(player.life + "❤️   " + " level " + player.level + "      " + "Time:  " + "00" + ":" + clock);
                        repaint();
                        try {
                            Thread.sleep(GAME_SPEED_FAST);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        Integer toPress = null;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toPress = RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            toPress = LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            toPress = UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            toPress = DOWN;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE){
            toPress=Spase;
        }
        if (toPress != null) {
            this.pressedKeys[toPress] = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        Integer toRelease = null;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toRelease = RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            toRelease = LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            toRelease = UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            toRelease = DOWN;
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE){
            toRelease=Spase;
        }
        if (toRelease != null) {
            this.pressedKeys[toRelease] = false;
        }
    }
}
