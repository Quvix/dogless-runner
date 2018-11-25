package eu.quvix.doglessrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Callable;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Updatable {
    private static boolean DEBUG = false;
    private GameThread thread;
    private GameStateManager gsm;
    private DebugInfo debugInfo;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(final Context context) {
        setFocusable(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        BitmapManager.getInstance().loadBitmaps(this.getResources());

        gsm = GameStateManager.getInstance();
        gsm.putState(State.PLAYSTATE, new PlayState(this));
        gsm.switchState(State.PLAYSTATE);

        debugInfo = new DebugInfo(this);

        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

        debugInfo.registerParameter("FPS", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Double.toString(thread.getFPS());
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void update() {
        gsm.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            gsm.draw(canvas);
            debugInfo.draw(canvas);
        }
    }

    public double getFPS() {
        return thread.getFPS();
    }
}
