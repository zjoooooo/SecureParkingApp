package com.secureparking.homefg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.secureparking.sr.R;

public class HomeFragment2 extends Fragment {
	private ImageView website;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("frag2 onCreate");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_frag2, container, false);
		website = (ImageView) view.findViewById(R.id.website);
		System.out.println("frag2 onCreateView");
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		website.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://www.secureparking.com.sg";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		super.onActivityCreated(savedInstanceState);
	}
}
