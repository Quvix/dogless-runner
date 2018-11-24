package eu.quvix.doglessrunner;

import android.graphics.Canvas;

public interface GameState extends Drawable, Updatable {
    void init();
    void draw(Canvas canvas);
    void update();
}
