package com.secureparking.techfg;

import java.util.HashMap;
import java.util.List;
import com.secureparking.sr.R;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdp3 extends BaseAdapter {

	List<HashMap<String, String>> list;
	LayoutInflater inflater;

	FragmentActivity activity;

	public MyListViewAdp3(FragmentActivity fragmentActivity,
			List<HashMap<String, String>> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.activity = fragmentActivity;
		try {
			inflater = (LayoutInflater) fragmentActivity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		if (convertView == null) {
			view = inflater.inflate(R.layout.issuecontentservicereport, null);
		} else {
			view = convertView;
		}
		TextView tv_caprark = (TextView) view.findViewById(R.id.tv_sr_carpark);
		TextView tv_station = (TextView) view.findViewById(R.id.tv_sr_station);
		TextView tv_posttime = (TextView) view
				.findViewById(R.id.tv_sr_posttime);
		TextView tv_issue = (TextView) view.findViewById(R.id.tv_sr_content);
		TextView tv_submitter = (TextView) view
				.findViewById(R.id.tv_sr_submitter);
		TextView tv_solution = (TextView) view
				.findViewById(R.id.tv_sr_solution);

		HashMap<String, String> newInfo = list.get(position);
		tv_caprark.setText(newInfo.get("carpark"));
		tv_station.setText(newInfo.get("station"));
		tv_posttime.setText(newInfo.get("posttime"));
		tv_issue.setText(newInfo.get("issue"));
		tv_submitter.setText(newInfo.get("submitby"));
		tv_solution.setText(newInfo.get("solution"));
		return view;
	}

	class ViewHolder {
		TextView tv;
		ImageView im;
	}

}
