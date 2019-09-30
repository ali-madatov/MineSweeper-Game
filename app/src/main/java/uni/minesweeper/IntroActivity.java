package uni.minesweeper;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.ramotion.fluidslider.FluidSlider;
import kotlin.Unit;
import uni.minesweeper.model.MinesweeperModel;

public class IntroActivity extends AppCompatActivity {

    private int BOARD_MAX_SIZE = 10;
    private int BOARD_MIN_SIZE = 5;
    private int MINES_MIN = 3;
    private int totalMines;
    private int boardSize;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("boardSize", boardSize);
        savedInstanceState.putInt("totalMines", totalMines);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final TextView sizeTextView = findViewById(R.id.sizeTextView);
        final TextView minesTextView = findViewById(R.id.minesTextView);

        if (savedInstanceState != null) {
            boardSize = savedInstanceState.getInt("boardSize");
            totalMines = savedInstanceState.getInt("totalMines");
        }

        boardSize = (boardSize == 0) ? BOARD_MIN_SIZE : boardSize;
        totalMines = (totalMines == 0) ? MINES_MIN : totalMines;
        sizeTextView.setText(getApplicationContext().getString(R.string.board_size, String.valueOf(boardSize)));
        minesTextView.setText(String.valueOf(totalMines));

        final FluidSlider sizeSlider = findViewById(R.id.sizeSlider);
        sizeSlider.setStartText(String.valueOf(BOARD_MIN_SIZE));
        sizeSlider.setEndText(String.valueOf(BOARD_MAX_SIZE));
        sizeSlider.setBubbleText(String.valueOf(boardSize));
        sizeSlider.setPosition(0);

        sizeSlider.setPositionListener(value -> {
            boardSize = (int) (value * (BOARD_MAX_SIZE - BOARD_MIN_SIZE)) + BOARD_MIN_SIZE;
            sizeTextView.setText(getApplicationContext().getString(R.string.board_size, String.valueOf(boardSize)));
            sizeSlider.setBubbleText(String.valueOf(boardSize));

            if (totalMines > boardSize * boardSize) {
                totalMines = boardSize * boardSize;
                minesTextView.setText(String.valueOf(totalMines));
            }

            return Unit.INSTANCE;
        });

        ButtonFloat btnIncrease = findViewById(R.id.btnIncrease);
        ButtonFloat btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(createFloatBtnListener(false));
        btnIncrease.setOnClickListener(createFloatBtnListener(true));

        final ButtonRectangle btnPlay = findViewById(R.id.btnPlay);
        final IntroActivity _this = this;
        final MinesweeperModel model = MinesweeperModel.getSingletonInstance();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSize(boardSize);
                model.setTotalMines(totalMines);
                model.resetModel();
                startActivity(new Intent(_this, PlayActivity.class));
            }
        });
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
