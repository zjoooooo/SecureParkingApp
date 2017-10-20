package com.secureparking.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SRDisplayPage extends Activity {

	private Button btn_back;
	private TextView ev_issue;
	private TextView ev_solution;
	private TextView ev_OTendTime;
	private TextView ev_OTStartTime;
	private TextView ev_QTY;
	private TextView ev_UsedItem;
	private TextView ev_FollowBy;
	private TextView ev_endtime;
	private TextView ev_starttime;
	private TextView ev_bo;
	private TextView ev_station;
	private TextView ev_carpark;
	private TextView ev_submitby;
	private TextView ev_posttime;
	private TextView ev_ot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_servicereport_activity_page);

		initUi();
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initUi() {
		btn_back = (Button) findViewById(R.id.btn_srdisplay_close);
		ev_carpark = (TextView) findViewById(R.id.ev_srdisplay_CarPark);
		ev_station = (TextView) findViewById(R.id.ev_srdisplay_Station);
		ev_bo = (TextView) findViewById(R.id.ev_srdisplay_BO);
		ev_starttime = (TextView) findViewById(R.id.ev_srdisplay_StartTime);
		ev_endtime = (TextView) findViewById(R.id.ev_srdisplay_EndTime);
		ev_FollowBy = (TextView) findViewById(R.id.ev_srdisplay_FollowBy);
		ev_UsedItem = (TextView) findViewById(R.id.ev_srdisplay_UsedItem);
		ev_QTY = (TextView) findViewById(R.id.ev_srdisplay_QTY);
		ev_OTStartTime = (TextView) findViewById(R.id.ev_srdisplay_OTStartTime);
		ev_OTendTime = (TextView) findViewById(R.id.ev_srdisplay_OTendTime);
		ev_issue = (TextView) findViewById(R.id.ev_srdisplay_Issue);
		ev_solution = (TextView) findViewById(R.id.ev_srdisplay_Solution);
		ev_submitby = (TextView) findViewById(R.id.ev_srdisplay_submitter);
		ev_posttime = (TextView) findViewById(R.id.ev_srdisplay_posttime);
		ev_ot = (TextView) findViewById(R.id.ev_srdisplay_ot);

		Intent i = getIntent();
		ev_carpark.setText("Car Park : " + i.getStringExtra("carpark"));
		ev_station.setText("Station : " + i.getStringExtra("station"));
		ev_bo.setText("Bo : " + i.getStringExtra("bo"));
		ev_starttime.setText("Start time : " + i.getStringExtra("starttime"));
		ev_endtime.setText("End time : " + i.getStringExtra("endtime"));
		ev_UsedItem.setText("Item : " + i.getStringExtra("item"));
		ev_QTY.setText("Qty : " + i.getStringExtra("qty"));
		ev_OTStartTime.setText("Ot start time : "
				+ i.getStringExtra("otstarttime"));
		ev_OTendTime.setText("Ot end time : " + i.getStringExtra("otendtime"));
		ev_issue.setText("Issue : " + i.getStringExtra("issue"));
		ev_solution.setText("Solution : " + i.getStringExtra("solution"));
		ev_submitby.setText("Submitter : " + i.getStringExtra("submitby"));
		ev_posttime.setText("Send time : " + i.getStringExtra("posttime"));
		ev_ot.setText("Ot : " + i.getStringExtra("ot") + " mins");
		ev_FollowBy.setText("Followby : " + i.getStringExtra("followby"));

	}

}
