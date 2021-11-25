import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ReadPendingException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Board extends JPanel {

    static private boolean drawn = false;
    private int windowSize;
    private int margin;
    private int boardSize;
    private int tileMargin = 10;
    private int tileSize = 80;
    static private Tile[][] tiles = new Tile[4][4];
    Random rand = new Random();

    private GameMechanics mechs = new GameMechanics();
    private Action left = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            leftPressed();
        }
    };
    private Action up = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            upPressed();
        }
    };
    private Action down = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            downPressed();
        }
    };
    private Action right = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            rightPressed();
        }
    };

    public Board(int windowSize) {
        this.windowSize = windowSize;
        this.margin = windowSize / 5;
        this.boardSize = tileSize * 4 + tileMargin * 5;
        this.setBackground(Color.decode("#e9e2d2"));
        setupKeybindings();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j] = new Tile(0, i, j, tileSize);
            }
        }

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ClearSans-Bold.ttf")));

            // InputStream stream =
            // ClassLoader.getSystemClassLoader().getResourceAsStream("ClearSans-Bold.ttf");
            // font13 = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(13f);
            // font18 = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(18f);
        } catch (IOException | FontFormatException e) {
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        drawTitle(g2d);
        drawScore(g2d);
        createTiles(g2d);
    }

    public void drawTitle(Graphics g) {
        g.setFont(new Font("MonoSpaced", Font.BOLD, 50));
        g.setColor(Color.decode("#776e65"));
        g.drawString("2048", margin, 60);
    }

    public void drawBackground(Graphics g) {
        g.setColor(Color.decode("#faf8ef"));
        g.fillRect(0, 0, windowSize, windowSize);
    }

    public void drawScore(Graphics g) {
        int rectW = 90;
        int rectH = 50;
        int posX = margin + boardSize - rectW;
        int posY = 20;

        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(posX, posY, rectW, rectH, 5, 5);
        g.setFont(new Font("MonoSpaced", Font.BOLD, 13));
        g.setColor(Color.decode("#eee4da"));
        g.drawString("SCORE", posX + rectW / 4, posY + 20);
        g.setFont(new Font("MonoSpaced", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(3452), posX + rectW / 4, posY + 40);
    }

    public void createTiles(Graphics g) {

        g.translate(margin, margin);
        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(0, 0, boardSize, boardSize, 8, 8);

        List<Integer> randPosList = IntStream.range(0, 16).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(randPosList);

        if (!drawn) {
            setRandTile(randPosList.get(0));
            setRandTile(randPosList.get(1));
            drawn = true;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].paintTiles(g);
            }
        }
    }

    public void setRandTile(int randPos) {

        int randNum = rand.nextInt(4);
        randNum = (randNum < 3) ? 2 : 4;

        int col = randPos % 4;
        int row = Math.floorDiv(randPos, 4);

        tiles[col][row].setNumber(randNum);
    }

    public void setupKeybindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        this.getActionMap().put("left", left);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        this.getActionMap().put("up", up);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        this.getActionMap().put("down", down);
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        this.getActionMap().put("right", right);
    }

    public void leftPressed() {
        System.out.println("LEFT");
    }

    public void upPressed() {
        System.out.println("UP");
    }

    public void downPressed() {
        System.out.println("DOWN");
    }

    public void rightPressed() {
        System.out.println("RIGHT");
    }

}
