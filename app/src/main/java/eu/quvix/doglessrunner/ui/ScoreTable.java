package eu.quvix.doglessrunner.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.util.List;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.database.Score;

public class ScoreTable implements Drawable {
    private View view;
    private List<Score> scores;
    private RectF rect;
    private Paint backgroundFill;
    private Paint backgroundStroke;
    private Paint scorePaint;
    private Paint scorePaintCurr;
    private Point scorePos;
    private double currScore;

    public ScoreTable(View view, List<Score> scores, double currScore) {
        this.view = view;
        this.scores = scores;
        this.currScore = currScore;

        rect = new RectF((int)(view.getWidth() * 0.1), (int)(view.getHeight() * 0.2), (int)(view.getWidth() * 0.9), (int)(view.getHeight() * 0.9));

        backgroundFill = new Paint();
        backgroundFill.setStyle(Paint.Style.FILL);
        backgroundFill.setColor(Color.WHITE);

        backgroundStroke = new Paint();
        backgroundStroke.setColor(Color.BLACK);
        backgroundStroke.setStyle(Paint.Style.STROKE);
        backgroundStroke.setStrokeWidth(5);

        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(view.getHeight() * 0.05f);
        scorePaint.setStyle(Paint.Style.FILL);

        scorePaintCurr = new Paint();
        scorePaintCurr.setColor(Color.RED);
        scorePaintCurr.setTextSize(view.getHeight() * 0.05f);
        scorePaintCurr.setStyle(Paint.Style.FILL);

        scorePos = new Point((int)(view.getWidth() * 0.15), (int)(view.getHeight() * 0.3));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rect, 25, 25, backgroundFill);
        canvas.drawRoundRect(rect, 25, 25, backgroundStroke);

        for(int i = 0; i < scores.size(); i++) {
            canvas.drawText((i + 1) + ".: " + Double.toString(scores.get(i).score), scorePos.x, scorePos.y + ((scorePaint.descent() - scorePaint.ascent()) * i), (scores.get(i).score == currScore ? scorePaintCurr : scorePaint));
        }
    }
}
