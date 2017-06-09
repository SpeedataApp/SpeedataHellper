package com.speedata.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.speedata.kt40helper_.R;
import com.speedata.utils.MyLogger;

public class ListViewCheckBoxAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	int[] bitmaps;
	String[] titles;
	// private Boolean[] flag;
	private boolean[] state;
	HashMap<Integer, View> map = new HashMap<Integer, View>();

	public ListViewCheckBoxAdapter(Context mContext, String[] titles,
			boolean[] state) {
		super();
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = mContext;
		this.titles = titles;
		// flag = new Boolean[titles.length];
		this.state = state;
	}

	public ListViewCheckBoxAdapter() {
		super();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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

	MyLogger logger = MyLogger.jLog();

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (map.get(position) == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_checkbox_list,
					null);
			holder.check = (CheckBox) convertView.findViewById(R.id.cv_select);
			map.put(position, convertView);
			convertView.setTag(holder);

		} else {
			convertView = map.get(position);
			holder = (ViewHolder) convertView.getTag();
		}
		holder.check.setText(titles[position]);
		holder.check.setChecked(state[position]);
		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				state[position] = isChecked;
			}
		});
		return convertView;
	}

	public boolean[] getState() {
		for (int i = 0; i < state.length; i++) {
			logger.d("state:" + state[i]);
		}
		return state;
	}

	// public void setFlag(Boolean[] flag) {
	// this.flag = flag;
	// }

	static final class ViewHolder {
		// public TextView title;
		public CheckBox check;
	}

}
