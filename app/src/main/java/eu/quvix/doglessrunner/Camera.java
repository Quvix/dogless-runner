package eu.quvix.doglessrunner;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Camera {
    private PointF position;

    public Camera() {
        position = new PointF(0, 0);
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void applyToCanvas(Canvas canvas) {
        canvas.translate(-position.x, -position.y);
    }

    public void applyBackToCanvas(Canvas canvas) {
        canvas.translate(position.x, position.y);
    }
}
