package eu.quvix.doglessrunner.maps;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eu.quvix.doglessrunner.states.GameStateManager;
import eu.quvix.doglessrunner.states.State;

public class MapManager {
    public static SparseArray<GameMap> maps = new SparseArray<>();

    public static void loadMap(View view, int id, String name) {
        GameMap map = maps.get(id);
        if(map != null) {
            GameStateManager.getInstance().switchState(State.PLAY_STATE);
            Map<String, Object> params = new HashMap<>();
            params.put("map", map);
            GameStateManager.getInstance().initState(params);
        } else {
            loadMapFromWeb(view, id, name);
        }


    }

    private static void loadMapFromWeb(final View view, final int id, final String name) {
        new Thread(new Runnable(){
            public void run(){
                GameMap map = new GameMap(view, id, name);
                try {
                    URL url = new URL("http://highscore.quvix.eu/api/getmap/" + id);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000);

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        map.parseMapLine(line);
                    }
                    in.close();
                } catch (Exception e) {
                    Log.d("MAP_MANAGER",e.toString());
                }

                GameStateManager.getInstance().switchState(State.PLAY_STATE);
                Map<String, Object> params = new HashMap<>();
                params.put("map", map);
                GameStateManager.getInstance().initState(params);
            }
        }).start();
    }


}
