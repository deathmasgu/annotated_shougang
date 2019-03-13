package com.newgen.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.newgen.UI.MyToast;
import com.newgen.UI.XListView;
import com.newgen.UI.XListView.IXListViewListener;
import com.newgen.adapter.PublicNewsAdapter;
import com.newgen.domain.NewsPub;
import com.newgen.server.NewsServer;
import com.newgen.sg_news.activity.R;
import com.newgen.sg_news.activity.detail.ImgNewsDetailActivity;
import com.newgen.sg_news.activity.detail.LinkDetailActivity;
import com.newgen.sg_news.activity.detail.NewsDetailActivity;
import com.newgen.tools.PublicValue;
import com.newgen.tools.SharedPreferencesTools;
import com.newgen.tools.Tools;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

public class CopyOfVideoFragment extends Fragment implements IXListViewListener{
	
	private View mView;
	
	XListView listView;
	int startid = -1;
	private final String cate = "cate_";
	private long flushTime = 0;
	LoadDateTask task;
	boolean isFrist = true;
	public int firstVisible=0,visibleCount=0, totalCount=0;
	List<NewsPub> tempList = null;
	List<NewsPub> newsList = new ArrayList<NewsPub>();
	
	boolean isNight = false;
	
	int cid = 7;
	
	PublicNewsAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		String isNightString = SharedPreferencesTools.getValue(getActivity(), SharedPreferencesTools.KEY_NIGHT,
				SharedPreferencesTools.KEY_NIGHT);
		isNight = isNightString.equals("true");
		
		if(isNight)
			mView = inflater.inflate(R.layout.fragment_video_night, container, false);
		else 
			mView = inflater.inflate(R.layout.fragment_video, container, false);
		
