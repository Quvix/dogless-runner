package eu.quvix.doglessrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

public class BitmapManager {
    private static final BitmapManager SELF = new BitmapManager();

    private SparseArray<Bitmap> bitmaps;

    public static BitmapManager getInstance() {
        return SELF;
    }

    private BitmapManager() {
        bitmaps = new SparseArray<>();
    }

    public void loadBitmaps(Resources resources) {

    }

    public Bitmap getBitmap(int resId) {
        return bitmaps.get(resId);
    }
}
