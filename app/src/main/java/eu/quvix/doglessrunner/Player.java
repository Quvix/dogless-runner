package eu.quvix.doglessrunner;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class Player implements Drawable, Updatable {

    private Animation animation;

    public Player(View view, Point p) {
        BitmapManager bm = BitmapManager.getInstance();
        animation = new Animation(new Rect(p.x, p.y, p.x + 200, p.y + 200));
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_1), 3);
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_2), 3);
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_3), 3);
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_4), 3);
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_5), 3);
        animation.putFrame(bm.getBitmap(R.drawable.cat_walking_6), 3);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public void update() {
        animation.update();
    }
}
