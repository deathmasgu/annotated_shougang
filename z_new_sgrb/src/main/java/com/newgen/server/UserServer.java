package com.newgen.server;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.newgen.domain.Member;
import com.newgen.tools.HttpTools;
import com.newgen.tools.MD5;
import com.newgen.tools.PublicValue;
import com.newgen.tools.SharedPreferencesTools;
import com.newgen.tools.Tools;

public class UserServer {
	public Member userLogin(String loginName, String password, Activity act) {
		password = MD5.md5(password);
		String url = PublicValue.BASEURL + "login.do";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memcode", loginName);
		params.put("password", password);
		String result = null;
		try {
			result = HttpTools.httpPost(params, url, true, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (result == null)
			return null;
		else {
			try {
				Tools.log(result);
				JSONObject json = new JSONObject(result);
				if (json.getInt("ret") == 1) {
					// 记住登录用户名
					SharedPreferencesTools.setValue(act,
							SharedPreferencesTools.LOGINNAME, loginName,
							SharedPreferencesTools.SYSTEMCONFIG);
					Gson gson = new Gson();
					Member u = gson.fromJson(json.getString("data"),
							Member.class);
					// if (u != null) {
					// u.setPassword(password);
					// u.setMemcode(loginName);
					// }
					/* 将信息保存到文件 */
					Tools.saveUserInfo(u, act);
					return u;
				} else {
					return null;
				}
			} catch (JSONException e) {
				return null;
			}
		}
	}

	/***
	 * 第三方登陆
	 * 
	 * @param memcode
	 * @param password
	 * @param nickname
	 * @param photopic
	 * @param platform
	 * @param act
	 * @return
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public Member login3(String memcode, String password, String nickname,
			String photopic, String platform, Activity act)
			throws JSONException, UnsupportedEncodingException {
		password = MD5.md5(password);
		StringBuffer url = new StringBuffer(PublicValue.BASEURL);
		url.append("loginFromOther.do");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memcode", memcode);
		params.put("password", password);
		params.put("nickname", nickname);
		params.put("photo", photopic);
		String jsonStr = null;
		try {
			jsonStr = HttpTools.httpPost(params, url.toString(), true, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonStr == null)
			return null;
		else {
			JSONObject json = new JSONObject(jsonStr);
			if (json.getInt("ret") == 1) {
				Gson gson = new Gson();
				Member m = gson
						.fromJson(json.getString("member"), Member.class);
				Log.e("debug", "=====> to save :" + m.getId());
				Tools.saveUserInfo(m, act);
				return m;
			}
			return null;
		}
	}

	public String findPwd(String username, String email) {
		String url = String.format(PublicValue.BASEURL
				+ "resetPassword.do?memcode=%s&email=%s", username, email);
		String jsonStr = Tools.executeHttpPost(url);
		return jsonStr;
	}
	
	public String findPwd(Map<String, Object> params) {
		// TODO Auto-generated method stub
		try {
			String url = PublicValue.BASEURL + "resetPassword.do";
			String result = HttpTools.httpPost(params, url, true, "utf-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	public Member updateBindPhone(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String url = PublicValue.BASEURL + "updateBindPhone.do";
		
		String jsonStr = null;
		try {
			jsonStr = HttpTools.httpPost(params, url, true, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonStr == null)
			return null;
		else {
			JSONObject json;
			try {
				json = new JSONObject(jsonStr);
				if (json.getInt("ret") == 0)
					return null;
				else {
					Gson gson = new Gson();
					return gson.fromJson(json.getString("user"), Member.class);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	
	public Member updateInfo(Map<String, Object> params) throws JSONException {
		String url = PublicValue.BASEURL + "updateMember.do";

		String jsonStr = null;
		try {
			jsonStr = HttpTools.httpPost(params, url, true, "utf-8", "myfiles");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String jsonStr = Tools.uploadImage(params, url);
		if (jsonStr == null)
			return null;
		else {
			JSONObject json = new JSONObject(jsonStr);
			if (json.getInt("ret") == 0)
				return null;
			else {
				Gson gson = new Gson();
				return gson.fromJson(json.getString("user"), Member.class);
			}
		}
	}

	/***
	 * 用户修改密码
	 * 
	 * @param params
	 * @return
	 */
	public String changePwd(Map<String, Object> params) {
		String url = PublicValue.BASEURL + "modifyPassword.do";
		String jsonStr = null;
		try {
			jsonStr = HttpTools.httpPost(params, url, true, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	/***
	 * 用户注册
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String rigest(Map<String, Object> params) {
		try {
			String url = PublicValue.BASEURL + "loginMember.do";
			String result = HttpTools.httpPost(params, url, true, "utf-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	public String getCode(String phone) {
		// TODO Auto-generated method stub
		try {
			String url = PublicValue.BASEURL + "sendVerifyCode.do?phone="+phone;
			String result = Tools.executeHttpPost(url);
			if (result == null)
				return "获取失败";
			else {
				Tools.log(result);
				JSONObject json = new JSONObject(result);
				return json.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "获取失败"; 
		}
	}

	

	

	
		
}
