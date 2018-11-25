package eu.quvix.doglessrunner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class DebugInfo implements Drawable {
    private Paint textPaint;
    private Map<String, Callable<String>> parameters;
    private int index;
    private int lineHeight;
    private int x;
    private int y;

    public DebugInfo(View view) {
        parameters = new HashMap<>();

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(view.getHeight() / 40);

        lineHeight = view.getHeight() / 35;
        x = (int)(view.getWidth() * 0.01);
        y = (int)(view.getHeight() * 0.03);
    }

    public void registerParameter(String name, Callable<String> value) {
        parameters.put(name, value);
    }

    public void unregisterParameter(String name) {
        parameters.remove(name);
    }

    @Override
    public void draw(Canvas canvas) {
        index = 0;
        for(Map.Entry<String, Callable<String>> e : parameters.entrySet()) {
            try {
                canvas.drawText(e.getKey() + ": " + e.getValue().call(), x, y + lineHeight * index, textPaint);
            } catch (Exception ex) {
                Log.e("DebugInfo", "Unable to get value from parameter \"" + e.getKey() + "\"");
            }
            index++;
        }
    }
}
