package com.secureparking.ulti;

import android.util.Log;

import com.secureparking.dao.SrInfrom;
import com.secureparking.sr.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {
	private static final String TAG = "NetUtils";

	public static String loginOfGetHttpClient(String name, String password) {
		HttpClient client = null;
		try {

			client = new DefaultHttpClient();
			String data = "username=" + name + "&password=" + password;
			HttpGet get = new HttpGet(R.string.SerivceReportLogin_url + data);

			HttpResponse response = client.execute(get);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				InputStream is = response.getEntity().getContent();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
		return null;
	}

	/**
	 * submit service report
	 * 
	 * @param sr
	 * @return
	 */
	public static String PostSr(SrInfrom sr) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/SubmitSR");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + sr.getName() + "&password="
					+ sr.getPassword() + "&carpark=" + sr.getCarpark()
					+ "&station=" + sr.getStation() + "&bo=" + sr.getBo()
					+ "&followby=" + sr.getFollowby() + "&item=" + sr.getItem()
					+ "&issue=" + sr.getIssue() + "&solution="
					+ sr.getSolution() + "&qty=" + sr.getQty() + "&starttime="
					+ sr.getStarttime() + "&endtime=" + sr.getEndtime()
					+ "&otstarttime=" + sr.getOtstarttime() + "&otendtime="
					+ sr.getOtendtime() + "&id=" + sr.getId();
			Log.e(TAG, data);
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * GetIssueList
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String GetOpenIssueList(String name, String password) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetIssueList");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + name + "&password=" + password;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				return "fail";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return "fail";
	}

	/**
	 * GetInventoryList
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String GetInventoryList() {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/ItemList");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			// String data ="username="+username+"&password="+password;
			// OutputStream out = conn.getOutputStream();
			// out.write(data.getBytes());
			// out.flush();
			// out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SP staff List
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String GetStaffList() {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/StaffList");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			// String data ="username="+username+"&password="+password;
			// OutputStream out = conn.getOutputStream();
			// out.write(data.getBytes());
			// out.flush();
			// out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * GetIssueListClosed
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String GetClosedIssueList(String name, String password) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetIssueListClosed");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + name + "&password=" + password;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				return "fail";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "fail";
	}

	/**
	 * GetIssueListClosed
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String GetSrList(String name, String password) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetSrList");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + name + "&password=" + password;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				return "fail";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "fail";
	}

	/**
	 * Login
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String LoginofPost(String name, String password) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/login");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + name + "&password=" + password;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ʹ��Get��ʽ��½
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String LoginofGet(String name, String password) {
		HttpURLConnection conn;
		try {
			String data = "username=" + name + "&password=" + password;
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/login?" + data);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("GET"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getStringFromInputStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		String state = baos.toString();
		baos.close();
		return state;
	}

	public static String getCarparkLatilng() {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/MapInfo");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			// String data ="username="+username+"&password="+password;
			// OutputStream out = conn.getOutputStream();
			// out.write(data.getBytes());
			// out.flush();
			// out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getBlockLatilng(String blockNum) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetBlock");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "blockNum=" + blockNum;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String CheckUpdate(String url) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(url);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			// conn.setDoOutput(true);
			// String data ="blockNum="+blockNum;
			// OutputStream out = conn.getOutputStream();
			// out.write(data.getBytes());
			// out.flush();
			// out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String getCarparkData(String carpark) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetCarParkLots");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "carpark=" + carpark;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param ID
	 * @param CarPark
	 */
	public static String Checkattend(String username, String password,
			String ID, String CarPark) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/CheckAttend");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + username + "&password=" + password
					+ "&ID=" + ID + "&CarPark=" + CarPark;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param ID
	 * @param CarPark
	 * @return
	 */
	public static String Registerattend(String username, String password,
			String ID, String CarPark) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/RegisterAttend");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + username + "&password=" + password
					+ "&ID=" + ID + "&CarPark=" + CarPark;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				Log.e(TAG, "Visit Fail : " + responseCode);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String GetSearchList(String username, String password,
			String text) {
		HttpURLConnection conn;
		try {
			URL mUrl = new URL(
					"http://119.73.147.11/SecureParking/servlet/GetSearchList");
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("POST"); // �趨��ȡ��ʽ
			conn.setConnectTimeout(10000); // ���ӳ�ʱ
			conn.setReadTimeout(5000); // ��ȡ��ʱ
			conn.setDoOutput(true);
			String data = "username=" + username + "&password=" + password
					+ "&searchtext=" + text;
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				InputStream is = conn.getInputStream();
				return getStringFromInputStream(is);
			} else {
				return "fail";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return "fail";
	}

}
