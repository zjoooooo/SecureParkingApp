package com.secureparking.techfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.secureparking.sr.ClosedIssuePageActivity;
import com.secureparking.sr.R;
import com.secureparking.ulti.NetUtils;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment2 extends Fragment implements IXListViewListener {

	private List<HashMap<String, String>> li;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private XListView listView;
	private MyListViewAdp2 adapter;
	private List<HashMap<String, String>> list;
	private Handler handler;
	private String username;
	private String password;
	private Intent i;

	public Fragment2(Intent i) {
		this.i = i;
		username = i.getStringExtra("username");
		password = i.getStringExtra("password");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("frag2 onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tracking_closedissue_frag2,
				container, false);
		listView = (XListView) view.findViewById(R.id.lv_closedissuelist);
		System.out.println("frag2 onCreateView");
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// Intent i = new Intent();
		// username = i.getStringExtra("username");
		// password = i.getStringExtra("password");
		System.out.println("frag2 onActivityCreated");
		init();
		handler = new Handler() {
			/**
			 * ������Ϣ
			 */
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SUCCESS:
					list = (List<HashMap<String, String>>) msg.obj;

					adapter = new MyListViewAdp2(getActivity(), list);
					listView.setAdapter(adapter);
					listView.setXListViewListener(Fragment2.this);
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// Toast.makeText(
							// getActivity(),
							// "You select a item position :" + position
							// + " Long:" + id, Toast.LENGTH_SHORT).show();
							Intent i = new Intent(getActivity(),
									ClosedIssuePageActivity.class);
							i.putExtra("username", username);
							i.putExtra("password", password);
							i.putExtra("ID", list.get(position - 1).get("ID"));
							i.putExtra("Carpark",
									list.get(position - 1).get("Carpark"));
							i.putExtra("Station",
									list.get(position - 1).get("Station"));
							i.putExtra("Issue",
									list.get(position - 1).get("Issue"));
							i.putExtra("Solution",
									list.get(position - 1).get("Solution"));
							i.putExtra("ReportTime", list.get(position - 1)
									.get("ReportTime"));
							i.putExtra("Reportby",
									list.get(position - 1).get("Reportby"));
							i.putExtra("DoneBy",
									list.get(position - 1).get("DoneBy"));
							i.putExtra("DownTime",
									list.get(position - 1).get("DownTime"));
							i.putExtra("LastModifyBy", list.get(position - 1)
									.get("LastModifyBy"));
							i.putExtra("DoneTime",
									list.get(position - 1).get("DoneTime"));
							startActivity(i);
						}
					});
					break;
				case FAILED:
					Toast.makeText(
							getActivity(),
							"you can't get data,please check your internet connection",
							0).show();
					break;
				default:
					break;
				}
				// dialog.dismiss();
			}

		};
		super.onActivityCreated(savedInstanceState);
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		// lv_issue.setRefreshTime("Yes");
	}

	@Override
	public void onRefresh() {
		init();
		onLoad();
	}

	@Override
	public void onLoadMore() {

	}

	private void init() {
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetClosedIssueList(username,
							password);
					Message msg = new Message();
					if (!TextUtils.isEmpty(jsonstr)) {
						if ("fail".equals(jsonstr)) {
							msg.what = FAILED;
						} else {
							li = Analysis(jsonstr);

							if (li != null) {
								msg.what = SUCCESS;
								msg.obj = li;
							} else {
								msg.what = FAILED;
							}

						}
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();

	}

	public ArrayList<HashMap<String, String>> Analysis(String jsonStr)
			throws JSONException {

		JSONArray jsonArray = null;
		//
		ArrayList<HashMap<String, String>> lis = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			//
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Carpark", jsonObject.getString("Carpark"));
			map.put("Station", jsonObject.getString("Station"));
			map.put("ReportTime", jsonObject.getString("ReportTime"));
			map.put("Issue", jsonObject.getString("Issue"));
			map.put("ID", jsonObject.getString("ID"));
			map.put("Reportby", jsonObject.getString("Reportby"));
			map.put("DoneBy", jsonObject.getString("DoneBy"));
			map.put("DownTime", jsonObject.getString("DownTime"));
			map.put("LastModifyBy", jsonObject.getString("LastModifyBy"));
			map.put("DoneTime", jsonObject.getString("DoneTime"));
			map.put("Solution", jsonObject.getString("Solution"));
			// map.put("Status", jsonObject.getString("Status"));
			lis.add(map);
		}
		return lis;
	}

}
