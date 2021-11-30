public class GameMechanics {

    static boolean[][] occupation = new boolean[4][4];

    public GameMechanics() {
        // for (int i = 0; i < tiles.length; i++) {
        // for (int j = 0; j < tiles.length; j++) {
        // tiles[i][j] = new Tile(0, i, j, tileSize);
        // }
        // }
    }

    void handleLeft(Tile[][] tiles) {
        int prevCol = -1;
        int prevRow = -1;

        for (int row = 0; row < tiles.length; row++) {
            int prevNum = -1;
            for (int col = 0; col < tiles.length; col++) {
                Tile currentTile = tiles[row][col];
                int currentNum = currentTile.getNumber();
                if (currentNum != 0) {
                    if (prevNum == currentNum) {
                        tiles[prevRow][prevCol].setNumber(prevNum * 2);
                        currentTile.setNumber(0);

                        prevCol = -1;
                        prevRow = -1;
                        prevNum = -1;
                    } else {
                        prevCol = col;
                        prevRow = row;
                        prevNum = currentNum;
                    }
                }
            }
        }

        for (int row = 0; row < tiles.length; row++) {
            int lastEmptyCol = -1;
            for (int col = 0; col < tiles.length; col++) {
                Tile currentTile = tiles[row][col];
                int currentNum = currentTile.getNumber();
                if (currentNum == 0) {
                    lastEmptyCol = col;
                } else {
                    if (lastEmptyCol != -1) {
                        tiles[row][lastEmptyCol].setNumber(currentNum);
                        currentTile.setNumber(0);
                        lastEmptyCol = col;
                    }
                }

            }

        }
    }

    void handleUp() {

    }

    void handleRight() {

    }

    void handleDown() {

    }

    void addUpTiles(Tile[][] tiles, int colStart, int rowStart) {

    }

    void pushTiles(Tile[][] tiles) {
        // for (int row = 0; row < tiles.length; row++) {
        // for (int col = 0; col < tiles.length; col++) {

        // }

        // }
    }
}
