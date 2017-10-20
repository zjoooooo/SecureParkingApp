package com.secureparking.ulti;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SharePreference {

	public static boolean saveUserInfo(Context context, String user) {
		try {

			SharedPreferences sp = context.getSharedPreferences("secure",
					Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("user", user);
			edit.commit();
			return true;
		} catch (Exception e) {
			// throw new RuntimeException();
			Toast.makeText(context, "Save Function is not available",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public static boolean saveAllInfo(Context context, String user,
			String password) {
		try {

			SharedPreferences sp = context.getSharedPreferences("secure",
					Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("user", user);
			edit.putString("password", password);
			edit.commit();
			return true;
		} catch (Exception e) {
			// throw new RuntimeException();
			Toast.makeText(context, "Save Function is not available",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	public static Map<String, String> getUserInfo(Context context) {
		SharedPreferences sp = context.getSharedPreferences("secure",
				Context.MODE_PRIVATE);
		String user = sp.getString("user", null);
		if (!TextUtils.isEmpty(user)) {
			Map<String, String> userInfoMap = new HashMap<String, String>();
			userInfoMap.put("username", user);
			return userInfoMap;
		}

		return null;

	}

}
