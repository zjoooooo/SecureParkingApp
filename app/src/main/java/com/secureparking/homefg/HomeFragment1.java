package com.secureparking.homefg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.secureparking.sr.R;
import com.secureparking.ulti.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment1 extends Fragment implements OnPageChangeListener {


	private List<HashMap<String, String>> li;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private ListView listView;
	private HomeListViewAdp1 adapter;
	// private List<HashMap<String, String>> list;
	private Handler handler;
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * Dot ImageView array
	 */
	private ImageView[] tips;

	/**
	 * ImageView array for pic
	 */
	private ImageView[] mImageViews;

	/**
	 * Pic resource id
	 */
	private int[] imgIdArray;

	public HomeFragment1() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		new DrawerBuilder().withActivity(getActivity()).build();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = null;
		view = inflater.inflate(R.layout.home_frag1, container, false);

		listView = (ListView) view.findViewById(R.id.lv_home_frag1);
		ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) view.findViewById(R.id.home_viewPager);

		// pic resource
		imgIdArray = new int[] { R.drawable.item01, R.drawable.item02,
				R.drawable.item03, R.drawable.item04, R.drawable.item05,
				R.drawable.item06, R.drawable.item07, R.drawable.item08,
				R.drawable.item09, R.drawable.item10 };

		// Add dot to ViewGroup
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(15, 15));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.dark_dot);
			}

			group.addView(imageView);
		}

		// Load pic to pic array
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());

			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					imgIdArray[i]);
			int bmpwidth = bmp.getWidth();
			int bmpheight = bmp.getHeight();
			Matrix matrix = new Matrix();
			Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, bmpwidth, bmpheight,
					matrix, true);
			// imageView.setScaleType(ScaleType.MATRIX); //required
			// matrix.postRotate(180f,imageView.getDrawable().getBounds().width()/2,
			// imageView.getDrawable().getBounds().height()/2);
			// imageView.setImageMatrix(matrix);
			imageView.setImageBitmap(bm);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setAdjustViewBounds(true);
			// imageView.setBackgroundResource();
			mImageViews[i] = imageView;
		}
		// set pic Adapter
		viewPager.setAdapter(new MyAdapter());
		// set dot change listener
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem((mImageViews.length) * 100);
		// listView.setAdapter(new HomeListViewAdp1(getActivity(), li));
		return view;
	}




	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void init() {
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetOpenIssueList("justin",
							"weishenme");
					// String jsonstr=null;
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
		/******************* ***********************/
		JSONArray jsonArray = null;
		// ��ʼ��list�������
		ArrayList<HashMap<String, String>> lis = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			// ��ʼ��map�������
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Carpark", jsonObject.getString("Carpark"));
			map.put("Station", jsonObject.getString("Station"));
			map.put("ReportTime", jsonObject.getString("ReportTime"));
			map.put("Issue", jsonObject.getString("Issue"));
			map.put("ID", jsonObject.getString("ID"));
			lis.add(map);
		}
		return lis;
	}

	// class MyClickListener implements OnClickListener {
	//
	// int index;
	//
	// public MyClickListener(int i) {
	// index = i;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// Log.d("onclick", "onclick " + index);
	// }
	//
	// }

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);

		}

		/**
		 *
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}

	/**
	 *
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.dark_dot);
			}
		}
	}
}
