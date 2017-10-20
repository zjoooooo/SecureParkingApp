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

public class HomeListViewAdp1 extends BaseAdapter {

	List<HashMap<String, String>> list;
	LayoutInflater inflater;
	FragmentActivity activity;
	private TextView tv_caprark;

	public HomeListViewAdp1(FragmentActivity fragmentActivity,
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
			view = inflater.inflate(R.layout.issuecontentopen, null);
		} else {
			view = convertView;
		}
		// tv_caprark = (TextView) view.findViewById(R.id.tv_openissue_carpark);
		// TextView tv_station = (TextView)
		// view.findViewById(R.id.tv_openissue_station);
		// TextView tv_reporttime = (TextView)
		// view.findViewById(R.id.tv_openissue_reporttime);
		// TextView tv_issue = (TextView)
		// view.findViewById(R.id.tv_openissue_content);
		// TextView tv_id = (TextView) view.findViewById(R.id.tv_openissue_ID);
		// HashMap<String, String> newInfo = list.get(position);
		// tv_caprark.setText(newInfo.get("Carpark"));
		// tv_station.setText(newInfo.get("Station"));
		// tv_reporttime.setText(newInfo.get("ReportTime"));
		// tv_issue.setText(newInfo.get("Issue"));
		// tv_id.setText(newInfo.get("ID"));
		// tv_id.setText(newInfo.get("Reportby"));
		return view;
	}

	class ViewHolder {
		TextView tv;
		ImageView im;
	}

}
