import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Color tileColor = Color.LIGHT_GRAY;
    private Color textColor = Color.GRAY;
    private int margin = 100;
    private int tileSize = 60;

    public Board() {
        // this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        this.setBackground(Color.decode("#e9e2d2"));
        // this.setBounds(200, 200, 200, 200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        drawScore(g2d);
        createTiles(g2d);
    }

    public void drawBackground(Graphics g) {
        g.setColor(Color.decode("#e9e2d2"));
        g.fillRect(0, 0, 500, 500);
    }

    public void drawScore(Graphics g) {
        int posX = 300;
        int posY = 20;
        int rectW = 90;
        int rectH = 50;

        g.setColor(Color.GRAY);
        g.fillRoundRect(posX, posY, rectW, rectH, 15, 15);
        g.setFont(new Font("Tahoma", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString("Score", posX + rectW / 4, posY + 20);
        g.setFont(new Font("Tahoma", Font.BOLD, 16));
        g.drawString(String.valueOf(7777), posX + rectW / 4, posY + 40);
    }

    public void createTiles(Graphics g) {

        ArrayList<Tile> tiles = new ArrayList<>();

        g.translate(margin, margin);
        g.setColor(Color.GRAY);
        g.fillRoundRect(0, 0, 290, 290, 15, 15);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                int posX = x * (10 + tileSize) + 10;
                int posY = y * (10 + tileSize) + 10;

                tiles.add(new Tile((x + 1) * (y + 1), tileColor, textColor, posX, posY, tileSize));

            }
        }

        tiles.forEach(tile -> tile.paintTiles(g));
    }

    public void drawTile(Graphics g, Tile tile, int x, int y) {

    }

}
