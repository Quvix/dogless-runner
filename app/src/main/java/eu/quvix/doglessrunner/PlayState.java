package eu.quvix.doglessrunner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class PlayState implements GameState{
    private View view;
    private Paint backgroundPaint;
    private Player player;

    public PlayState(View view) {
        this.view = view;

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.WHITE);
    }

    @Override
    public void init() {
        player = new Player(view, new Point(200 , 200));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(backgroundPaint);
        player.draw(canvas);
    }

    @Override
    public void update() {
        player.update();
    }
}
