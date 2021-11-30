import java.awt.*;
import java.io.File;
import java.io.IOException;
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

    static private boolean firstPaint = true; // the first time the panel has been painted or not
    static private int gameState = 0; // -1 = end of game, 0 = game not started, 1 = game has begun
    private int windowSize;
    private int margin;
    private int boardSize;
    private int tileMargin = 10;
    private int tileSize = 80;
    static private Tile[][] tiles = new Tile[4][4];
    private int score = 0;
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

    private Action restartGame = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            restart();
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
        paintBackground(g2d);
        paintTitle(g2d);
        paintScore(g2d);
        paintTiles(g2d);
        paintInstructions(g2d);
    }

    public void paintBackground(Graphics g) {
        g.setColor(Color.decode("#faf8ef"));
        g.fillRect(0, 0, windowSize, windowSize);
    }

    public void paintTitle(Graphics g) {
        g.setFont(new Font("MonoSpaced", Font.BOLD, 50));
        g.setColor(Color.decode("#776e65"));
        g.drawString("2048", margin, 60);
    }

    public void paintScore(Graphics g) {
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
        g.drawString(String.valueOf(score), posX + rectW / 4, posY + 40);
    }

    public void paintTiles(Graphics g) {

        g.translate(margin, margin);
        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(0, 0, boardSize, boardSize, 8, 8);

        if (gameState == 1) {
            addRandTile(1);
        } else if (gameState == 0 && !firstPaint) {
            addRandTile(2);
            gameState = 1;
        }

        if (firstPaint) {
            firstPaint = false;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].paintTiles(g);
            }
        }
    }

    public void paintInstructions(Graphics g) {
        int posX = 0;
        int posY = boardSize + 30;

        g.setColor(Color.decode("#776e65"));
        g.setFont(new Font("MonoSpaced", Font.BOLD, 18));
        g.drawString("- Use arrow keys to move tiles", posX, posY);
        g.drawString("- Press 'R' to restart game", posX, posY + 20);
    }

    public void addRandTile(int numOfTiles) {
        List<Integer> randPosList = IntStream.range(0, 16).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(randPosList);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getNumber() != 0) {
                    int posIndex = (i + 1) * (j + 1);
                    randPosList.remove(Integer.valueOf(posIndex - 1));
                }
            }
        }

        for (int i = 0; i < numOfTiles; i++) {
            if (i < randPosList.size()) {
                setRandTile(randPosList.get(i));
            }
        }

    }

    public void setRandTile(int randPos) {

        int randNum = rand.nextInt(4);
        randNum = (randNum < 3) ? 2 : 4;

        int row = Math.floorDiv(randPos, 4);
        int col = randPos % 4;

        tiles[row][col].setNumber(randNum);
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
        this.getInputMap().put(KeyStroke.getKeyStroke('R', 0), "restartGame");
        this.getActionMap().put("restartGame", restartGame);
    }

    public void leftPressed() {
        System.out.println("LEFT");
        mechs.handleLeft(tiles);
        this.repaint();
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

    public void restart() {
        System.out.println("RESTART");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].setNumber(0);
            }
        }
        score = 0;
        gameState = 0;
        this.repaint();
    }

}
