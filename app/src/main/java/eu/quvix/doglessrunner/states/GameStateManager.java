package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;

public class GameStateManager implements Drawable, Updatable {
    private static GameStateManager SELF;
    private State currentState;
    private Map<State, GameState> states = new HashMap<>();

    public static synchronized GameStateManager getInstance() {
        if(SELF == null) {
            SELF = new GameStateManager();
        }
        return SELF;
    }

    public void putState(State state, GameState gameState) {
        gameState.init();
        states.put(state, gameState);
    }

    public void switchState(State state) {
        currentState = state;
    }

    public void initState(Map<String, Object> params) {
        getCurrentState().init(params);
    }

    public void draw(Canvas canvas) {
        getCurrentState().draw(canvas);
    }

    public void update() {
        getCurrentState().update();
    }

    public GameState getCurrentState() {
        return states.get(currentState);
    }
}
