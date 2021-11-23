import java.awt.*;
import java.util.*;

public class Board {

    private Color tileColor;
    private Color textColor;
    private int tileSize;

    public Board() {

    }

    public void createTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GRAY);
        g2d.fillRect(150, 60, tileSize + 10, tileSize + 10);

        ArrayList<Tile> tiles = new ArrayList<>();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                tiles.add(new Tile((x + 1) * (y + 1), tileColor, textColor, x * tileSize, y * tileSize, tileSize));

            }
        }

        tiles.forEach(tile -> tile.paintTiles(g));
    }

}
