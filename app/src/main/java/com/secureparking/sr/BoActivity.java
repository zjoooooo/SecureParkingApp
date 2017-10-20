package com.secureparking.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BoActivity extends Activity {

	private ListView lv_bo;
	private String[] objects = { "TM", "BD", "SD", "JE", "JW", "AM", "BM",
			"HG", "CL", "BB", "BI", "TP", "Other" };
	private String tag = "BoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosebo);

		// Log.e(tag, "Load BoActivity ok");
		lv_bo = (ListView) findViewById(R.id.lv_bo);

		lv_bo.setAdapter(new ArrayAdapter<String>(this, R.layout.boitem,
				R.id.tv_boinform, objects));

		lv_bo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String bo = objects[position];
				Intent data = new Intent();
				data.putExtra("bo", bo);
				setResult(RESULT_OK, data);
				finish();

			}
		});
	}

}
