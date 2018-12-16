package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.SoundManager;
import eu.quvix.doglessrunner.database.DatabaseManager;
import eu.quvix.doglessrunner.database.Score;
import eu.quvix.doglessrunner.maps.GameMap;
import eu.quvix.doglessrunner.ui.Background;
import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.ui.DebugInfo;

public class PlayState implements GameState {
    private View view;
    private Background background;
    private GameMap map;
    private Camera camera;

    private boolean freeze = false;
    private int freezeTicks = 0;
    private Callable<Void> onUnfreeze;

    public PlayState(View view) {
        this.view = view;
    }

    @Override
    public void init() {
//        entityManager.setPlayer(new Player(view));
//        entityManager.addPlatform(new Platform(view, 10, 1000, Platform.Type.LONG));
        background = new Background(view, BitmapManager.getInstance().getBitmap(R.drawable.sky_background));
        camera = new Camera();
    }

    @Override
    public void init(Map<String, Object> params) {
        this.map = (GameMap)params.get("map");
        if(map == null) {
            Toast.makeText(view.getContext(), "ERROR LOADING MAP",
                    Toast.LENGTH_LONG).show();
            GameStateManager.getInstance().switchState(State.SELECTLEVEL_STATE);
        }

        DebugInfo.registerParameter("score", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Double.toString(map.getPlayer().getScore());
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        camera.applyToCanvas(canvas);
        map.draw(canvas);
        camera.applyBackToCanvas(canvas);
    }

    @Override
    public void update() {
        background.update();

        if(!freeze) {
            map.update();
            if(map.getPlayer().isDead()) {
                freeze = true;
                freezeTicks = 180;
                SoundManager.getInstance().playSoundEffect(R.raw.meow_death);
                onUnfreeze = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        DatabaseManager.getDatabase(view).scoreDao().insert(new Score(map.getId(), map.getPlayer().getScore()));
                        GameStateManager.getInstance().switchState(State.HIGHSCORES_STATE);
                        Map<String, Object> params = new HashMap<>();
                        params.put("map", map);
                        GameStateManager.getInstance().initState(params);
                        return null;
                    }
                };
            }
        } else {
            freezeTicks--;
            if(freezeTicks == 0) {
                freeze = false;
                try {
                    onUnfreeze.call();
                } catch (Exception e) {
                    Log.e("PLAYER", "Unfreeze failed");
                }
            }
        }
        camera.setPosition(map.getPlayer().getBounds().left - view.getWidth() * 0.2f, 0);

    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public void onTouch(int x, int y) {
        map.onTouch(x, y);
    }

    @Override
    public void onMove(int x, int y, float velX, float velY) {

    }

    @Override
    public void onBackPressed() {
        GameStateManager.getInstance().switchState(State.SELECTLEVEL_STATE);
    }
}