		adapter = new PublicNewsAdapter(getActivity(), newsList,isNight);
		listView = (XListView) mView.findViewById(R.id.listView);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(new ListItemClick());
		listView.setAdapter(adapter);
		
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {

	                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
	                	// 持续滚动开始，只触发一次                顺序: 2
	                    Log.e("HomePageVideoRelease", "SCROLL_STATE_FLING_2");
	                    break;
	
	                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://代表本次滑动完毕并停止滚动
	                	// 整个滚动事件结束，只触发一次            顺序: 4
	                	Log.e("HomePageVideoRelease", "SCROLL_STATE_IDLE_4");
	                	autoPlayVideo(view);
	                    break;
	                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
	                	// 手指触屏拉动准备滚动，只触发一次        顺序: 1
	                	Log.e("HomePageVideoRelease", "SCROLL_STATE_TOUCH_SCROLL_1");
	                    break;
	
	                default:
	                    break;
	            }
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				// 一直在滚动中，多次触发                          顺序: 3
				// firstVisibleItem   当前第一个可见的item
	            // visibleItemCount   当前可见的item个数
	            if (firstVisible == firstVisibleItem) {
	                return;
	            }
	            firstVisible = firstVisibleItem;
	            visibleCount = visibleItemCount;
	            totalCount = totalItemCount;
			}
		});	
		
		
		return mView;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (isFrist) {
			getCacheData();// 获取缓存数据
			flushTime = new Date().getTime();
			onRefresh();
			isFrist = false;
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JCVideoPlayer.releaseAllVideos();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		JCVideoPlayer.releaseAllVideos();
		startid = -1;
		long tempTime = new Date().getTime();
		String notice = Tools.getTimeInterval(flushTime, tempTime);
		listView.setRefreshTime(notice);
		flushTime = tempTime;
		task = new LoadDateTask();
		task.execute();
	}
	
	

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		JCVideoPlayer.releaseAllVideos();
		if (null != newsList && newsList.size() > 0)
			startid = newsList.get(newsList.size() - 1).getId();
		task = new LoadDateTask();
		task.execute();
	}
	
	
	/***
	 * 获取缓存数据
	 */
	public void getCacheData() {
		String jStr = SharedPreferencesTools.getValue(getActivity(),
				cate + cid, SharedPreferencesTools.CACHEDATA);
		if (null != jStr && !"".equals(jStr)) {
			try {
				Gson gson = new Gson();
				JSONArray json = new JSONArray(jStr);
				for (int i = 0; i < json.length(); i++) {
					NewsPub news = gson.fromJson(json.getString(i),
							NewsPub.class);
					if (null != news)
						newsList.add(news);
				}
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private class LoadDateTask extends AsyncTask<Object, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			JCVideoPlayer.releaseAllVideos();
			listView.setEnabled(false);
		}
		
		@Override
		protected Integer doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			int ret = -1;
			try {
				NewsServer server = new NewsServer();
				tempList = (server.getNewsList(cid, 10, startid));
				if (tempList == null) {
					ret = -1;
				} else if (tempList.size() <= 0) {
					ret = 0;
				} else {
					ret = 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			try {
				switch (result) {
				case 1:
					if (startid <= 0) {// 刷新动作
						newsList.clear();
						adapter.notifyDataSetChanged();
					}
					newsList.addAll(tempList);
					adapter.notifyDataSetChanged();
					tempList.clear();
					tempList = null;
					Gson gson = new Gson();
					if (startid <= 0) {// 当刷新数据是，做数据缓存
						String jStr = gson.toJson(newsList);
						SharedPreferencesTools.setValue(getActivity(), cate
								+ cid, jStr, SharedPreferencesTools.CACHEDATA);
					}
					startid = newsList.get(newsList.size() - 1).getId();
					break;
				case -1:
					break;
				case 0:
					MyToast.showToast(getActivity(), "没有更多数据", 3);
					break;
				}
				stopLoadOrRefresh();
				listView.setEnabled(true);
			} catch (Exception e) {

			}
		}
		
		@Override
		protected void onCancelled() {
			stopLoadOrRefresh();
			listView.setEnabled(true);
		}
		
	}
	
	
	/**
	 * 视频停止、音频停止
	 */
	public void stopLoadOrRefresh() {
		JCVideoPlayer.releaseAllVideos();
		listView.stopRefresh();
		listView.stopLoadMore();
	}
	

	class ListItemClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			
			NewsPub news = newsList.get(position - 1);
			Intent intent = null;
			if (news.getNewsPubExt().getInfotype() == 3) {//推广
				if (news.getNewsPubExt().getUrl() != null
						&& !news.getNewsPubExt().getUrl().equals("")) {
					intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(news.getNewsPubExt().getUrl());
					intent.setData(content_url);
				} else {
					intent = new Intent(getActivity(), NewsDetailActivity.class);
				}
			}else
				switch (news.getNewsPubExt().getType()) {
				case PublicValue.NEWS_STYLE_WORD:
					intent = new Intent(getActivity(), NewsDetailActivity.class);
					intent.putExtra("newsObject", news);
					break;
				case PublicValue.NEWS_STYLE_IMG:
					intent = new Intent(getActivity(),
							ImgNewsDetailActivity.class);
					break;
				case PublicValue.NEWS_STYLE_LINK: // 视频直播和超链url
					intent = new Intent(getActivity(), LinkDetailActivity.class);
					intent.putExtra("url", news.getNewsPubExt().getUrl());
					intent.putExtra("shareimg", news.getNewsPubExt()
							.getFaceimgpath()
							+ PublicValue.IMG_SIZE_M
							+ news.getNewsPubExt().getFaceimgname());
					break;
				default:
					intent = new Intent(getActivity(), NewsDetailActivity.class);
					intent.putExtra("newsObject", news);
					break;
				}
			if (null != intent) {
				intent.putExtra("newsId", news.getId());
				intent.putExtra("title", news.getShorttitle());
				JCVideoPlayer.releaseAllVideos();
				startActivity(intent);
			}
			
		}
		
	}
	
	
	
	/**
	 * 判断是否有视频item在 如果有在超出视图的时候暂停视频播放
	 */
	public void autoPlayVideo(AbsListView view){
		
		 for (int i = 0; i < visibleCount; i++) {
			 if (view!=null&&view.getChildAt(i)!=null&&view.getChildAt(i).findViewById(R.id.videocontroller1) != null){
				 
				 JCVideoPlayerStandard videoPlayerStandard1 = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.videocontroller1);
		         Rect rect = new Rect();
		         videoPlayerStandard1.getLocalVisibleRect(rect);
		         int videoheight3 = videoPlayerStandard1.getHeight();
		         Log.e("HomePageVideoRelease","i="+i+"==="+"videoheight3:"+videoheight3+"==="+"rect.top:"+rect.top+"==="+"rect.bottom:"+rect.bottom);
		         if (rect.top==0&&rect.bottom==videoheight3){
		         }else{
	        		 //部分不在视图中
					 Log.e("HomePageVideoRelease", "======================releaseAllVideos=====================");
					 JCVideoPlayer.releaseAllVideos();
	        	 }
			 } 
		 }
	}
	
	
}
