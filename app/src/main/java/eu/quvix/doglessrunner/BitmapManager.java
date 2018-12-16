package eu.quvix.doglessrunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.lang.reflect.Field;
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
        Field[] ID_Fields = R.drawable.class.getFields();
        for (Field ID_Field : ID_Fields) {
            try {
                loadBitmap(resources, ID_Field.getInt(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBitmap(Resources resources, int resId) {
        bitmaps.put(resId, BitmapFactory.decodeResource(resources, resId));
    }

    public Bitmap getBitmap(int resId) {
        return bitmaps.get(resId);
    }
}
