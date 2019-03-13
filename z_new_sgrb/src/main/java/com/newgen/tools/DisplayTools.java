package com.newgen.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayTools {
	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
	/***
	 * 将sp 值转换为dip值
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2dp(Context context, float spValue){
		final float pxValue = sp2px(context, spValue);
		return px2dip(context, pxValue);
	}

	/***
	 * 取屏宽
	 * 
	 * @param activty
	 * @return
	 */
	public static int getScreenWidth(Activity activty) {
		DisplayMetrics dm = new DisplayMetrics();
		activty.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 取屏高
	 * 
	 * @param activty
	 * @return
	 */
	public static int getScreenHeight(Activity activty) {
		DisplayMetrics dm = new DisplayMetrics();
		activty.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/****
	 * 取配置文件中的数字
	 * @param context
	 * @param res
	 * @return
	 */
	public static int getInt(Context context, int res) {
		try {
			int i = (int) context.getResources().getDimension(res);
			return i;
		} catch (Exception e) {
			return 0;
		}
	}
}
