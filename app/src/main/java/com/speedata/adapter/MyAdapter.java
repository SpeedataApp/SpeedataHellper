package com.speedata.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.kt40helper_.R;

public class MyAdapter extends BaseAdapter {
	private ArrayList mData = new ArrayList();
    private LayoutInflater mInflater;

    public MyAdapter() {
//        mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return (String) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView " + position + " " + convertView);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_settings_list, null);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.tv_list_context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(mData.get(position)+"");
        return convertView;
}

	public static class ViewHolder {
    public TextView textView;

	}
}
