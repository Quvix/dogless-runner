package eu.quvix.doglessrunner.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.List;
import java.util.Map;

import eu.quvix.doglessrunner.BitmapManager;
import eu.quvix.doglessrunner.Camera;
import eu.quvix.doglessrunner.R;
import eu.quvix.doglessrunner.database.DatabaseManager;
import eu.quvix.doglessrunner.database.Score;
import eu.quvix.doglessrunner.maps.GameMap;
import eu.quvix.doglessrunner.ui.Background;
import eu.quvix.doglessrunner.ui.ScoreTable;

public class HighscoresState implements GameState {

    private View view;
    private List<Score> scores;
    private GameMap map;
    private Background background;
    private ScoreTable scoreTable;
    private Paint mapNamePaint;
    private Point mapNamPos;

    public HighscoresState(View view) {
        this.view = view;
        background = new Background(view, BitmapManager.getInstance().getBitmap(R.drawable.sky_background));

        mapNamePaint = new Paint();
        mapNamePaint.setStyle(Paint.Style.FILL);
        mapNamePaint.setColor(Color.BLACK);
        mapNamePaint.setTextSize(view.getHeight() * 0.1f);
        mapNamePaint.setTextAlign(Paint.Align.CENTER);

        mapNamPos = new Point((int)(view.getWidth() * 0.5), (int)(view.getHeight() * 0.15));
    }

    @Override
    public void init() {

    }

    @Override
    public void init(Map<String, Object> params) {
        map = (GameMap)params.get("map");
        scores = DatabaseManager.getDatabase(view).scoreDao().getAll(map.getId(), 0, 10);
        this.scoreTable = new ScoreTable(view, scores, map.getPlayer().getScore());
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        if(scoreTable != null) {
            scoreTable.draw(canvas);
        }
        canvas.drawText(map.getName(), mapNamPos.x, mapNamPos.y, mapNamePaint);
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public Camera getCamera() {
        return null;
    }

    @Override
    public void onTouch(int x, int y) {
        GameStateManager.getInstance().switchState(State.SELECTLEVEL_STATE);
    }

    @Override
    public void onMove(int x, int y, float velX, float velY) {

    }

    @Override
    public void onBackPressed() {

    }
}
