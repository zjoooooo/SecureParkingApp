package com.secureparking.sr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.secureparking.dao.SrInfrom;
import com.secureparking.ulti.NetUtils;

import java.sql.Connection;
import java.util.Calendar;

//import net.sourceforge.jtds.jdbc.Driver;;

public class SRPageActivity extends Activity {

	private Connection con;
	// private Connection connect;
	private EditText etStartTime;
	private EditText etEndTime;
	private int month, year, date;
	private Calendar cal;
	private int dom;
	private EditText otStartTime;
	private EditText otEndTime;
	private TextView etcarpark;
	// static PageActivity instance= null;
	private Button btn_sm;
	private TextView etstation;
	private TextView etBO;
	private EditText etFollowBy;
	private EditText etUsedItem;
	private EditText etUsedItem2;
	private EditText etUsedItem3;
	private EditText etQTY;
	private EditText etQTY2;
	private EditText etQTY3;
	private EditText etIssue;
	private EditText etSolution;
	private String password;
	private String username;
	private String carpark;
	private String id;
	private String station;
	private String issue;
	private String bo;
	private ProgressDialog dialog;
	private final int Done = 0;
	private final int Double = 1;
	private final int Error = 2;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Done:
				dialog.dismiss();
				AlertDialog.Builder Alertdialog = new AlertDialog.Builder(
						SRPageActivity.this);
				Alertdialog.setTitle("Hi," + username.toUpperCase());
				Alertdialog
						.setMessage("Good job!!Your service report has already updated to server,have a nice day!!");
				Alertdialog.setCancelable(false);
				Alertdialog.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				Alertdialog.create().show();
				break;
			case Double:
				dialog.dismiss();
				AlertDialog.Builder Alertdialog2 = new AlertDialog.Builder(
						SRPageActivity.this);
				Alertdialog2.setTitle("Hi," + username.toUpperCase());
				Alertdialog2
						.setMessage("You already submit a same service report don't submit again!!");
				Alertdialog2.setCancelable(false);
				Alertdialog2.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
				Alertdialog2.create().show();
				break;
			case Error:
				dialog.dismiss();
				AlertDialog.Builder Alertdialog3 = new AlertDialog.Builder(
						SRPageActivity.this);
				Alertdialog3.setTitle("Hi," + username.toUpperCase());
				Alertdialog3.setMessage("You meet a unknow error!");
				Alertdialog3.setCancelable(false);
				Alertdialog3.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				Alertdialog3.create().show();

