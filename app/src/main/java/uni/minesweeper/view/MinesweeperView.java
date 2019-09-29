package uni.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import uni.minesweeper.R;
import uni.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    private Paint linePaint;
    private Paint backgroundPaint;
    private MinesweeperModel model;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        model = MinesweeperModel.getSingletonInstance();

        linePaint = new Paint();
        linePaint.setColor(Color.DKGRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.colorBg));
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        int size = model.getBoardSize();

        // Draw grid
        for (int i = 0; i <= size; ++i) {
            float startX = ((float) i * getWidth()) / size;
            float startY = ((float) i * getHeight()) / size;
            canvas.drawLine(startX, 0, startX, getHeight(), linePaint);
            canvas.drawLine(0, startY, getWidth(), startY, linePaint);
        }
    }
}
