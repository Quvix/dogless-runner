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
        // Cat walking animation
        loadBitmap(resources, R.drawable.cat_walking_1);
        loadBitmap(resources, R.drawable.cat_walking_2);
        loadBitmap(resources, R.drawable.cat_walking_3);
        loadBitmap(resources, R.drawable.cat_walking_4);
        loadBitmap(resources, R.drawable.cat_walking_5);
        loadBitmap(resources, R.drawable.cat_walking_6);

        loadBitmap(resources, R.drawable.sky_background);

        loadBitmap(resources, R.drawable.platform_long);
        loadBitmap(resources, R.drawable.platform_medium);
    }

    private void loadBitmap(Resources resources, int resId) {
        bitmaps.put(resId, BitmapFactory.decodeResource(resources, resId));
    }

    public Bitmap getBitmap(int resId) {
        return bitmaps.get(resId);
    }
}
