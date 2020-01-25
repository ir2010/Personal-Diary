package com.ir_sj.personaldiary;

import android.content.Context;

import com.ir_sj.personaldiary.R;

public class BackgroundColor {
    private int backgroundColor;

    public BackgroundColor(Context context) {
        backgroundColor = context.getResources().getColor(R.color.normal);
    }

    public int getCurrentBackgroundColor() {
        return backgroundColor;
    }

    public void changeBackgroundColor(int newColor) {
        backgroundColor = newColor;
        /*  Some kind of notification for all of the affected views  */
    }
}
