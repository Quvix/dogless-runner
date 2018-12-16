package eu.quvix.doglessrunner.entities;

import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;

public class EntityManager implements Drawable, Updatable {
    private View view;
    private Player player = null;
    private List<Platform> platforms = new ArrayList<>();

    public EntityManager(View view) {
        this.view = view;
    }

    @Override
    public void draw(Canvas canvas) {
        if(player != null) {
            player.draw(canvas);
        }

        for(Platform p : platforms) {
            p.draw(canvas);
        }
    }

    @Override
    public void update() {
        if(player != null) {
            player.update();
        }

        for(Platform p : platforms) {
            p.update();
            player.checkCollision(p);
        }


    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public void onTouch(int x, int y) {
        player.onTouch();
    }

    public void onMove(int x, int y, float velX, float velY) {

    }
}
