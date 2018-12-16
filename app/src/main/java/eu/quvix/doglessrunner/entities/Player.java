package eu.quvix.doglessrunner.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.Animation;
import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.SoundManager;
import eu.quvix.doglessrunner.database.DatabaseManager;
import eu.quvix.doglessrunner.database.Score;
import eu.quvix.doglessrunner.states.GameStateManager;
import eu.quvix.doglessrunner.states.State;

public class Player extends GameEntity {

    private enum State {
        RUNNING, FALLING, JUMPING;
    }

    private static final float MAX_FALLING_SPEED = 15f;
    private static final float JUMP_POWER = 16f;
    private static final float GRAVITY = 0.4f;
    private static final float SPEED = 5f;
    private float velY = 0;
    private boolean dead = false;
    private int collected = 0;

    private Animation runningAnimation;
    private Bitmap jumpingBitmap;
    private Bitmap fallingBitmap;
    private State state;
    private View view;
    private boolean noCollisionsLastTick = true;

    public Player(View view, float x, float y) {
        this.view = view;
        BitmapManager bm = BitmapManager.getInstance();
        Bitmap sample = bm.getBitmap(R.drawable.cat_walking_1);
        float width = (float)(view.getWidth() * 0.1);
        float height = width / sample.getWidth() * sample.getHeight();
        setBounds(new RectF(x, y - height, (float)(x + width), y));

        runningAnimation = new Animation(getBounds());
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
                canvas.drawBitmap(fallingBitmap, null, getBounds(), null);
                break;
            case JUMPING:
                canvas.drawBitmap(jumpingBitmap, null, getBounds(), null);
                break;
        }
    }

    public void jump() {
        if(state == State.RUNNING) {
            velY -= JUMP_POWER;
            state = State.JUMPING;
        }
    }

    @Override
    public void update() {
        moveHorizontally(SPEED);

        if(noCollisionsLastTick && state == State.RUNNING) {
            state = State.FALLING;
        }
        switch(state) {
            case RUNNING:
                runningAnimation.update();
                break;
            case FALLING:
                velY += GRAVITY;
                if(velY > MAX_FALLING_SPEED) {
                    velY = MAX_FALLING_SPEED;
                }
                if(getBounds().top > view.getHeight()) {
                    die();
                }
                break;
            case JUMPING:
                velY += GRAVITY;
                if(velY > 0) {
                    state = State.FALLING;
                }
                break;
        }
        moveVertically(velY);
        noCollisionsLastTick = true;
    }

    @Override
    public void onTouch() {
        jump();
    }

    @Override
    public void onMove(float velX, float velY) {

    }

    public void checkCollision(GameEntity entity) {
        if(entity instanceof  Platform) {
            if(state == State.FALLING || state == State.RUNNING) {
                if((getBounds().left + getWidth() * 0.1 >= entity.getBounds().left && getBounds().left + getWidth() * 0.1 <= entity.getBounds().right) ||
                        (getBounds().right > entity.getBounds().left && getBounds().right < entity.getBounds().right)) {
                    if(state == State.FALLING) {
                        if(getBounds().bottom >= entity.getBounds().top && getBounds().bottom <= entity.getBounds().top + getHeight() / 4) {
                            putOnEntity(entity);
                        }
                    }
                    if(state == State.RUNNING) {
                        if(getBounds().bottom >= entity.getBounds().top && getBounds().top <= entity.getBounds().top + getHeight() / 4) {
                            noCollisionsLastTick = false;
                        }
                    }

                }
            }
        }
    }

    public void putOnEntity(GameEntity entity) {
        getBounds().bottom = entity.getBounds().top;
        getBounds().top = getBounds().bottom - getHeight();
        startRunning();
    }

    private void startRunning() {
        state = State.RUNNING;
        runningAnimation.reset();
        velY = 0;
    }

    public void die() {
        SoundManager.getInstance().playSoundEffect(R.raw.meow_death);
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public int getCollected() {
        return collected;
    }

    public void incCollected() {
        collected++;
    }

    public double getScore() {
        return getBounds().left + getCollected() * 1000;
    }
}
