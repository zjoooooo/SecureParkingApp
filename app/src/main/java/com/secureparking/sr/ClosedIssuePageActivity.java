package com.secureparking.sr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ClosedIssuePageActivity extends Activity {

	private TextView tv_carpark;
	private TextView tv_Station;
	private TextView tv_ReportTime;
	private TextView tv_DoneTime;
	private TextView tv_DownTime;
	private TextView tv_lastupdater;
	private TextView tv_Solver;
	private TextView tv_Reporter;
	private TextView tv_Issue;
	private TextView tv_Solution;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closed_issue_page);
		initUi();
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void initUi() {
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_carpark = (TextView) findViewById(R.id.tv_CarPark);
		tv_Station = (TextView) findViewById(R.id.tv_Station);
		tv_ReportTime = (TextView) findViewById(R.id.tv_ReportTime);
		tv_DoneTime = (TextView) findViewById(R.id.tv_DoneTime);
		tv_DownTime = (TextView) findViewById(R.id.tv_DownTime);
		tv_lastupdater = (TextView) findViewById(R.id.tv_lastupdater);
		tv_Solver = (TextView) findViewById(R.id.tv_Solver);
		tv_Reporter = (TextView) findViewById(R.id.tv_Reporter);
		tv_Issue = (TextView) findViewById(R.id.tv_Issue);
		tv_Solution = (TextView) findViewById(R.id.tv_Solution);
		Intent i = getIntent();
		tv_carpark.setText("CarPark : " + i.getStringExtra("Carpark"));
		tv_Station.setText("Station : " + i.getStringExtra("Station"));
		tv_ReportTime
				.setText("Report Time : " + i.getStringExtra("ReportTime"));
		tv_DoneTime.setText("Solver Time : " + i.getStringExtra("DoneTime"));
		tv_DownTime.setText("Down Time : " + i.getStringExtra("DownTime")
				+ " hour");
		tv_lastupdater.setText("Last update by : "
				+ i.getStringExtra("LastModifyBy"));
		tv_Solver.setText("Solver : " + i.getStringExtra("DoneBy"));
		tv_Reporter.setText("Reporter : " + i.getStringExtra("Reportby"));
		tv_Issue.setText("Issue details : " + i.getStringExtra("Issue"));
		tv_Solution.setText("Solution : " + i.getStringExtra("Solution"));

	}

}
