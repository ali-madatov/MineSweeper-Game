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
    private MinesweeperModel model = null;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("isFlagMode", isFlagMode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        model = MinesweeperModel.getSingletonInstance();
        final ToggleButton btnToggleMode = findViewById(R.id.btnToggleMode);

        if (savedInstanceState != null) {
            isFlagMode = savedInstanceState.getBoolean("isFlagMode");
        }

        btnToggleMode.setTextOn(getApplicationContext().getString(R.string.mode, "FLAG"));
        btnToggleMode.setTextOff(getApplicationContext().getString(R.string.mode, "CHECK"));
        btnToggleMode.setChecked(isFlagMode);

        btnToggleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFlagMode = !isFlagMode;
                btnToggleMode.setChecked(isFlagMode);
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
