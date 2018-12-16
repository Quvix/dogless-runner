package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;
import android.view.View;

import java.util.Map;
import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.ui.Background;
import eu.quvix.doglessrunner.ui.Menu;

public class MenuState implements GameState {

    private View view;
    private Menu menu;
    private Background background;

    public MenuState(View view) {
        this.view = view;

        background = new Background(view, BitmapManager.getInstance().getBitmap(R.drawable.sky_background));

        menu = new Menu(view, (int)(view.getHeight() * 0.3));
        menu.addButton("Start game", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                GameStateManager.getInstance().switchState(State.SELECTLEVEL_STATE);
                GameStateManager.getInstance().initState(null);
                return null;
            }
        });

        menu.addButton("Nastavení", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                GameStateManager.getInstance().switchState(State.SETTINGS_STATE);
                GameStateManager.getInstance().initState(null);
                return null;
            }
        });

        menu.addButton("Konec", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                return null;
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void init(Map<String, Object> params) {

    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        menu.draw(canvas);
    }

    @Override
    public void update() {
        background.update();
        menu.update();
    }

    @Override
    public Camera getCamera() {
        return null;
    }

    @Override
    public void onTouch(int x, int y) {
        menu.onTouch(x, y);
    }

    @Override
    public void onMove(int x, int y, float velX, float velY) {

    }

    @Override
    public void onBackPressed() {

    }
}
