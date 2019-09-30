package uni.minesweeper;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import uni.minesweeper.model.MinesweeperModel;

public class PlayActivity extends AppCompatActivity {
    private boolean isFlagMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startIntent = getIntent();

        int boardSize = startIntent.getIntExtra("boardSize", 0);
        int totalMines = startIntent.getIntExtra("totalMines", 0);

        final MinesweeperModel model = MinesweeperModel.getSingletonInstance();
        model.setSize(boardSize);
        model.setTotalMines(totalMines);
        model.resetModel();

        setContentView(R.layout.activity_play);

        final ToggleButton btnToggleMode = findViewById(R.id.btnToggleMode);
        btnToggleMode.setText(getApplicationContext().getString(R.string.mode, isFlagMode ? "FLAG" : "CHECK"));

        btnToggleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlagMode = !isFlagMode;
                btnToggleMode.setText(getApplicationContext().getString(R.string.mode, isFlagMode ? "FLAG" : "CHECK"));
                model.setFlagMode(isFlagMode);
            }
        });

        final PlayActivity _this = this;
        Button btnNewGame = findViewById(R.id.btnNewGame);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(_this, IntroActivity.class));
            }
        });
    }
}
