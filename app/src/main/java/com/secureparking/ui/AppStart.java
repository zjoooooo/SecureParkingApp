package com.secureparking.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.secureparking.sr.R;
import com.secureparking.ulti.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;
//import com.ideacode.news.R;
//import com.ideacode.news.app.AppContext;
//import com.ideacode.news.common.util.CommonSetting;
//import com.ideacode.news.common.util.StringUtils;
//import com.ideacode.news.common.util.UIHelper;
//import com.ideacode.news.logic.IdeaCodeActivity;
//import com.ideacode.news.logic.MainService;
//import com.ideacode.news.logic.Task;
//import com.ideacode.news.logic.TaskType;

public class AppStart extends IdeaCodeActivity {

	private TextView dataTips;
	boolean isFirstIn = false;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	protected static final int Enter = 1;
	protected static final int Show_update = 2;
	private TextView tv_version;
	private String versionFromServer;
	private String descrFromServer;
	private String apkurlFromServer;
	private boolean flag = false;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Enter:
				System.out.println("no new version go to home page");
				if (!isFirstIn) {
//					Intent homeIntent = new Intent(AppStart.this,
//							HomeMainFragActivity.class);
//					startActivity(homeIntent);
					finish();
				} else {
					Intent guideIntent = new Intent(AppStart.this,
							GuideActivity.class);
					startActivity(guideIntent);
					finish();
				}
				break;
			case Show_update:
				showUpdateDialong();
				break;

			default:
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.start, null);
		setContentView(view);
		tv_version = (TextView) findViewById(R.id.version);
		ImageView iv = (ImageView) findViewById(R.id.logo_bg);
		TextView tv = (TextView) findViewById(R.id.dataloading);
		tv_version.setText("version:" + getVersion());
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// tv_version.setText("version:"+getVersion());
		// }
		// }).start();

		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(5000);
		alphaAnimation.setStartOffset(0);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setFillBefore(false);
		animationSet.setFillAfter(true);
		iv.startAnimation(animationSet);
		tv.startAnimation(animationSet);
		tv_version.startAnimation(animationSet);
		MediaPlayer.create(getApplicationContext(), R.raw.logo_music_35)
				.start();
		// if (!MainService.isrun) {
		// Intent it = new Intent(this, MainService.class);
		// this.startService(it);
		// }

		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		dataTips = (TextView) findViewById(R.id.dataloading);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				CheckUpdate();
				dataTips.setText(R.string.dataloadend);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		}, 5000);

	}

	@Override
	protected void onResume() {
		if (flag) {
			showUpdateDialong();
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		flag = true;
		super.onStop();
	}

	/**
	 * Check new Version and update.
	 */
	private void showUpdateDialong() {

		System.out.println("open AlertDialog");
		AlertDialog.Builder builder = new Builder(AppStart.this);
		builder.setTitle("Check update");
		builder.setMessage(descrFromServer + " " + versionFromServer);
		builder.setCancelable(false);
		builder.setPositiveButton("Update now", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Uri uri = Uri
							.parse("market://details?id=com.secureparking.sr");
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(it);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		builder.setNegativeButton("Next time", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (!isFirstIn) {
//					Intent homeIntent = new Intent(AppStart.this,
//							HomeMainFragActivity.class);
//					startActivity(homeIntent);
					finish();
				} else {
					Intent guideIntent = new Intent(AppStart.this,
							GuideActivity.class);
					startActivity(guideIntent);
					finish();
				}
			}
		});
		builder.show();
	};

	private void CheckUpdate() {

		new Thread() {

			@Override
			public void run() {
				System.out.println("Start to Check version");
				// Uri uri = Uri.parse("market://details?id=Ӧ�ð���");
				// Intent it = new Intent(Intent.ACTION_VIEW,uri);
				// startActivity(it);
				// URL url = new URL(getString(R.string.serverurl));
				// HttpURLConnection con= (HttpURLConnection)
				// url.openConnection();
				String vstr = NetUtils
						.CheckUpdate(getString(R.string.CheckVersion_url));
				JSONObject jobj;
				Message msg = Message.obtain();
				try {
					if (!TextUtils.isEmpty(vstr)) {
						jobj = new JSONObject(vstr);
						versionFromServer = (String) jobj.get("version");
						System.out.println("new version:" + versionFromServer);
						descrFromServer = (String) jobj.get("description");
						apkurlFromServer = (String) jobj.get("apkurl");
						if (getVersion().equals(versionFromServer)) {
							msg.what = Enter;
						} else {
							msg.what = Show_update;
						}
					} else {
						if (!isFirstIn) {
//							Intent homeIntent = new Intent(AppStart.this,
//									HomeMainFragActivity.class);
//							startActivity(homeIntent);
							finish();
						} else {
							Intent guideIntent = new Intent(AppStart.this,
									GuideActivity.class);
							startActivity(guideIntent);
							finish();
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					handler.sendMessage(msg);
				}

			};

		}.start();

	}

	// // ����չʾ������
	// AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
	// aa.setDuration(2000);
	// view.startAnimation(aa);
	// aa.setAnimationListener(new AnimationListener() {
	// @Override
	// public void onAnimationEnd(Animation arg0) {
	// dataTips.setVisibility(View.VISIBLE);
	//
	// HashMap params = new HashMap();
	// params.put("context", AppStart.this);
	// Task ts = new Task(TaskType.TS_EXAM_GETINITIALIZEDATA, params);
	// //MainService.newTask(ts);
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// }
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// }
	//
	// });

	// ���ݵͰ汾cookie��1.5�汾���£�����1.5.0,1.5.1��
	// AppContext appContext = (AppContext) getApplication();
	// String cookie = appContext.getProperty("cookie");
	// if (StringUtils.isEmpty(cookie)) {
	// String cookie_name = appContext.getProperty("cookie_name");
	// String cookie_value = appContext.getProperty("cookie_value");
	// if (!StringUtils.isEmpty(cookie_name) &&
	// !StringUtils.isEmpty(cookie_value)) {
	// cookie = cookie_name + "=" + cookie_value;
	// appContext.setProperty("cookie", cookie);
	// appContext.removeProperty("cookie_domain", "cookie_name", "cookie_value",
	// "cookie_version", "cookie_path");
	// }
	// }
	public String getVersion() {

		PackageManager pm = getPackageManager();

		PackageInfo info;
		try {
			info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		int type = (Integer) param[0];
		// switch (type) {
		// case TaskType.TS_EXAM_GETINITIALIZEDATA:
		// if (CommonSetting.InitSystemDataException == (Integer) param[2]) {
		// Toast.makeText(this,
		// getResources().getString(R.string.initsystemdataexception),
		// Toast.LENGTH_LONG).show();
		// Intent it = new Intent(this, MainService.class);
		// this.stopService(it);
		// UIHelper.Exit(this);
		// } else {
		dataTips.setText(R.string.dataloadend);
		if (!isFirstIn) {
//			Intent homeIntent = new Intent(AppStart.this,
//					HomeMainFragActivity.class);
//			startActivity(homeIntent);
			finish();
		} else {
			Intent guideIntent = new Intent(AppStart.this, GuideActivity.class);
			startActivity(guideIntent);
			finish();
		}
		// }
		// break;
	}
	// }
}
