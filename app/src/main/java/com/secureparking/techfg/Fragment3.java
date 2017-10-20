package com.secureparking.techfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secureparking.sr.SRDisplayPage;
import com.secureparking.sr.R;
import com.secureparking.ulti.NetUtils;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

@SuppressLint("Instantiatable")
public class Fragment3 extends Fragment implements IXListViewListener {

	private List<HashMap<String, String>> li;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private XListView listView;
	private MyListViewAdp3 adapter;
	private List<HashMap<String, String>> list;
	private Handler handler;
	private ProgressDialog dialog;
	private String username;
	private String password;
	private Intent i;

	public Fragment3(Intent i) {
		this.i = i;
		username = i.getStringExtra("username");
		password = i.getStringExtra("password");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("frag3 onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		System.out.println("frag3 onCreateView");
		View view = inflater.inflate(R.layout.tracking_report_frag3, container,
				false);
		listView = (XListView) view.findViewById(R.id.lv_servicereportlist);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

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
					adapter = new MyListViewAdp3(getActivity(), list);
					listView.setAdapter(adapter);
					dialog.dismiss();
					listView.setXListViewListener(Fragment3.this);
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// Toast.makeText(
							// getActivity(),
							// "You select a item position :" + position
							// + " Long:" + id, Toast.LENGTH_SHORT).show();
							Intent i = new Intent(getActivity(),
									SRDisplayPage.class);
							i.putExtra("username", username);
							i.putExtra("password", password);
							i.putExtra("carpark",
									list.get(position - 1).get("carpark"));
							i.putExtra("station",
									list.get(position - 1).get("station"));
							i.putExtra("bo", list.get(position - 1).get("bo"));
							i.putExtra("followby",
									list.get(position - 1).get("followby"));
							i.putExtra("item",
									list.get(position - 1).get("item"));
							i.putExtra("issue",
									list.get(position - 1).get("issue"));
							i.putExtra("solution",
									list.get(position - 1).get("solution"));
							i.putExtra("starttime",
									list.get(position - 1).get("starttime"));
							i.putExtra("endtime",
									list.get(position - 1).get("endtime"));
							i.putExtra("otstarttime", list.get(position - 1)
									.get("otstarttime"));
							i.putExtra("otendtime",
									list.get(position - 1).get("otendtime"));
							i.putExtra("submitby",
									list.get(position - 1).get("submitby"));
							i.putExtra("posttime",
									list.get(position - 1).get("posttime"));
							i.putExtra("ot", list.get(position - 1).get("ot"));
							startActivity(i);
						}
					});
					break;
				case FAILED:
					Toast.makeText(getActivity(), "Network error", 0).show();
					dialog.dismiss();
					break;
				default:
					dialog.dismiss();
					break;
				}
				// dialog.dismiss();
			}

		};
		// Intent i = new Intent();
		// username = i.getStringExtra("username");
		// password = i.getStringExtra("password");
		System.out.println("frag3 onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		// lv_issue.setRefreshTime("Yes");
	}

	@Override
	public void onRefresh() {
		// if(refreshflag){
		// refreshflag=false;

		init();
		onLoad();

		// refreshflag=true;

		//
		// }
	}

	@Override
	public void onLoadMore() {

	}

	private void init() {
		dialog = ProgressDialog.show(getActivity(), null, "Getting Data");
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetSrList(username, password);
					Message msg = Message.obtain();
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
					e.printStackTrace();
					dialog.dismiss();
				}

			}
		}.start();

	}

	public ArrayList<HashMap<String, String>> Analysis(String jsonStr)
			throws JSONException {
		JSONArray jsonArray = null;
		ArrayList<HashMap<String, String>> lis = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("carpark", jsonObject.getString("carpark"));
			map.put("station", jsonObject.getString("station"));
			map.put("bo", jsonObject.getString("bo"));
			map.put("followby", jsonObject.getString("followby"));
			map.put("item", jsonObject.getString("item"));
			map.put("issue", jsonObject.getString("issue"));
			map.put("solution", jsonObject.getString("solution"));
			map.put("starttime", jsonObject.getString("starttime"));
			map.put("endtime", jsonObject.getString("endtime"));
			map.put("otstarttime", jsonObject.getString("otstarttime"));
			map.put("otendtime", jsonObject.getString("otendtime"));
			map.put("submitby", jsonObject.getString("submitby"));
			map.put("posttime", jsonObject.getString("posttime"));
			map.put("ot", jsonObject.getString("ot"));
			map.put("qty", jsonObject.getString("qty"));
			lis.add(map);
		}
		return lis;
	}

}
