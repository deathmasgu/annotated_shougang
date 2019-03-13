package com.newgen.share;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.newgen.domain.NewsPub;
import com.newgen.domain.NewsPubExt;
import com.newgen.domain.szb.Article;
import com.newgen.sg_news.activity.R;
import com.newgen.sg_news.activity.detail.ImgNewsDetailActivity;
import com.newgen.tools.PublicValue;

import android.content.Context;

public class ShareTools {
	
	public void showShare(boolean silent, String platform, Context context,
			NewsPub news) {
		
		OnekeyShare oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();
		
		String htmlUrl = PublicValue.BASEURL + "shareNews.do?newsid="+ news.getId();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(news.getTitle());
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(htmlUrl);
		
		String text = news.getShorttitle();
		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(htmlUrl);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(htmlUrl);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		
		try {
			String imgUrl;
			NewsPubExt ext = news.getNewsPubExt();
			
			if (ext.getFaceimgname() != null
					&& !"".equals(ext.getFaceimgname()))
				imgUrl = ext.getFaceimgpath() + PublicValue.IMG_SIZE_S
						+ ext.getFaceimgname();
			else
				imgUrl = PublicValue.BASEURL + "logo.png";
			oks.setImageUrl(imgUrl);
		} catch (Exception e) {
			oks.setImageUrl(PublicValue.BASEURL + "logo.png");
		}
		
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
	    // 启动分享GUI
	    oks.show(context);
	};
	
	
	public void showEpaperShare(boolean silent, String platform,
			Context context, Article article) {
		// TODO Auto-generated method stub
		OnekeyShare oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();
		
		String htmlUrl = PublicValue.BASEURL + "shareArticle.do?aid="
				+ article.getId()+"&pSName=sgrb";
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(article.getTitle());
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(htmlUrl);
		
		String text = article.getTitle();
		// text是分享文本，所有平台都需要这个字段
		oks.setText(text);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(htmlUrl);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(htmlUrl);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		
		try {
			String imgUrl;
			
			if (article.getImages()!=null&&!article.getImages().equals(""))
				imgUrl = article.getImages();
			else
				imgUrl = PublicValue.BASEURL + "logo.png";
			oks.setImageUrl(imgUrl);
		} catch (Exception e) {
			oks.setImageUrl(PublicValue.BASEURL + "logo.png");
		}
		
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
	    // 启动分享GUI
	    oks.show(context);
	}
	
	public void showSharelink(boolean silent, String platform,Context context, String title,
			String shareimg, String url) {
		// TODO Auto-generated method stub
		OnekeyShare oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();
		
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(url);
		
		// text是分享文本，所有平台都需要这个字段
		oks.setText(title);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		
		try {
			String imgUrl;
			
			if (shareimg!=null&&!shareimg.equals(""))
				imgUrl = shareimg;
			else
				imgUrl = PublicValue.BASEURL + "logo.png";
			oks.setImageUrl(imgUrl);
		} catch (Exception e) {
			oks.setImageUrl(PublicValue.BASEURL + "logo.png");
		}
		
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
	    // 启动分享GUI
	    oks.show(context);
	}
	
	
	
	
	
	
	class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

		@Override
		public void onShare(Platform platform, ShareParams paramsToShare) {
			
		}

	}
	

}