				break;
			default:
				dialog.dismiss();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_servicereport_activity_page);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
		carpark = intent.getStringExtra("Carpark");
		id = intent.getStringExtra("ID");
		station = intent.getStringExtra("Station");
		issue = intent.getStringExtra("Issue");
		bo = intent.getStringExtra("BO");
		init();

		etFollowBy.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							StaffActivity.class);
					startActivityForResult(i, 6);
					return true;
				}
				return false;
			}
		});

		etUsedItem.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							ItemActivity.class);
					startActivityForResult(i, 0);
					return true;
				}
				return false;
			}
		});

		etUsedItem2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							ItemActivity.class);
					startActivityForResult(i, 7);
					return true;
				}
				return false;
			}
		});
		etUsedItem3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							ItemActivity.class);
					startActivityForResult(i, 8);
					return true;
				}
				return false;
			}
		});

		etStartTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							GetTimeActivity.class);
					startActivityForResult(i, 1);
					return true;
				}
				return false;
			}
		});
		etEndTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							GetTimeActivity.class);
					startActivityForResult(i, 2);
					return true;
				}
				return false;
			}
		});
		otStartTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							GetTimeActivity.class);
					startActivityForResult(i, 3);
					return true;
				}
				return false;
			}
		});
		otEndTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent i = new Intent(SRPageActivity.this,
							GetTimeActivity.class);
					startActivityForResult(i, 4);
					return true;
				}
				return false;
			}
		});

		// etBO.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// Intent intent = new Intent(SRPageActivity.this,
		// BoActivity.class);
		// startActivityForResult(intent, 5);
		// return true;
		// }
		// return false;
		// }
		// });

		// etBO.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent(PageActivity.this, BoActivity.class);
		// startActivityForResult(intent,5);
		// }
		// });

	}

	private void init() {
		// private String name;
		// private String password;
		etStartTime = (EditText) findViewById(R.id.StartTime);
		etEndTime = (EditText) findViewById(R.id.EndTime);
		otStartTime = (EditText) findViewById(R.id.OTStartTime);
		otEndTime = (EditText) findViewById(R.id.OTendTime);
		etcarpark = (TextView) findViewById(R.id.CarPark);
		etcarpark.setText(carpark);
		etstation = (TextView) findViewById(R.id.Station);
		etstation.setText(station);
		etBO = (TextView) findViewById(R.id.BO);
		etBO.setText(bo);
		etFollowBy = (EditText) findViewById(R.id.FollowBy);
		etUsedItem = (EditText) findViewById(R.id.UsedItem);
		etUsedItem2 = (EditText) findViewById(R.id.UsedItem2);
		etUsedItem3 = (EditText) findViewById(R.id.UsedItem3);
		etQTY = (EditText) findViewById(R.id.QTY);
		etQTY2 = (EditText) findViewById(R.id.QTY2);
		etQTY3 = (EditText) findViewById(R.id.QTY3);
		etIssue = (EditText) findViewById(R.id.Issue);
		etIssue.setText(issue);
		etSolution = (EditText) findViewById(R.id.Solution);
		btn_sm = (Button) findViewById(R.id.Submit);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == 1) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("result");
					etStartTime.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}
			} else if (requestCode == 2) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("result");
					etEndTime.setText(result);

				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 3) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("result");
					otStartTime.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 4) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("result");
					otEndTime.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {
				}

			} else if (requestCode == 5) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("bo");
					etBO.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 0) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("bo");
					etUsedItem.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 6) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("bo");
					etFollowBy.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 7) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("bo");
					etUsedItem2.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			} else if (requestCode == 8) {
				if (resultCode == RESULT_OK) {
					String result = data.getStringExtra("bo");
					etUsedItem3.setText(result);
				}
				if (resultCode == RESULT_CANCELED) {

				}

			}

		}
	}

	// listen back button for quit action
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder dialog = new Builder(this);
			dialog.setTitle("Hi," + username.toUpperCase());
			dialog.setCancelable(false);
			dialog.setMessage("Are you sure to quit from writing service report?");
			dialog.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			dialog.setNegativeButton("No", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			dialog.create().show();
		}
		return false;
	}


	// DialogInterface.OnClickListener listener = new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// switch (which) {
	// case AlertDialog.BUTTON_POSITIVE:// "YES"��ť�˳�����
	// finish();
	// break;
	// case AlertDialog.BUTTON_NEGATIVE:// "NO"�ڶ�����ťȡ���Ի���
	// break;
	// default:
	// break;
	// }
	// }
	// };

	// DialogInterface.OnClickListener smlistener = new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// switch (which) {
	// case AlertDialog.BUTTON_POSITIVE:// "YES"��ť�˳�����
	// submit();
	// break;
	// case AlertDialog.BUTTON_NEGATIVE:// "NO"�ڶ�����ťȡ���Ի���
	// break;
	// default:
	// break;
	// }
	// }
	// };


	public void submitSR(View v) {

		if (!TextUtils.isEmpty(etcarpark.getText().toString())
				&& !TextUtils.isEmpty(etstation.getText().toString())
				&& !TextUtils.isEmpty(etBO.getText().toString())
				&& !TextUtils.isEmpty(etIssue.getText().toString())
				&& !TextUtils.isEmpty(etSolution.getText().toString())
				&& !TextUtils.isEmpty(etStartTime.getText().toString())
				&& !TextUtils.isEmpty(etEndTime.getText().toString())
				&& !TextUtils.isEmpty(etUsedItem.getText().toString())) {
			if ((!TextUtils.isEmpty(otStartTime.getText().toString()) && TextUtils
					.isEmpty(otEndTime.getText().toString()))
					|| (TextUtils.isEmpty(otStartTime.getText().toString()) && !TextUtils
							.isEmpty(otEndTime.getText().toString()))) {

				Toast.makeText(SRPageActivity.this,
						"Ot start and Ot end must be both exisit or empty.",
						Toast.LENGTH_LONG).show();

				// Crouton.makeText(SRPageActivity.this,
				// "Otstart and Otend must be both exisit or empty ",Style.ALERT,R.id.alter_SR_view_group).show();
			} else {
				AlertDialog.Builder dialog = new Builder(this);
				dialog.setTitle("Mr." + username.toUpperCase()
						+ ".Please double check all details before submit!");
				dialog.setMessage("Are you sure to submit service report?");
				dialog.setCancelable(false);
				dialog.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						submit();

					}
				});
				dialog.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				dialog.create().show();
			}

		} else {
			// Toast.makeText(
			// SRPageActivity.this,
			// "CarPark,Station,BO,Issue,Solution,StartTime,EndTime can't be empty",
			// Toast.LENGTH_LONG).show();
			// Crouton.makeText(SRPageActivity.this,
			// "Carpark,Station,BO,Issue,Solution,StartTime,EndTime is not allowed to be empty",Style.ALERT,R.id.alter_SR_view_group).show();
			AlertDialog.Builder Alertdialog = new AlertDialog.Builder(
					SRPageActivity.this);
			Alertdialog.setTitle("Hi," + username.toUpperCase());
			Alertdialog
					.setMessage("Car park,Station,BO,Issue,Solution,Start Time,End Time can't be empty data.");
			Alertdialog.setCancelable(false);
			Alertdialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			Alertdialog.create().show();

		}

	}

	/**
	 * �ύ����
	 */
	public void submit() {
		dialog = ProgressDialog.show(SRPageActivity.this, null,
				"Submitting service report!");
		final SrInfrom sr = new SrInfrom();
		sr.setName(username);
		sr.setPassword(password);
		sr.setCarpark(etcarpark.getText().toString().trim());
		sr.setStation(etstation.getText().toString().trim());
		sr.setBo(etBO.getText().toString().trim());
		sr.setFollowby(etFollowBy.getText().toString().trim());
		sr.setItem(etUsedItem.getText().toString().trim());
		sr.setItem2(etUsedItem2.getText().toString().trim());
		sr.setItem3(etUsedItem3.getText().toString().trim());
		sr.setQty(etQTY.getText().toString().trim());
		sr.setQty2(etQTY2.getText().toString().trim());
		sr.setQty3(etQTY3.getText().toString().trim());
		sr.setIssue(etIssue.getText().toString().trim());
		sr.setSolution(etSolution.getText().toString().trim());
		sr.setStarttime(etStartTime.getText().toString().trim());
		sr.setEndtime(etEndTime.getText().toString().trim());
		sr.setOtstarttime(otStartTime.getText().toString().trim());
		sr.setOtendtime(otEndTime.getText().toString().trim());
		sr.setId(id);
		new Thread(new Runnable() { // �½����߳�

					@Override
					public void run() {

						String state = NetUtils.PostSr(sr);
						Message msg =  new Message();
						if ("done".equals(state)) {
							msg.what = Done;
						} else if ("double".equals(state)) {
							msg.what = Double;
						} else {
							msg.what = Error;
						}
						handler.sendMessage(msg);
						// runOnUiThread(new Runnable() {
						//
						// @Override
						// public void run() {
						// if ("done".equals(state)) {
						// Toast.makeText(
						// SRPageActivity.this,
						// "Well done!Mr."
						// + username.toUpperCase()
						// +
						// ",your service report has been updated to server,have a nice day!!",
						// Toast.LENGTH_LONG).show();
						//
						// finish();
						//
						// } else if ("double".equals(state)) {
						// Toast.makeText(
						// SRPageActivity.this,
						// "You already submit a same service report don't submit again",
						// Toast.LENGTH_LONG).show();
						// finish();
						// } else {
						//
						// Toast.makeText(
						// SRPageActivity.this,
						// "Fail to submit please contact justin for further information",
						// Toast.LENGTH_LONG).show();
						// }
						// dialog.dismiss();
						// }
						// });
					}
				}).start();

	}

}
