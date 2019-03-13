package com.newgen.adapter;

import java.util.List;


import com.newgen.sg_news.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> searchs;
	private boolean isNight;

	public SearchListAdapter(Context context,List<String> searchs,boolean isNight){
		this.mContext = context;
		this.searchs = searchs;
		this.isNight = isNight;
		mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(searchs!=null)
			return searchs.size();
		else 
			return 0;
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			if(isNight)
				convertView = mInflater.inflate(R.layout.search_list_item_night, null);
			else 
				convertView = mInflater.inflate(R.layout.search_list_item, null);
			
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		
		holder.title.setText(searchs.get(position));
		return convertView;
	}
	
	
	private static class ViewHolder {

		private TextView title;
		
	}

}
