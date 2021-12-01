import java.awt.*;
import java.io.File;
import java.io.FileWriter;
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
    static private int gameState = 0; // -1 = game lost, 0 = game not started, 1 = game live
    private int windowSize;
    private int margin;
    private int boardSize;
    private int widgetMargin;
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

    private Action restartGame = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            restart();
        }
    };

    public Board(int windowSize) {
        this.windowSize = windowSize;
        this.margin = windowSize / 5;
        this.widgetMargin = 35;
        this.boardSize = tileSize * 4 + tileMargin * 5;
        this.setBackground(Color.decode("#e9e2d2"));
        setupKeybindings();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j] = new Tile(0, i, j);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        paintBackground(g2d);
        paintTitle(g2d);
        paintScore(g2d);
        paintHighScore(g2d);
        paintTiles(g2d);
        paintInstructions(g2d);
    }

    public void paintBackground(Graphics g) {
        g.setColor(Color.decode("#faf8ef"));
        g.fillRect(0, 0, windowSize, windowSize);
    }

    public void paintTitle(Graphics g) {
        Font font = new Font("MonoSpaced", Font.BOLD, 52);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textMarginY = -(int) fontMetrics.getLineMetrics("2048", g).getBaselineOffsets()[2];

        g.setFont(font);
        g.setColor(Color.decode("#776e65"));
        g.drawString("2048", margin, widgetMargin + textMarginY - 10);
    }

    public void paintScore(Graphics g) {
        int rectW = 100;
        int rectH = 50;
        int posX = margin + boardSize - 2 * rectW - 10;
        int posY = widgetMargin;

        int fontSizeScore = 18;

        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(posX, posY, rectW, rectH, 5, 5);
        g.setFont(new Font("MonoSpaced", Font.BOLD, 13));
        g.setColor(Color.decode("#eee4da"));
        g.drawString("SCORE", posX + rectW / 6, posY + 20);
        g.setFont(new Font("MonoSpaced", Font.BOLD, fontSizeScore));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(mechs.score), posX + rectW / 6, posY + 40);
    }

    public void paintHighScore(Graphics g) {
        String highscore = "";
        try {
            File scoreFile = new File("highscore.txt");
            Scanner reader = new Scanner(scoreFile);
            highscore = reader.nextLine();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int rectW = 100;
        int rectH = 50;
        int posX = margin + boardSize - rectW;
        int posY = widgetMargin;

        int fontSizeScore = 18;

        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(posX, posY, rectW, rectH, 5, 5);
        g.setFont(new Font("MonoSpaced", Font.BOLD, 13));
        g.setColor(Color.decode("#eee4da"));
        g.drawString("BEST", posX + rectW / 6, posY + 20);
        g.setFont(new Font("MonoSpaced", Font.BOLD, fontSizeScore));
        g.setColor(Color.WHITE);
        g.drawString(highscore, posX + rectW / 6, posY + 40);
    }

    public void paintTiles(Graphics g) {

        g.translate(margin, margin);
        g.setColor(Color.decode("#bbada0"));
        g.fillRoundRect(0, 0, boardSize, boardSize, 11, 11);

        List<Integer> emptyTiles = getEmptyTiles();

        if (emptyTiles.size() > 0) {
            if (gameState == 1) {
                addRandTile(1, emptyTiles);
            } else if (gameState == 0 && !firstPaint) {
                addRandTile(2, emptyTiles);
                gameState = 1;
            }
        } else {
            gameState = -1;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].paintTiles(g, i, j, tileSize);
            }
        }

        if (!firstPaint) {
            gameState = mechs.checkGameOver(tiles);
        }
        if (firstPaint) {
            firstPaint = false;
        }

        if (gameState == -1 || gameState == 2) {
            paintEndgame(g);
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

    public void paintEndgame(Graphics g) {
        g.setColor(new Color(250, 248, 239, 150));
        g.fillRoundRect(0, 0, boardSize, boardSize, 11, 11);

        String message;
        if (gameState == -1) {
            message = "Game over!";
        } else {
            message = "You won!";
        }

        Font font = new Font("MonoSpaced", Font.BOLD, 50);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textMarginX = fontMetrics.stringWidth(message);
        g.setFont(font);
        g.setColor(Color.decode("#776e65"));
        g.drawString(message, (boardSize - textMarginX) / 2 + 5, boardSize / 2);
    }

    public List<Integer> getEmptyTiles() {
        List<Integer> randPosList = IntStream.range(0, 16).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(randPosList);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getNumber() != 0) {
                    int posIndex = (i) * 4 + (j);
                    randPosList.remove(Integer.valueOf(posIndex));
                }
            }
        }

        return randPosList;
    }

    public void addRandTile(int numOfTiles, List<Integer> randPosList) {
        for (int i = 0; i < numOfTiles; i++) {
            if (i < randPosList.size()) {
                int randPos = randPosList.get(i);
                setRandTile(randPos);
            }
        }
    }

    public void setRandTile(int randPos) {
        int randNum = rand.nextInt(5);
        randNum = (randNum < 4) ? 8192 : 8192;

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
        if (gameState == 1) {
            int moved = mechs.handleLeft(tiles);
            updateBoard(moved);
        }
    }

    public void upPressed() {
        if (gameState == 1) {
            int moved = mechs.handleUp(tiles);
            updateBoard(moved);
        }
    }

    public void downPressed() {
        if (gameState == 1) {
            int moved = mechs.handleDown(tiles);
            updateBoard(moved);
        }
    }

    public void rightPressed() {
        if (gameState == 1) {
            int moved = mechs.handleRight(tiles);
            updateBoard(moved);
        }
    }

    public void updateBoard(int moved) {
        if (moved != 0) {
            this.repaint();
        }
        checkBestScore();
    }

    public void checkBestScore() {
        try {
            File scoreFile = new File("highscore.txt");
            Scanner reader = new Scanner(scoreFile);
            int highscore = Integer.valueOf(reader.nextLine());

            if (mechs.score > highscore) {
                FileWriter fileWriter = new FileWriter("highscore.txt");
                fileWriter.write(String.valueOf(mechs.score));
                fileWriter.close();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].setNumber(0);
            }
        }
        mechs.score = 0;
        gameState = 0;
        this.repaint();
    }

}
