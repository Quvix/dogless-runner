package eu.quvix.doglessrunner.entities;

import android.graphics.RectF;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;

public abstract class GameEntity implements Drawable, Updatable {
    private RectF bounds;
    private float height;
    private float width;

    public GameEntity(RectF bounds) {
        setBounds(bounds);
    }

    public GameEntity() {
        this(new RectF());
    }

    public void moveVertically(float amount) {
        bounds.top += amount;
        bounds.bottom += amount;
    }

    public void moveHorizontally(float amount) {
        bounds.right += amount;
        bounds.left += amount;
    }

    public void setHeight(float height) {
        bounds.top = height;
        bounds.bottom = height + this.height;
    }

    public void setWidth(float width) {
        bounds.right = width;
        bounds.left = width + this.width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
        height = bounds.bottom - bounds.top;
        width = bounds.right - bounds.left;
    }

    protected abstract void onTouch();

    protected abstract void onMove(float velX, float velY);
}
