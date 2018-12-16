package eu.quvix.doglessrunner.states;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.SoundManager;
import eu.quvix.doglessrunner.ui.Background;
import eu.quvix.doglessrunner.ui.Menu;

public class SettingsState implements GameState {

    private View view;
    private Menu menu;
    private Background background;

    public SettingsState(final View view) {
        this.view = view;

        background = new Background(view, BitmapManager.getInstance().getBitmap(R.drawable.sky_background));
        menu = new Menu(view, (int) (view.getHeight() * 0.3));

        menu.addButton("Hudba on/off", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                boolean on = SoundManager.getInstance().toggleSoundtracksVolume();
                SharedPreferences sharedPref = view.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("soundtrack", on);
                editor.commit();
                Toast.makeText(view.getContext(),"Hudba " + (on ? "zapnuta" : "vypnuta"),Toast.LENGTH_SHORT).show();
                return null;
            }
        });

        menu.addButton("Zvuky on/off", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                boolean on = SoundManager.getInstance().toggleSoundEffectsVolume();
                SharedPreferences sharedPref = view.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("soundEffect", on);
                editor.commit();
                Toast.makeText(view.getContext(),"Zvuky " + (on ? "zapnuty" : "vypnuty"),Toast.LENGTH_SHORT).show();
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
        GameStateManager.getInstance().switchState(State.MENU_STATE);
    }
}
