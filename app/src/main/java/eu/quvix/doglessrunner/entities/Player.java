package eu.quvix.doglessrunner.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import eu.quvix.doglessrunner.Animation;
import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.Updatable;

public class Player implements Drawable, Updatable {

    private enum State {
        RUNNING, FALLING, JUMPING;
    }

    private static final float MAX_FALLING_SPEED = 15f;
    private static final float JUMP_POWER = 15f;
    private static final float GRAVITY = 0.4f;
    private float velY = 0;

    private Animation runningAnimation;
    private Bitmap jumpingBitmap;
    private Bitmap fallingBitmap;
    private RectF rect;
    private State state;
    private View view;
    private float width;
    private float height;

    public Player(View view) {
        this.view = view;
        BitmapManager bm = BitmapManager.getInstance();
        Bitmap sample = bm.getBitmap(R.drawable.cat_walking_1);
        width = (float)(view.getWidth() * 0.1);
        height = width / sample.getWidth() * sample.getHeight();
        rect = new RectF(200, view.getHeight() - height, (float)(200 + width), (float)(view.getHeight()));

        runningAnimation = new Animation(rect);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_1), 3);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_2), 3);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_3), 3);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_4), 3);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_5), 3);
        runningAnimation.putFrame(bm.getBitmap(R.drawable.cat_walking_6), 3);

        jumpingBitmap = bm.getBitmap(R.drawable.cat_walking_1);
        fallingBitmap = bm.getBitmap(R.drawable.cat_walking_3);

        state = State.JUMPING;
    }

    @Override
    public void draw(Canvas canvas) {
        switch(state) {
            case RUNNING:
                runningAnimation.draw(canvas);
                break;
            case FALLING:
                canvas.drawBitmap(fallingBitmap, null, rect, null);
                break;
            case JUMPING:
                canvas.drawBitmap(jumpingBitmap, null, rect, null);
                break;
        }
    }

    private void moveVerticaly(float amount) {
        rect.top += amount;
        rect.bottom += amount;
    }

    private void setHeight(float height) {
        rect.top = height;
        rect.bottom = height + this.height;
    }

    public void jump() {
        if(state == State.RUNNING) {
            velY -= JUMP_POWER;
            state = State.JUMPING;
        }
    }

    @Override
    public void update() {
        switch(state) {
            case RUNNING:
                runningAnimation.update();
                break;
            case FALLING:
                velY += GRAVITY;
                if(velY > MAX_FALLING_SPEED) {
                    velY = MAX_FALLING_SPEED;
                }
                if(rect.bottom > view.getHeight()) {
                    setHeight(view.getHeight() - height);
                    state = State.RUNNING;
                    runningAnimation.reset();
                    velY = 0;
                }
                break;
            case JUMPING:
                velY += GRAVITY;
                if(velY > 0) {
                    state = State.FALLING;
                }
                break;
        }
        moveVerticaly(velY);
    }
}
