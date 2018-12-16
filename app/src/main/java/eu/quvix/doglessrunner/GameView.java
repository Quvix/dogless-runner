package eu.quvix.doglessrunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.states.GameStateManager;
import eu.quvix.doglessrunner.states.HighscoresState;
import eu.quvix.doglessrunner.states.MenuState;
import eu.quvix.doglessrunner.states.PlayState;
import eu.quvix.doglessrunner.states.SelectLevelState;
import eu.quvix.doglessrunner.states.SettingsState;
import eu.quvix.doglessrunner.states.State;
import eu.quvix.doglessrunner.ui.DebugInfo;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Updatable {
    private static boolean DEBUG = false;
    private static int SCROLL_THRESHOLD = 5;
    private GameThread thread;
    private GameStateManager gsm;
    private DebugInfo debugInfo;
    private boolean isOnClick = false;
    private Point lastClick = new Point(0, 0);

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
        SoundManager.getInstance().loadSounds(getContext());

        gsm = GameStateManager.getInstance();
        gsm.putState(State.PLAY_STATE, new PlayState(this));
        gsm.putState(State.MENU_STATE, new MenuState(this));
        gsm.putState(State.SELECTLEVEL_STATE, new SelectLevelState(this));
        gsm.putState(State.SETTINGS_STATE, new SettingsState(this));
        gsm.putState(State.HIGHSCORES_STATE, new HighscoresState(this));
        gsm.switchState(State.MENU_STATE);

        debugInfo = new DebugInfo(this);

        SharedPreferences sharedPref = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SoundManager.getInstance().setSoundEffectsEnabled(sharedPref.getBoolean("soundEffect", true));
        SoundManager.getInstance().setSoundtrackEnabled(sharedPref.getBoolean("soundtrack", true));

        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

        DebugInfo.registerParameter("FPS", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Double.toString(thread.getFPS());
            }
        });

        SoundManager.getInstance().playSoundtrack(R.raw.nyan);
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastClick.x = (int)ev.getX();
                lastClick.y = (int)ev.getY();
                isOnClick = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isOnClick) {
                    Log.i("LISTENER", "onClick ");
                    gsm.getCurrentState().onTouch(lastClick.x, lastClick.y);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float velX = Math.abs(lastClick.x - ev.getX());
                float velY = Math.abs(lastClick.y - ev.getY());
                if (isOnClick && (velX > SCROLL_THRESHOLD || velY > SCROLL_THRESHOLD)) {
                    isOnClick = false;
                }
                Log.i("LISTENER", "movement detected X: " + Math.abs(lastClick.x - ev.getX()) + "; Y: " + Math.abs(lastClick.y - ev.getY()));
                gsm.getCurrentState().onMove(lastClick.x, lastClick.y, velX, velY);
                break;
            default:
                break;
        }
        return true;
    }
}
