package com.newgen.sg_news.activity.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.newgen.UI.MyDialog;
import com.newgen.server.UserServer;
import com.newgen.sg_news.activity.R;
import com.newgen.sg_news.activity.R.id;
import com.newgen.sg_news.activity.R.layout;
import com.newgen.tools.MD5;
import com.newgen.tools.PublicValue;
import com.newgen.tools.SharedPreferencesTools;
import com.newgen.tools.Tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwdActivity extends Activity {
	
	ImageView backBtn;
	TextView submitBtn;
	EditText oldPwd, newPwd, rePwd;
	Dialog dialog;
	Handler handler;
	boolean isNight = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String isNightString = SharedPreferencesTools.getValue(ChangePwdActivity.this, SharedPreferencesTools.KEY_NIGHT,
				SharedPreferencesTools.KEY_NIGHT);
		isNight = isNightString.equals("true");
		
		
		if(isNight)
			setContentView(R.layout.activity_change_pwd_night);
		else 
			setContentView(R.layout.activity_change_pwd);
		
		PublicValue.USER = Tools.getUserInfo(this);
		
		initWight();
		initListener();
		
	}


	private void initWight() {
		// TODO Auto-generated method stub
		backBtn = (ImageView) findViewById(R.id.back);
		submitBtn = (TextView) findViewById(R.id.submit);
		oldPwd = (EditText) findViewById(R.id.oldPwd);
		newPwd = (EditText) findViewById(R.id.newPwd);
		rePwd = (EditText) findViewById(R.id.rePwd);
	}
	
	
	private void initListener() {
		// TODO Auto-generated method stub
		backBtn.setOnClickListener(new Click());
		submitBtn.setOnClickListener(new Click());
	}
	
	private class Click implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == backBtn)
				finish();
			else if (v == submitBtn) {
				if(validata())
					new ChangePasswordTask().execute(100);
			}
		}
	}
	
	/***
	 * 数据验证
	 * 
	 * @return
	 */
	private boolean validata() {
		String oldValue = oldPwd.getText().toString();
		final String newValue = newPwd.getText().toString();
		String reValue = rePwd.getText().toString();

		if (oldValue == null || oldValue.equals("")) {
			Toast.makeText(getApplicationContext(), "请填写旧密码",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (newValue == null || newValue.equals("")) {
			Toast.makeText(getApplicationContext(), "请填写新密码",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (newValue.length() < 6 || newValue.length() > 10) {
			Toast.makeText(getApplicationContext(), "密码由6-10位字符组成",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (!newValue.equals(reValue)) {
			Toast.makeText(getApplicationContext(), "两次密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		} 
		return true;
	}
	
	
	private class ChangePasswordTask extends
		AsyncTask<Object, Integer, Integer> {

		
		@Override
		protected void onPreExecute() {
			dialog = MyDialog.createLoadingDialog(ChangePwdActivity.this,
					"正在为您提交数据，请稍后……");
			dialog.show();
		}
		
		@Override
		protected Integer doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			int ret = 0;
			UserServer server = new UserServer();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memcode", PublicValue.USER.getMemcode());
			params.put("newpassword", MD5.md5(newPwd.getText().toString()));
			params.put("password", PublicValue.USER.getPassword());
			try {
				String json = server.changePwd(params);
				if (json == null)
					ret = 0;
				else {
					JSONObject j = new JSONObject(json);
					if (j.getInt("ret") == 1)
						ret = 1;
					else
						ret = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ret = 0;
			}
			return ret;
		}
		
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(null != dialog && dialog.isShowing())
			
				dialog.cancel();
			switch (result) {
			case 0:
				Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
				Tools.cleanUserInfo(ChangePwdActivity.this);
				Intent intent = new Intent(ChangePwdActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
		
		
	}
	
	
	
	@Override  
	public Resources getResources() {  
	    Resources res = super.getResources();    
	    Configuration config=new Configuration();    
	    config.setToDefaults();    
	    res.updateConfiguration(config,res.getDisplayMetrics() );  
	    return res;  
	}  
	
}
