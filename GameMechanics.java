import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameMechanics {

    static int gameState = 0; // 0 = game not over, -1 = game lost, 1 = game won
    int score = 0;
    static boolean tileChange = false;

    public GameMechanics() {
    }

    int handleLeft(Tile[][] tiles) {
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

    int handleUp(Tile[][] tiles) {
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

    int handleDown(Tile[][] tiles) {

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

    int handleRight(Tile[][] tiles) {

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

        List<Integer> range = IntStream.range(0, tileList.size() - 1).boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        if (addLast) {
            Collections.reverse(range);
        }

        for (int i : range) {
            if (i < tileList.size() - 1) {
                Tile currentTile = tileList.get(i);
                Tile nextTile = tileList.get(i + 1);
                int num = currentTile.getNumber();
                int nextNum = nextTile.getNumber();

                if (num == nextNum) {
                    tileChange = true;
                    score += num * 2;
                    currentTile.setNumber(num * 2);
                    tileList.remove(i + 1);
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
