import java.awt.*;

public class Tile {

    private Color color = Color.decode("#eee4da");
    private Color textColor = Color.decode("#776e65");
    private Color colorBlank = Color.decode("#cdc1b4");
    private int number;
    private int col;
    private int row;
    private int size;

    public Tile(int number, int row, int col, int size) {
        this.number = number;
        this.col = col;
        this.row = row;
        this.size = size;
    }

    public void paintTiles(Graphics g) {

        int x = col * (10 + size) + 10;
        int y = row * (10 + size) + 10;

        if (this.number < 1) {
            g.setColor(colorBlank);
            g.fillRoundRect(x, y, size, size, 5, 5);

        } else {
            String numberString = String.valueOf(number);

            Font font = new Font("MonoSpaced", Font.BOLD, 33);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            int textMarginX = fontMetrics.stringWidth(numberString);
            int textMarginY = -(int) fontMetrics.getLineMetrics(numberString, g).getBaselineOffsets()[2];

            g.setColor(color);
            g.fillRoundRect(x, y, size, size, 5, 5);
            g.setColor(textColor);
            g.drawString(numberString, x + (size - textMarginX) / 2,
                    y + size - (size - textMarginY) / 2 - textMarginY / 5);
        }

    }

    public void setNumber(int newNumber) {
        this.number = newNumber;
    }

    public int getNumber() {
        return this.number;
    }

}
