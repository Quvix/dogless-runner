package eu.quvix.doglessrunner.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.quvix.doglessrunner.Drawable;
import eu.quvix.doglessrunner.Updatable;

public class Menu implements Updatable, Drawable {

    private List<MenuButton> btns;
    private View view;
    private int height;

    public Menu(View view, int height) {
        btns = new ArrayList<>();
        this.view = view;
        this.height = height;
    }

    public void addButton(String text, Callable<Void> onClick) {
        btns.add(new MenuButton(view, (int)(height + view.getHeight() * 0.12 * btns.size()), text, onClick));
    }

    @Override
    public void draw(Canvas canvas) {
        for(MenuButton btn : btns) {
            btn.draw(canvas);
        }
    }

    @Override
    public void update() {
        for(MenuButton btn : btns) {
            btn.update();
        }
    }

    public void onTouch(int x, int y) {
        for(MenuButton btn : btns) {
            if(x >= btn.getBounds().left && x <= btn.getBounds().right && y >= btn.getBounds().top && y <= btn.getBounds().bottom) {
                btn.onTouch();
            }
        }
    }
}
