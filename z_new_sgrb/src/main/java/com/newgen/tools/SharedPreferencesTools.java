package com.newgen.tools;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/****
 * 应用程序中保存信息类
 * 
 * @author yilang
 * 
 */
public class SharedPreferencesTools {
	public final static String SYSTEMCONFIG = "configInfo";
	public final static String LOGINNAME = "loginName";
	/**
	 * 缓存数据
	 */
	public final static String CACHEDATA = "cacheData";//缓存数据
	
	public final static String KEY_DEVICE_ID = "UUID";
	public final static String KEY_FONT_SIZE = "FONT_SIZE";
	public final static String KEY_PUSH_CTRL = "IS_PUSH";
	public final static String KEY_NIGHT ="BT_NIGHT";
	public final static String KEY_WIFI ="WIFI";
	public static int NORMALSIZE = 1;//正文字体大小
	public static String SIZENAME = "中";
	
	/***
	 * 设置值
	 * 
	 * @param act
	 * @param values
	 * @param spName
	 * @return
	 */
	public static boolean setValue(Activity act, Map<String, String> values,
			String spName) {
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		Set<String> keys = values.keySet();// 取到所有键的集合
		for (String key : keys) {
			editor.putString(key, values.get(key));
		}
		return editor.commit();
	}

	/****
	 * 单个设值
	 * @param act
	 * @param key
	 * @param value
	 * @param spName
	 * @return
	 */
	public static boolean setValue(Activity act, String key, String value,
			String spName) {
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/***
	 * 取值
	 * 
	 * @param act
	 * @param spName
	 * @return
	 */
	public static Map<String, ?> getValue(Activity act, String spName) {
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return shared.getAll();
	}
	
	
	/****
	 * 单个设值
	 * @param act
	 * @param key
	 * @param value
	 * @param spName
	 * @return
	 */
	public static boolean setpdValue(Activity act, String key, int value,
			String spName) {
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/***
	 * 取值
	 * 
	 * @param act
	 * @param spName
	 * @return
	 */
	public static Map<String, ?> getpdValue(Activity act, String spName) {
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return shared.getAll();
	}
	
	/***
	 * 取值
	 * @param act
	 * @param key
	 * @param spName
	 * @return
	 */
	public static String getValue(Activity act , String key, String spName){
		SharedPreferences shared = act.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return shared.getString(key, "");
	}
}
