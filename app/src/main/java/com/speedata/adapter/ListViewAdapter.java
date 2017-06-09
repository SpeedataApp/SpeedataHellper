package com.speedata.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

public class ListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	int[] bitmaps;
	String[] titles;
	private boolean flog = false;

	// private ArrayList mData = new ArrayList<String>();

	public ListViewAdapter(Context mContext, int[] bitmaps, String[] titles,
			boolean flog) {
		super();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = mContext;
		this.bitmaps = bitmaps;
		this.titles = titles;
		this.flog = flog;
	}

	public ListViewAdapter(Context mContext, String[] titles, boolean flog) {
		super();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = mContext;
		this.titles = titles;
		this.flog = flog;
	}

	public ListViewAdapter() {
		super();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// System.out.println("title_length" + titles.length);
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// public void addItem(final String item) {
	// mData.add(item);
	// notifyDataSetChanged();
	// }

	// @Override
	// public int getCount() {
	// return mData.size();
	// }
	//
	// @Override
	// public String getItem(int position) {
	// return (String) mData.get(position);
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// System.out.println("getView " + position + " " + convertView);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_settings_list,
					null);
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_list_context);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// System.out.println("title_" + "  position_"
		// + position);
		holder.title.setText(titles[position]);
		if (flog) {
			// 读取对应资源文件 转换为bitMap
			holder.icon = (ImageView) convertView.findViewById(R.id.image_list);
			holder.go = (ImageView) convertView.findViewById(R.id.image_goto);
			holder.icon.setImageBitmap(BitmapFactory.decodeResource(
					mContext.getResources(), bitmaps[position]));
			holder.go.setVisibility(View.INVISIBLE);
		}
		// holder.title.setText(mData.get(position)+"");
		return convertView;
	}

	static final class ViewHolder {
		public TextView title;
		public ImageView icon;
		public ImageView go;
	}

}
