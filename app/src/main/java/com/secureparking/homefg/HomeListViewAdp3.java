package com.secureparking.homefg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.secureparking.sr.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeListViewAdp3 extends BaseAdapter {

	LayoutInflater inflater;
	private static String[] list = { "Car park management services",
			"Sale of parking equipment & accessories", "Valet services",
			"Car wash services", "Online Booking", "24hrs Monitoring services",
			"Mobile enforcement", "Revenue survey" };
	// private static int[] imgIdArray = { R.drawable.carparkmanagementservices,
	// R.drawable.saleofparkingequipmentaccessories,
	// R.drawable.valet20parking, R.drawable.carwash,
	// R.drawable.book_online, R.drawable.monitoring,
	// R.drawable.mobileenforcement, R.drawable.survey };
	public static final String[] imageUrls = {
			"http://119.73.147.3/SecureParking/PIC/1.png",
			"http://119.73.147.3/SecureParking/PIC/2.png",
			"http://119.73.147.3/SecureParking/PIC/3.jpg",
			"http://119.73.147.3/SecureParking/PIC/4.jpg",
			"http://119.73.147.3/SecureParking/PIC/5.jpg",
			"http://119.73.147.3/SecureParking/PIC/6.jpg",
			"http://119.73.147.3/SecureParking/PIC/7.png",
			"http://119.73.147.3/SecureParking/PIC/8.jpg" };
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(20)).build();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	FragmentActivity activity;

	public HomeListViewAdp3(FragmentActivity fragmentActivity) {
		// TODO Auto-generated constructor stub
		this.activity = fragmentActivity;
		inflater = (LayoutInflater) fragmentActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			view = inflater.inflate(R.layout.homepage3listviewcontent, null);
		} else {
			view = convertView;
		}
		TextView title = (TextView) view.findViewById(R.id.services_title);
		ImageView im = (ImageView) view.findViewById(R.id.service_img);
		title.setText(list[position]);
		ImageLoader.getInstance().displayImage(imageUrls[position], im,
				options, animateFirstListener);
		return view;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
