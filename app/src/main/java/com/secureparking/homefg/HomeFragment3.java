package com.secureparking.homefg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.secureparking.sr.R;

public class HomeFragment3 extends Fragment {
	private static String[] list = { "Car Park Management Services",
			"Sale Of Parking Equipment & Accessories", "Valet Services",
			"Car Wash Services", "Online Booking", "24hrs Monitoring Services",
			"Mobile Enforcement", "Revenue Survey" };
	public static final String[] imageUrls = {
			"http://119.73.147.3/SecureParking/PIC/1.png",
			"http://119.73.147.3/SecureParking/PIC/2.png",
			"http://119.73.147.3/SecureParking/PIC/3.jpg",
			"http://119.73.147.3/SecureParking/PIC/4.jpg",
			"http://119.73.147.3/SecureParking/PIC/5.jpg",
			"http://119.73.147.3/SecureParking/PIC/6.png",
			"http://119.73.147.3/SecureParking/PIC/7.png",
			"http://119.73.147.3/SecureParking/PIC/8.jpg" };
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_frag3, container, false);
		ListView lv = (ListView) view.findViewById(R.id.homefrag3_list);
		lv.setAdapter(new ViewAdp3());
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder dialog = new Builder(getActivity(),
						AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

				dialog.setTitle(list[position]);
				dialog.setCancelable(false);
				dialog.setMessage("sample!!!\nsample!!!\nsample!!!\nsample!!!\nsample!!!\nsample!!!\n");
				dialog.setPositiveButton("Close", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				// dialog.setNegativeButton("No", new OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				//
				// }
				// });
				dialog.create().show();
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayedImages.clear();
	}

	private static class ViewHolder {
		TextView text;
		ImageView image;
	}

	class ViewAdp3 extends BaseAdapter {

		// private static int[] imgIdArray = {
		// R.drawable.carparkmanagementservices,
		// R.drawable.saleofparkingequipmentaccessories,
		// R.drawable.valet20parking, R.drawable.carwash,
		// R.drawable.book_online, R.drawable.monitoring,
		// R.drawable.mobileenforcement, R.drawable.survey };
		private LayoutInflater inflater;

		public ViewAdp3() {
			inflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return list.length;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// View view = null;
			// if (convertView == null) {
			// view = inflater
			// .inflate(R.layout.homepage3listviewcontent, null);
			// } else {
			// view = convertView;
			// }
			// TextView title = (TextView)
			// view.findViewById(R.id.services_title);
			// ImageView im = (ImageView) view.findViewById(R.id.service_img);
			// title.setText(list[position]);
			// ImageLoader.getInstance().displayImage(imageUrls[position], im,
			// options, animateFirstListener);
			// return view;
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = inflater.inflate(R.layout.homepage3listviewcontent,
						parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.services_title);
				holder.image = (ImageView) view.findViewById(R.id.service_img);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.text.setText(list[position]);

			ImageLoader.getInstance().displayImage(imageUrls[position],
					holder.image, options, animateFirstListener);

			return view;
		}

	}

	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		final static List<String> displayedImages = Collections
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
