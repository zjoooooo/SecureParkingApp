package com.secureparking.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.secureparking.ulti.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemActivity extends Activity {

	private ListView lv_bo;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private List<HashMap<String, String>> li;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				// lv_bo = (ListView) findViewById(R.id.lv_bo);

				lv_bo = (ListView) findViewById(R.id.lv_bo);
				lv_bo.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						String bo = li.get(position).get("item");
						Intent data = new Intent();
						data.putExtra("bo", bo);
						setResult(RESULT_OK, data);
						finish();
					}
				});
				SimpleAdapter mSchedule = new SimpleAdapter(ItemActivity.this,
						li,
						R.layout.boitem,
						new String[] { "item" },

						new int[] { R.id.tv_boinform });
				lv_bo.setAdapter(mSchedule);

				break;
			case FAILED:

				break;
			default:
				break;
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosebo);
		init();
		Button btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	private void init() {
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetInventoryList();
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
		ArrayList<HashMap<String, String>> lis = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("item", jsonObject.getString("item"));
			// listcontains.add(jsonObject.getString("item"));
			// map.put("Status", jsonObject.getString("Status"));
			lis.add(map);
		}
		return lis;
	}

}
