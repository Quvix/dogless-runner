package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.maps.MapManager;
import eu.quvix.doglessrunner.ui.Background;
import eu.quvix.doglessrunner.ui.Menu;

public class SelectLevelState implements GameState {
    private static final String ALL_MAPS_URL = "http://highscore.quvix.eu/api/getmaps?hash_key=rF1eNtMvadnpPUXT64OiVlyqDzEfBoH1";
    private View view;
    private Menu menu;
    private Background background;

    public SelectLevelState(final View view) {
        this.view = view;

        background = new Background(view, BitmapManager.getInstance().getBitmap(R.drawable.sky_background));
        menu = new Menu(view, (int) (view.getHeight() * 0.3));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ALL_MAPS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("maps");
                    for(int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                        menu.addButton(jsonObject.getString("name"), new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                MapManager.loadMap(view, jsonObject.getInt("id"), jsonObject.getString("name"));
                                return null;
                            }
                        });
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(view.getContext()).add(jsonObjectRequest);
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
