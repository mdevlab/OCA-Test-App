package io.mdevlab.ocatraining.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by husaynhakeem on 5/25/17.
 */

public class UtilUI {

    public static void setViewDrawableBackgroundColor(View view, String color) {
        Drawable mDrawable = view.getBackground();
        mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY));
    }
}
