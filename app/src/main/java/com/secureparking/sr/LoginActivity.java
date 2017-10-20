package com.secureparking.sr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.secureparking.techfg.MainFragActivity;
import com.secureparking.ulti.NetUtils;
import com.secureparking.ulti.SharePreference;

import java.util.Map;

public class LoginActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private EditText etUser;
	private EditText etPassword;
	private CheckBox checkBox;
	private Button btn;
	private ProgressDialog dialog;
	private String username;
	private String password;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				SharePreference.saveAllInfo(LoginActivity.this, username,
						password);
			case FAILED:
				SharePreference.saveUserInfo(LoginActivity.this, username);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etUser = (EditText) findViewById(R.id.editText1);
		etPassword = (EditText) findViewById(R.id.editText2);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);


		Map<String, String> userInfoMap = SharePreference.getUserInfo(this);
		if (userInfoMap != null) {
			etUser.setText(userInfoMap.get("username"));
			checkBox.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {
		username = etUser.getText().toString().replaceAll(" ", "");
		password = etPassword.getText().toString().replaceAll(" ", "");

		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "user and password can't be empty",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			dialog = ProgressDialog.show(LoginActivity.this, null,
					"Please wait");
		}

		if (checkBox.isChecked()) {
			Log.i(TAG, username + ":" + password);
			boolean saveOk = SharePreference.saveUserInfo(this, username);
			// if(saveOk){
			// Toast.makeText(this, "Save Ok", Toast.LENGTH_SHORT).show();
			// }else{
			// Toast.makeText(this, "Fail to Save", Toast.LENGTH_SHORT).show();
			// }
		}
		new Thread(new Runnable() {

					@Override
					public void run() {

						final String state = NetUtils.LoginofPost(username,
								password);

						runOnUiThread(new Runnable() {
							Message msg = new Message();

							@Override
							public void run() {
								if ("Login Ok".equals(state)) {
									// SharePreference.saveAllInfo(LoginActivity.class,
									// username, password);

									msg.what = 0;
									handler.sendMessage(msg);
									dialog.dismiss();
									Toast.makeText(LoginActivity.this,
											"Welcome to Issue Tracking System",
											Toast.LENGTH_SHORT).show();
									// Intent i = new
									// Intent(MainActivity.this,PageActivity.class);
									// i.putExtra("username", username);
									// i.putExtra("password", password);
									// startActivity(i);
									Intent i = new Intent(LoginActivity.this,
											MainFragActivity.class);
									i.putExtra("username", username);
									i.putExtra("password", password);
									startActivity(i);
								} else {
									msg.what = 1;
									handler.sendMessage(msg);
									dialog.dismiss();
									Toast.makeText(LoginActivity.this,
											"Login Fail", Toast.LENGTH_LONG)
											.show();
								}

							}

						});
					}

				}).start();

	}
}
