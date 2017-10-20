package com.secureparking.techfg;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.secureparking.sr.R;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.keyboardsurfer.android.widget.crouton.Crouton;

public class MainFragActivity extends FragmentActivity {

	private ArrayList<Fragment> list = null;
	private Intent i;
	private Fragment zh;
	private Fragment xw;
	private Fragment yl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		i = getIntent();
		setContentView(R.layout.simple_titles);
		initViewPager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list));
		// TabPageIndicator indicator =
		// (TabPageIndicator)findViewById(R.id.indicator1);
		TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.home2_indicator);
		indicator.setViewPager(pager);

		// Initial Jpush
		JPushInterface.setDebugMode(false);
		JPushInterface.init(this);
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				MainFragActivity.this);
		builder.statusBarDrawable = R.drawable.securelogo;
		// builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
		builder.notificationDefaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;

		JPushInterface.setPushNotificationBuilder(1, builder);
		Set<String> tag = new HashSet<String>();
		String name = i.getStringExtra("username").toUpperCase();
		tag.add(name);
		tag.add("technician");
		tag.add("all");
		JPushInterface.setTags(this, tag, new TagAliasCallback() {

			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				if (arg0 == 0) {
					System.out.println("Set Tag ok!!");
				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// JPushInterface.onResume(getApplicationContext());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// JPushInterface.onPause(getApplicationContext());
	}

	private void initViewPager() {
		zh = new Fragment1(i);
		xw = new Fragment2(i);
		yl = new Fragment3(i);

		list = new ArrayList<Fragment>();
		list.add(zh);
		list.add(xw);
		list.add(yl);
		// list.add(ty);

	}

}
