import java.awt.*

;

public class Tile {

    private String number;
    private Color color;
    private Color textColor;
    private int x;
    private int y;
    private int size;

    public Tile(int number, Color color, Color textColor, int x, int y, int size) {
        this.number = String.valueOf(number);
        this.color = color;
        this.textColor = textColor;
        this.x = x;
        this.y = y;
        this.size = size;

    }

    public void paintTiles(Graphics g) {

        Font font = new Font("Tahoma", Font.BOLD, 19);
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textMarginX = fontMetrics.stringWidth(number);
        int textMarginY = -(int) fontMetrics.getLineMetrics(number, g).getBaselineOffsets()[2];

        g.setColor(color);
        g.fillRoundRect(x, y, size, size, 15, 15);
        g.setColor(textColor);
        g.drawString(number, x + (size - textMarginX) / 2, y + size - (size - textMarginY) / 2 - 2);

    }

}
