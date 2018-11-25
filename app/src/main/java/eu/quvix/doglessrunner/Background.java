package eu.quvix.doglessrunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class Background implements Drawable, Updatable {

    private Bitmap bitmap;
    private int width;
    private float positionX = 0;
    private Rect rect;
    private int repeatCount;

    public Background(View view, Bitmap bitmap) {
        this.bitmap = bitmap;
        width = view.getHeight() / bitmap.getHeight() * bitmap.getWidth();
        rect = new Rect();
        rect.bottom = view.getHeight();
        rect.top = 0;
        repeatCount = view.getWidth() / width + 1;
    }

    private Rect getRect(int index) {
        rect.left = (int)positionX + (width * index);
        rect.right = (int)positionX + (width * index) + width;
        return rect;
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < repeatCount; i++) {
            canvas.drawBitmap(bitmap, null, getRect(i), null);
        }
    }

    @Override
    public void update() {
        positionX--;
        if(positionX < -width) {
            positionX = 0;
        }
    }
}
