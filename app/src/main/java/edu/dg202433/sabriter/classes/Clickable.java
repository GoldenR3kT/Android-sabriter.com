package edu.dg202433.sabriter.classes;

import android.content.Context;

public interface Clickable {
    void onClicItem(int itemIndex);
    void onRatingBarChange(int itemIndex, float value);
    Context getContext();

}
