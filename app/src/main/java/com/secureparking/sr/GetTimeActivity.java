package com.secureparking.sr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetTimeActivity extends Activity implements OnTimeChangedListener,
		OnClickListener {
	private DatePicker dp;
	private TimePicker tp;
	protected Calendar Cal;
	private int y, m, d, h, mins;
	private Button cT, btn_cancel;
	String gettime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_time);
		Calendar now = Calendar.getInstance();
		dp = (DatePicker) findViewById(R.id.datapicker);
		tp = (TimePicker) findViewById(R.id.timepicker);
		tp.setIs24HourView(true);
		tp.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(now.get(Calendar.MINUTE));
		tp.setOnTimeChangedListener(this);

		dp.init(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						y = year;
						m = monthOfYear + 1;
						d = dayOfMonth;

					}
				});
		cT = (Button) findViewById(R.id.GetTime);
		btn_cancel = (Button) findViewById(R.id.cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gettime = "";
				Intent i = new Intent();
				i.putExtra("result", gettime);
				setResult(RESULT_OK, i);
				finish();

			}
		});
		cT.setOnClickListener(this);
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		h = hourOfDay;
		mins = minute;

	}

	@Override
	@SuppressLint("SimpleDateFormat")
	public void onClick(View v) {
		// dp.clearFocus();
		// tp.clearFocus();
		if (y == 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
			gettime = df.format(new Date());
			if (mins == 0) {
				SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
				gettime = gettime + df2.format(new Date());

			} else {
				gettime = gettime + h + ":" + mins;
			}
			Intent i = new Intent();
			i.putExtra("result", gettime);
			setResult(RESULT_OK, i);
			finish();
		} else {
			gettime = y + "-" + m + "-" + d + " ";
			if (mins == 0) {
				SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
				gettime = gettime + df2.format(new Date());

			} else {
				gettime = gettime + h + ":" + mins;
			}
			// Toast.makeText(this, gettime, Toast.LENGTH_SHORT).show();
			// PageActivity.instance.etStartTime.setText(gettime);
			Intent i = new Intent();
			i.putExtra("result", gettime);
			setResult(RESULT_OK, i);
			finish();
		}
	}

}
