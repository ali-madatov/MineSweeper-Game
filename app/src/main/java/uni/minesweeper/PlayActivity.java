package uni.minesweeper;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import uni.minesweeper.model.MinesweeperModel;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent startIntent = getIntent();

        int boardSize = startIntent.getIntExtra("boardSize", 0);
        int totalMines = startIntent.getIntExtra("totalMines", 0);

        MinesweeperModel model = MinesweeperModel.getSingletonInstance();
        model.setSize(boardSize);
        model.setTotalMines(totalMines);
        model.resetModel();
    }
}
