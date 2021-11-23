import java.awt.*

;

public class Tile {

    private int number;
    private Color color;
    private Color textColor;
    private int x;
    private int y;
    private int size;

    public Tile(int number, Color color, Color textColor, int x, int y, int size) {
        this.number = number;
        this.color = color;
        this.textColor = textColor;
        this.x = x;
        this.y = y;
        this.size = size;

    }

    public void paintTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(textColor);
        g.drawString(String.valueOf(number), x + 160, y + 70);
        g2d.setColor(color);
        g2d.fillRoundRect(x + 150, y + 60, size, size, 10, 10);

    }

}
