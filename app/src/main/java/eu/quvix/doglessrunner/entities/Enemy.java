package eu.quvix.doglessrunner.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.R;

public class Enemy extends GameEntity {

    private Bitmap bitmap;

    public Enemy(View view, float x, float y) {
        RectF rect = new RectF();
        bitmap = BitmapManager.getInstance().getBitmap(R.drawable.enemy1);
        rect.bottom = y;
        rect.left = x;
        rect.right = x + view.getWidth() * 0.05f;
        rect.top = y - (rect.right - rect.left) / bitmap.getWidth() * bitmap.getHeight();
        setBounds(rect);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, getBounds(), null);
    }

    @Override
    public void update() {

    }

    @Override
    protected void onTouch() {

    }

    @Override
    protected void onMove(float velX, float velY) {

    }
}
