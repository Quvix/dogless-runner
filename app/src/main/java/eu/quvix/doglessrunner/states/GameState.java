package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;

import java.util.Map;

import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;

public interface GameState extends Drawable, Updatable {
    void init();
    void init(Map<String, Object> params);
    void draw(Canvas canvas);
    void update();
    Camera getCamera();

    void onTouch(int x, int y);
    void onMove(int x, int y, float velX, float velY);
    void onBackPressed();
}
