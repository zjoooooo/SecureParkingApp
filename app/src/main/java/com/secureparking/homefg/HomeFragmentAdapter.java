package com.secureparking.homefg;

import java.util.ArrayList;

import com.secureparking.sr.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viewpagerindicator.IconPagerAdapter;

public class HomeFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private ArrayList<Fragment> list;
	private static final String[] CONTENT = new String[] { "Page1", "About Us",
			"Our Services" };
	protected static final int[] ICONS = new int[] {
			R.drawable.perm_group_calendar, R.drawable.perm_group_camera,
			R.drawable.perm_group_device_alarms, R.drawable.perm_group_location };

	private int mCount = CONTENT.length;

	public HomeFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public HomeFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length];
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCount;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

	@Override
	public int getIconResId(int index) {
		return ICONS[index % ICONS.length];
	}

}
