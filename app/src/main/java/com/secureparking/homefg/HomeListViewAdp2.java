package com.secureparking.homefg;

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

public class HomeListViewAdp2 extends BaseAdapter {

	List<HashMap<String, String>> list;
	LayoutInflater inflater;

	FragmentActivity activity;

	public HomeListViewAdp2(FragmentActivity fragmentActivity,
			List<HashMap<String, String>> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.activity = fragmentActivity;
		inflater = (LayoutInflater) fragmentActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			view = inflater.inflate(R.layout.issuecontentclosed, null);
		} else {
			view = convertView;
		}
		TextView tv_caprark = (TextView) view
				.findViewById(R.id.tv_closedissue_carpark);
		TextView tv_station = (TextView) view
				.findViewById(R.id.tv_closedissue_station);
		TextView tv_reporttime = (TextView) view
				.findViewById(R.id.tv_closedissue_reporttime);
		TextView tv_issue = (TextView) view
				.findViewById(R.id.tv_closedissue_content);
		TextView tv_id = (TextView) view.findViewById(R.id.tv_closedissue_ID);
		TextView tv_solution = (TextView) view
				.findViewById(R.id.tv_closedissue_solution);

		HashMap<String, String> newInfo = list.get(position);
		tv_caprark.setText(newInfo.get("Carpark"));
		tv_station.setText(newInfo.get("Station"));
		tv_reporttime.setText(newInfo.get("ReportTime"));
		tv_issue.setText(newInfo.get("Issue"));
		tv_id.setText(newInfo.get("ID"));
		tv_solution.setText(newInfo.get("Solution"));
		return view;
	}

	class ViewHolder {
		TextView tv;
		ImageView im;
	}

}
