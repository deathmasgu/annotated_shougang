package com.newgen.server;

import com.newgen.tools.HttpTools;
import com.newgen.tools.PublicValue;
import com.newgen.tools.Tools;

public class MainServer {
	public String getNewsCategoryAndADPath(){
//		String url="http://192.168.66.201:8989/JBNewsAppServer/getListCategory.do";
		String url = PublicValue.BASEURL + "getListCategory.do";// 得到请求数据url
		Tools.log(url);
		String resultStr = HttpTools.httpGet(url, 6);//设置6秒超时
		return resultStr;
	}
}
