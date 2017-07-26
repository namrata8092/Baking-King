package com.nds.baking.king.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

/**
 * Created by Namrata Shah on 7/22/2017.
 */

public final class DrawableUtil {

    private DrawableUtil(){};

	/*  check for deprecated API before retriving drawable*/
    public static Drawable getDrawableImage(Context mContext,int drawableID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getResources().getDrawable(drawableID ,null);
        }else{
            return mContext.getResources().getDrawable(drawableID);
        }
    }
/*  check for deprecated API before setting drawable image*/
    public static void setDrawableToImageView(Drawable drawable, ImageView imageView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(drawable);
        }else{
            imageView.setBackgroundDrawable(drawable);
        }
    }
}
