package eu.quvix.doglessrunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class Platform implements Drawable {
    public enum Type {
        MEDIUM, LONG;
    }

    private Bitmap bitmap;
    private Rect rect;

    public Platform(View view, int x, int y, Platform.Type type) {
        BitmapManager bm = BitmapManager.getInstance();
        switch (type) {
            case MEDIUM:
                bitmap = bm.getBitmap(R.drawable.platform_medium);
                break;
            case LONG:
                bitmap = bm.getBitmap(R.drawable.platform_long);
                break;
        }
        int height = (int)(view.getHeight() * 0.05);
        int width = height / bitmap.getHeight() * bitmap.getWidth();
        rect = new Rect(x, y, x + width, y + width);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    public Rect getBounds() {

    }
}
