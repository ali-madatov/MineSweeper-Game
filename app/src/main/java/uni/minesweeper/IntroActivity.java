package uni.minesweeper;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.Slider;

public class IntroActivity extends AppCompatActivity {

    private int BOARD_MAX_SIZE = 10;
    private int BOARD_MIN_SIZE = 5;
    private int MINES_MIN = 3;
    private int totalMines;
    private int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final TextView sizeTextView = findViewById(R.id.sizeTextView);

        boardSize = BOARD_MIN_SIZE;
        sizeTextView.setText(getApplicationContext().getString(R.string.board_size, String.valueOf(boardSize)));

        Slider sizeSlider = findViewById(R.id.boardSlider);
        sizeSlider.requestFocus();
        sizeSlider.setMin(BOARD_MIN_SIZE);
        sizeSlider.setMax(BOARD_MAX_SIZE);

        totalMines = MINES_MIN;
        final TextView minesTextView = findViewById(R.id.minesTextView);
        minesTextView.setText(String.valueOf(MINES_MIN));

        sizeSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                boardSize = value;
                sizeTextView.setText(getApplicationContext().getString(R.string.board_size, String.valueOf(boardSize)));

                if (totalMines > boardSize * boardSize) {
                    totalMines = boardSize * boardSize;
                    minesTextView.setText(String.valueOf(totalMines));
                }
            }
        });

        ButtonFloat btnIncrease = findViewById(R.id.btnIncrease);
        ButtonFloat btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(createFloatBtnListener(false));
        btnIncrease.setOnClickListener(createFloatBtnListener(true));
    }

    private View.OnClickListener createFloatBtnListener(final boolean isIncrease) {
        final TextView minesTextView = findViewById(R.id.minesTextView);

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalMines += isIncrease ? 1 : -1;
                totalMines = (totalMines < MINES_MIN) ? MINES_MIN : Math.min(totalMines, boardSize * boardSize);
                minesTextView.setText(String.valueOf(totalMines));
            }
        };
    }
}
