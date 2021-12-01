import java.awt.*;
import java.util.HashMap;

public class Tile {

    HashMap<Integer, Color> colorMap = new HashMap<>() {
        {
            put(2, Color.decode("#eee4da"));
            put(4, Color.decode("#ede0c8"));
            put(8, Color.decode("#f2b179"));
            put(16, Color.decode("#f59563"));
            put(32, Color.decode("#f67c5f"));
            put(64, Color.decode("#f65e3b"));
            put(128, Color.decode("#edcf72"));
            put(256, Color.decode("#edcc61"));
            put(512, Color.decode("#edc850"));
            put(1024, Color.decode("#edc53f"));
            put(2048, Color.decode("#edc22e"));
            put(4096, Color.decode("#fc746c"));
            put(8192, Color.decode("#fb5c58"));
            put(16384, Color.decode("#f05138"));
            put(32768, Color.decode("#6baed8"));
            put(65536, Color.decode("#477fba"));
            put(131072, Color.decode("#005d9e"));
        }
    };
    // private Color color = Color.decode("#eee4da");
    // private Color textColor = Color.decode("#776e65");
    private Color colorBlank = Color.decode("#cdc1b4");
    private int number;
    private int col;
    private int row;
    // private int size;

    public Tile(int number) {
        this.number = number;
    }

    public Tile(int number, int row, int col) {
        this.number = number;
        this.col = col;
        this.row = row;
    }

    public void paintTiles(Graphics g, int row, int col, int size) {

        int x = col * (10 + size) + 10;
        int y = row * (10 + size) + 10;

        if (this.number < 1) {
            g.setColor(colorBlank);
            g.fillRoundRect(x, y, size, size, 5, 5);

        } else {

            int fontSize = 38;
            int digits = (int) (Math.log10(number) + 1);

            if (number > 100) {
                fontSize -= 3 * digits;
            }

            String numberString = String.valueOf(number);

            Font font = new Font("MonoSpaced", Font.BOLD, fontSize);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            int textMarginX = fontMetrics.stringWidth(numberString);
            int textMarginY = -(int) fontMetrics.getLineMetrics(numberString, g).getBaselineOffsets()[2];

            Color color = (number <= 131072) ? colorMap.get(number) : Color.decode("#1e1b14");
            Color textColor = getTextColor();

            g.setColor(color);
            g.fillRoundRect(x, y, size, size, 5, 5);
            g.setColor(textColor);
            g.drawString(numberString, x + (size - textMarginX) / 2,
                    y + size - (size - textMarginY) / 2 - textMarginY / 5);
        }

    }

    private Color getTextColor() {
        if (number <= 4) {
            return Color.decode("#776e65");
        } else {
            return Color.decode("#f9f6f2");
        }
    }

    public boolean checkIfPosChange(int row, int col) {
        if (this.number != 0) {
            if (row != this.row || col != this.col) {
                return true;
            }
        }
        return false;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setNumber(int newNumber) {
        this.number = newNumber;
    }

    public int getNumber() {
        return this.number;
    }

}
