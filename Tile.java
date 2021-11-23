import java.awt.*

;

public class Tile {

    private int number;
    private Color color;
    private int x;
    private int y;
    private int size;

    public Tile(int number, Color color, int x, int y, int size) {
        this.number = number;
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;

    }

    public void paintTiles(Graphics g) {
        g.drawString(String.valueOf(number), x, y);
        g.setColor(color);
        g.fillRect(x, y, size, size);

    }

}
