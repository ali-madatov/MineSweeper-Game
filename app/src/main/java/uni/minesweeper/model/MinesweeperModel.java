package uni.minesweeper.model;

import android.util.Log;
import java.util.ArrayList;

public class MinesweeperModel {
    private static MinesweeperModel singletonInstance = null;
    private int boardSize;
    private int totalMines;

    static enum ETileType {SAFE, CHECKED, BOMB, FLAG}

    private ArrayList<ArrayList<ETileType>> model = new ArrayList<>();


    private MinesweeperModel() {
    }

    public static MinesweeperModel getSingletonInstance() {
        if (singletonInstance == null)
            return new MinesweeperModel();

        return singletonInstance;
    }

    public void setSize(int size) {
        boardSize = size;
    }

    public void setTotalMines(int mines) {
        totalMines = mines;
    }

    public void resetModel() {
        if (boardSize == 0 || totalMines == 0) {
            Log.w("DBG_WARNING", "Tried to create model with " +
                ((boardSize == 0) ? "boardSize == 0" : "totalMines == 0")
            );

            return;
        }

        // Create board
        for (int i = 0; i < boardSize; ++i) {
            ArrayList<ETileType> row = new ArrayList<>();

            for (int j = 0; j < boardSize; ++j)
                row.add(ETileType.SAFE);

            model.add(row);
        }

        // Add mines
        int mines = totalMines;

        while (mines > 0) {
            int randomRow = (int) (model.size() * Math.random());
            int randomCol = (int) (model.size() * Math.random());

            if (model.get(randomRow).get(randomCol) != ETileType.BOMB) {
                model.get(randomRow).set(randomCol, ETileType.BOMB);
                --mines;
            }
        }
    }

    public void setTile(int row, int col, ETileType type) {
        model.get(row).set(col, type);
    }

    public ETileType getTile(int row, int col) {
        return model.get(row).get(col);
    }
}
