import java.util.ArrayList;
import java.util.List;

public class GameMechanics {

    public int score = 0;
    private static boolean tileChange = false;

    public GameMechanics() {
    }

    public int handleLeft(Tile[][] tiles) {
        tileChange = false;

        for (int row = 0; row < tiles.length; row++) {
            List<Tile> tileList = addNumToList(tiles, row, -1);
            tileList = addTiles(tileList, false);
            tileList = addEmptyTiles(tileList, false);
            setNewTiles(tiles, tileList, row, -1);
        }

        if (tileChange) {
            return 1;
        } else {
            return 0;
        }
    }

    public int handleUp(Tile[][] tiles) {
        tileChange = false;

        for (int col = 0; col < tiles.length; col++) {
            List<Tile> tileList = addNumToList(tiles, -1, col);
            tileList = addTiles(tileList, false);
            tileList = addEmptyTiles(tileList, false);
            setNewTiles(tiles, tileList, -1, col);
        }

        if (tileChange) {
            return 1;
        } else {
            return 0;
        }
    }

    public int handleDown(Tile[][] tiles) {

        tileChange = false;

        for (int col = 0; col < tiles.length; col++) {
            List<Tile> tileList = addNumToList(tiles, -1, col);
            tileList = addTiles(tileList, true);
            tileList = addEmptyTiles(tileList, true);
            setNewTiles(tiles, tileList, -1, col);
        }

        if (tileChange) {
            return 1;
        } else {
            return 0;
        }
    }

    public int handleRight(Tile[][] tiles) {

        tileChange = false;

        for (int row = 0; row < tiles.length; row++) {
            List<Tile> tileList = addNumToList(tiles, row, -1);
            tileList = addTiles(tileList, true);
            tileList = addEmptyTiles(tileList, true);
            setNewTiles(tiles, tileList, row, -1);
        }

        if (tileChange) {
            return 1;
        } else {
            return 0;
        }
    }

    private List<Tile> addNumToList(Tile[][] tiles, int row, int col) {
        List<Tile> tileList = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++) {
            Tile tile;
            if (row != -1) {
                tile = tiles[row][i];
            } else {
                tile = tiles[i][col];
            }
            if (tile.getNumber() != 0) {
                tileList.add(tile);
            }
        }

        return tileList;
    }

    private List<Tile> addTiles(List<Tile> tileList, boolean addLast) {
        int index = addLast ? tileList.size() - 1 : 0;
        ;
        while (true) {
            int nextIndex = addLast ? index - 1 : index + 1;
            if (nextIndex < 0 || nextIndex > tileList.size() - 1) {
                break;
            }
            Tile currentTile = tileList.get(index);
            Tile nextTile = tileList.get(nextIndex);
            int num = currentTile.getNumber();
            int nextNum = nextTile.getNumber();

            index = nextIndex;
            if (num == nextNum) {
                tileChange = true;
                score += num * 2;
                currentTile.setNumber(num * 2);
                tileList.remove(nextIndex);
                if (addLast) {
                    index--;
                } else {
                    index++;
                }
            }
        }

        return tileList;
    }

    private List<Tile> addEmptyTiles(List<Tile> tileList, boolean addFirst) {
        for (int i = tileList.size(); i < 4; i++) {
            if (addFirst) {
                tileList.add(0, new Tile(0));
            } else {
                tileList.add(i, new Tile(0));
            }
        }
        return tileList;
    }

    private void setNewTiles(Tile[][] tiles, List<Tile> tileList, int row, int col) {
        for (int i = 0; i < tiles.length; i++) {
            if (row != -1) {
                Tile newTile = tileList.get(i);
                if (newTile.checkIfPosChange(row, i)) {
                    tileChange = true;
                }
                newTile.setPos(row, i);
                tiles[row][i] = newTile;
            } else {
                Tile newTile = tileList.get(i);
                if (newTile.checkIfPosChange(i, col)) {
                    tileChange = true;
                }
                newTile.setPos(i, col);
                tiles[i][col] = newTile;
            }
        }
    }

    public int checkGameOver(Tile[][] tiles) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length - 1; col++) {
                int currentNum = tiles[row][col].getNumber();
                int nextNum = tiles[row][col + 1].getNumber();
                if (currentNum == 0 || nextNum == 0 || currentNum == nextNum) {
                    return 1;
                }
            }
        }

        for (int col = 0; col < tiles.length; col++) {
            for (int row = 0; row < tiles.length - 1; row++) {
                int currentNum = tiles[row][col].getNumber();
                int nextNum = tiles[row + 1][col].getNumber();
                if (currentNum == 0 || nextNum == 0 || currentNum == nextNum) {
                    return 1;
                }
            }
        }
        return -1;
    }

}
