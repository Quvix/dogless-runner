package eu.quvix.doglessrunner.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.SoundManager;
import eu.quvix.doglessrunner.Updatable;

public class MenuButton implements Drawable, Updatable {
    private Callable<Void> onClick;
    private RectF bounds;
    private Point textPosition;
    private Paint backgroundPaint;
    private Paint strokePaint;
    private String text;
    private Paint textPaint;

    public MenuButton(View view, int height, String text, Callable<Void> onClick) {
        this.onClick = onClick;
        this.bounds = new RectF((float)(view.getWidth() * 0.2), height, (float)(view.getWidth() * 0.8), (float)(height + view.getHeight() * 0.1));
        this.text = text;

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);

        strokePaint = new Paint();
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);;
        strokePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize((bounds.bottom - bounds.top) / 2);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPosition = new Point();
        textPosition.x = (int)((bounds.right + bounds.left) / 2);
        textPosition.y = (int)((bounds.top + bounds.bottom) / 2 - ((textPaint.descent() + textPaint.ascent()) / 2));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(bounds, 25, 25, backgroundPaint);
        canvas.drawRoundRect(bounds, 25, 25, strokePaint);
        canvas.drawText(text, textPosition.x, textPosition.y, textPaint);
    }

    @Override
    public void update() {

    }

    public void onTouch() {
        try {
            SoundManager.getInstance().playSoundEffect(R.raw.menu);
            onClick.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RectF getBounds() {
        return bounds;
    }
}
