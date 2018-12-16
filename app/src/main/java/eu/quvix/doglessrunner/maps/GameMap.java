package eu.quvix.doglessrunner.maps;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;
import eu.quvix.doglessrunner.entities.Enemy;
import eu.quvix.doglessrunner.entities.Food;
import eu.quvix.doglessrunner.entities.Platform;
import eu.quvix.doglessrunner.entities.Player;

public class GameMap implements Drawable, Updatable {

    private View view;
    private Player player = null;
    private List<Platform> platforms = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private int id;
    private String name;

    public GameMap(View view, int id, String name) {
        this.view = view;
        this.id = id;
        this.name = name;
    }

    @Override
    public void draw(Canvas canvas) {
        for(Platform p : platforms) {
            p.draw(canvas);
        }

        for(Food f : foods) {
            f.draw(canvas);
        }

        for(Enemy e : enemies) {
            e.draw(canvas);
        }

        if(player != null) {
            player.draw(canvas);
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

        Iterator<Food> foodIterator = foods.iterator();
        while(foodIterator.hasNext()) {
            Food f = foodIterator.next();
            RectF playerBounds= player.getBounds();
            if(f.getBounds().intersects(playerBounds.left, playerBounds.top, playerBounds.right, playerBounds.bottom)) {
                foods.remove(f);
                player.incCollected();
            }
        }

        for(Enemy e : enemies) {
            RectF playerBounds= player.getBounds();
            if(e.getBounds().intersects(playerBounds.left, playerBounds.top, playerBounds.right, playerBounds.bottom)) {
                player.die();
            }
        }
    }

    public void parseMapLine(String line) {
        String[] tokens = line.split(";");
        switch (tokens[0]) {
            case "0":
                setPlayer(new Player(view, Float.parseFloat(tokens[1]) * view.getWidth(), Float.parseFloat(tokens[2]) * view.getHeight()));
                break;
            case "1":
                addPlatform(new Platform(view, Float.parseFloat(tokens[1]) * view.getWidth(), Float.parseFloat(tokens[2]) * view.getHeight(), Platform.Type.values()[Integer.parseInt(tokens[3])]));
                break;
            case "2":
                addFood(new Food(view, Float.parseFloat(tokens[1]) * view.getWidth(), Float.parseFloat(tokens[2]) * view.getHeight()));
                break;
            case "3":
                addEnemy(new Enemy(view, Float.parseFloat(tokens[1]) * view.getWidth(), Float.parseFloat(tokens[2]) * view.getHeight()));
                break;
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

    public void addFood(Food food) {
        foods.add(food);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void onTouch(int x, int y) {
        player.onTouch();
    }

    public void onMove(int x, int y, float velX, float velY) {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
