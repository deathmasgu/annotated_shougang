package com.newgen.fragment;

import java.util.HashMap;
import java.util.Map;

import cn.net.newgen.widget.dialog.ArtAlertDialog;

import com.igexin.sdk.PushManager;
import com.newgen.UI.CircleImageView;
import com.newgen.domain.Member;
import com.newgen.sg_news.activity.AboutUsActivity;
import com.newgen.sg_news.activity.ActiveActivity;
import com.newgen.sg_news.activity.ChangeThemeActivty;
import com.newgen.sg_news.activity.CollectListActivity;
import com.newgen.sg_news.activity.MainActivity;
import com.newgen.sg_news.activity.MessageActivity;
import com.newgen.sg_news.activity.R;
import com.newgen.sg_news.activity.user.LoginActivity;
import com.newgen.sg_news.activity.user.UpdateUserInfoActivity;
import com.newgen.tools.DataCleanManager;
import com.newgen.tools.PublicValue;
import com.newgen.tools.SharedPreferencesTools;
import com.newgen.tools.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalFragment extends Fragment{
	
	Dialog dialog;
	private View mView;
	private TextView version_text,cache_text,font_text;
	private LinearLayout clear_cache,set_font;
	private ImageView jpush_mode;
	private CircleImageView loginButton;
	private TextView user_name,my_message;
	private LinearLayout my_collect,my_night;
	private LinearLayout about_us,personal_active;
	private String cacheSize; // 缓存的
	DataCleanManager manager = new DataCleanManager();
	boolean pushOpen = true;// 推送功能是否打开
	int LoginCode = 1;  
	ImageLoader loader;
	DisplayImageOptions options;
	private boolean isNight;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		String isNightString = SharedPreferencesTools.getValue(getActivity(), SharedPreferencesTools.KEY_NIGHT,
				SharedPreferencesTools.KEY_NIGHT);
		isNight = isNightString.equals("true");
		
		
		if(isNight)
			mView = inflater.inflate(R.layout.fragment_personal_night, container, false);
		else
			mView = inflater.inflate(R.layout.fragment_personal, container, false);
		
		PublicValue.USER = Tools.getUserInfo(getActivity());
		
		initWight();
		initView();
		initListener();
		initImageLoadAndDisplayImageOptions();
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		PublicValue.USER = Tools.getUserInfo(getActivity());
		checkUserIsLogin();
	}
	
	
	private void initWight() {
		// TODO Auto-generated method stub
		set_font = (LinearLayout)mView.findViewById(R.id.set_font);
		clear_cache = (LinearLayout)mView.findViewById(R.id.clear_cache);
		font_text = (TextView)mView.findViewById(R.id.font_text);
		cache_text = (TextView)mView.findViewById(R.id.cache_text);
		version_text = (TextView)mView.findViewById(R.id.version_text);
		jpush_mode = (ImageView)mView.findViewById(R.id.jpush_mode);
		
		loginButton = (CircleImageView)mView.findViewById(R.id.loginButton);
		user_name = (TextView)mView.findViewById(R.id.user_name);
		my_collect = (LinearLayout)mView.findViewById(R.id.my_collect);
		my_night = (LinearLayout)mView.findViewById(R.id.my_night);
		my_message = (TextView)mView.findViewById(R.id.my_message);
		about_us = (LinearLayout)mView.findViewById(R.id.about_us);
		personal_active = (LinearLayout)mView.findViewById(R.id.personal_active);
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		version_text.setText(Tools.getVision(getActivity()));
		
		// 字号初始化
		Map<String, ?> map = SharedPreferencesTools.getValue(getActivity(),
				SharedPreferencesTools.KEY_FONT_SIZE);
		try {
			if (null != map)
				font_text.setText((String) map.get("name"));
			else 
				font_text.setText("中");
		} catch (Exception e) {
			font_text.setText("中");
		}
		
		
		// 推送初始化
		String isPush = SharedPreferencesTools.getValue(getActivity(),
				SharedPreferencesTools.KEY_PUSH_CTRL,
				SharedPreferencesTools.KEY_PUSH_CTRL);
		if (null != isPush && !"".equals(isPush)) {
			pushOpen = "true".equals(isPush);
		} else
			pushOpen = true;
		
		if (pushOpen)
			jpush_mode.setImageResource(R.drawable.jpush_mode_open);
		else
			jpush_mode.setImageResource(R.drawable.jpush_mode_close);

		// 缓存初始化
		try {
			cacheSize = manager.getCacheSize(getActivity().getExternalCacheDir());
			cache_text.setText(cacheSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initListener() {
		// TODO Auto-generated method stub
		set_font.setOnClickListener(new OnClick());
		clear_cache.setOnClickListener(new OnClick());
		jpush_mode.setOnClickListener(new OnClick());
		loginButton.setOnClickListener(new OnClick());
		my_night.setOnClickListener(new OnClick());
		my_collect.setOnClickListener(new OnClick());
		my_message.setOnClickListener(new OnClick());
		about_us.setOnClickListener(new OnClick());
		personal_active.setOnClickListener(new OnClick());
	}
	
	
	class OnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v==clear_cache){
				// 清理缓存
				dialog = new ArtAlertDialog(getActivity(), "确定清除缓存吗?", "清理缓存", "确定", "取消", new ArtAlertDialog.OnArtClickListener() {
					
					@Override
					public void okButtonClick() {
						// TODO Auto-generated method stub
						// 为用户体验效果，先清除部分提示清理完成，后台清除其他缓存
						manager.cleanFiles(getActivity());

						cache_text.setText("0.0 KB");
						Toast.makeText(getActivity(), "缓存清理成功",
								Toast.LENGTH_SHORT).show();

						manager.cleanInternalCache(getActivity());
						manager.cleanExternalCache(getActivity());
					}
					
					@Override
					public void cancelButtonClick() {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}else if(v==set_font){
				setFontSize();
			}else if(v==jpush_mode){
				if (pushOpen) {
					jpush_mode.setImageResource(R.drawable.jpush_mode_close);
					PushManager.getInstance().stopService(getActivity());
					pushOpen = false;
				} else {
					jpush_mode.setImageResource(R.drawable.jpush_mode_open);
					// com.getui.demo.DemoPushService 为第三方自定义推送服务
			        PushManager.getInstance().initialize(getActivity().getApplicationContext(), com.newgen.service.PushService.class);
			        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
			        PushManager.getInstance().registerPushIntentService(getActivity().getApplicationContext(), com.newgen.service.IntentService.class);
					pushOpen = true;
				}
				SharedPreferencesTools
						.setValue(getActivity(),
								SharedPreferencesTools.KEY_PUSH_CTRL,
								pushOpen ? "true" : "false",
								SharedPreferencesTools.KEY_PUSH_CTRL);
			}else if(v==loginButton){
				if(PublicValue.USER!=null){//进入编辑页面
					// 用户信息页面
					Intent intent = new Intent(getActivity(),
							UpdateUserInfoActivity.class);
					startActivity(intent);
				}else{//进入登录
					Intent intent = new Intent(getActivity(),
							LoginActivity.class);
					startActivityForResult(intent, LoginCode);
				}
			}else if(v==my_message){
				Intent intent = new Intent(getActivity(),
						MessageActivity.class);
				startActivityForResult(intent, LoginCode);
			}else if(v==my_night){
				if(isNight){
					SharedPreferencesTools
					.setValue(getActivity(),
							SharedPreferencesTools.KEY_NIGHT,"false",
							SharedPreferencesTools.KEY_NIGHT);
					changeAppBrightness(-1);
				}else{
					SharedPreferencesTools
					.setValue(getActivity(),
							SharedPreferencesTools.KEY_NIGHT,"true",
							SharedPreferencesTools.KEY_NIGHT);
					changeAppBrightness(30);
				}
				
				Intent intent = new Intent(getActivity(),ChangeThemeActivty.class);
				startActivity(intent);
				getActivity().finish();
				
			}else if(v==my_collect){
				Intent intent = new Intent(getActivity(),
						CollectListActivity.class);
				startActivity(intent);
			}else if(v==about_us){
				Intent intent = new Intent(getActivity(),
						AboutUsActivity.class);
				startActivity(intent);
			}else if(v==personal_active){
				Intent intent = new Intent(getActivity(),
						ActiveActivity.class);
				startActivity(intent);
			}
		}
		
	}
	
	/**
	 * 设置字体
	 */
	public void setFontSize() {
		final String[] items = getResources().getStringArray(R.array.font_size);
		new AlertDialog.Builder(getActivity()).setTitle("正文字体")
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 设置字体
						switch (which) {
						case 0:// 特大号
							font_text.setText("大");
							SharedPreferencesTools.NORMALSIZE = 0;
							SharedPreferencesTools.SIZENAME = "大";
							break;
						case 1:// 大号
							font_text.setText("中");
							SharedPreferencesTools.NORMALSIZE = 1;
							SharedPreferencesTools.SIZENAME = "中";
							break;
						case 2:// 中号
							font_text.setText("小");
							SharedPreferencesTools.NORMALSIZE = 2;
							SharedPreferencesTools.SIZENAME = "小";
							break;
						}
						/****
						 * 保存设置
						 */
						Map<String, String> map = new HashMap<String, String>();
						map.put("name", SharedPreferencesTools.SIZENAME);
						map.put("size", SharedPreferencesTools.NORMALSIZE + "");
						SharedPreferencesTools.setValue(getActivity(), map,
								SharedPreferencesTools.KEY_FONT_SIZE);
					}
				}).show();
	}
	
	
	// 回调方法，从第二个页面回来的时候会执行这个方法  
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {  
        // 根据上面发送过去的请求吗来区别  
        switch (requestCode) {  
        case 1:  
        	PublicValue.USER = Tools.getUserInfo(getActivity());
        	checkUserIsLogin();
            break;  
        default:  
            break;  
        }  
    }  

    
    // 登陆
 	public void checkUserIsLogin() {
 		Member m = Tools.getUserInfo(getActivity());
 		if (m != null) {
 			user_name.setText(m.getNickname());
 			Tools.log(m.getPhoto());
 			loader.displayImage(m.getPhoto(), loginButton, options);
 		} else {
 			user_name.setText("登录");
 			loginButton.setImageResource(R.drawable.user_login_btn);
 		}
 	}
 	
 	private void initImageLoadAndDisplayImageOptions() {
		loader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.user_login_btn)
				.showImageOnFail(R.drawable.user_login_btn)
				.showImageOnLoading(R.drawable.user_login_btn)
				.resetViewBeforeLoading(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}
 	
 	 /* 改变App当前Window亮度
 	   *
	   * @param brightness
	   */
	  public void changeAppBrightness(int brightness) {
	      Window window = getActivity().getWindow();
	      WindowManager.LayoutParams lp = window.getAttributes();
	      if (brightness == -1) {
	          lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
	      } else {
	          lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
	      }
	      window.setAttributes(lp);
	  }

}
