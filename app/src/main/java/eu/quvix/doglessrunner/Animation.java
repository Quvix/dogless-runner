package eu.quvix.doglessrunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

public class Animation implements Drawable, Updatable {

    private List<AnimationFrame> frames = new LinkedList<>();
    private int duration = 0;
    private int index = 0;
    private Rect rect;

    private class AnimationFrame {
        private Bitmap frame;
        private int duration;

        public AnimationFrame(Bitmap frame, int duration) {
            this.frame = frame;
            this.duration = duration;
        }

        public Bitmap getFrame() {
            return frame;
        }

        public int getDuration() {
            return duration;
        }
    }

    public Animation(Rect rect) {
        this.rect = rect;
    }

    public void putFrame(Bitmap bitmap, int duration) {
        frames.add(new AnimationFrame(bitmap, duration));
    }

    @Override
    public void draw(Canvas canvas) {
        if(!frames.isEmpty()) {
            canvas.drawBitmap(frames.get(index).getFrame(), null, rect, null);
        }
    }

    @Override
    public void update() {
        duration++;
        if(duration > frames.get(index).getDuration()) {
            if(index < frames.size() - 1) {
                index++;
            } else {
                index = 0;
            }
            duration = 1;
        }

    }
}
