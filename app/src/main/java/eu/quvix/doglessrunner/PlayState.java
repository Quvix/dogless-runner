package eu.quvix.doglessrunner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class PlayState implements GameState{
    private View view;
    private Paint backgroundPaint;

    public PlayState(View view) {
        this.view = view;

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.WHITE);
    }

    @Override
    public void init() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(backgroundPaint);
    }

    @Override
    public void update() {

    }
}
