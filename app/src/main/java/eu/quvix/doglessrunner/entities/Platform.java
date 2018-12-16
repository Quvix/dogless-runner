package eu.quvix.doglessrunner.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.R;

public class Platform extends GameEntity {

    public enum Type {
        MEDIUM, LONG;
    }

    private Bitmap bitmap;

    public Platform(View view, float x, float y, Platform.Type type) {
        BitmapManager bm = BitmapManager.getInstance();
        switch (type) {
            case MEDIUM:
                bitmap = bm.getBitmap(R.drawable.platform_medium);
                break;
            case LONG:
                bitmap = bm.getBitmap(R.drawable.platform_long);
                break;
        }
        float height = (int)(view.getHeight() * 0.05);
        float width = height / bitmap.getHeight() * bitmap.getWidth();
        setBounds(new RectF(x, y, x + width, y + height));
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
