package com.secureparking.techfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.secureparking.sr.SRPageActivity;
import com.secureparking.sr.R;
import com.secureparking.ulti.NetUtils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment1 extends Fragment implements IXListViewListener {

	private List<HashMap<String, String>> li;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private final int GetSearchData = 8;
	private final int AthorizedUser = 2;
	private final int NoOneAttend = 3;
	private final int UnAthorizedUser = 4;
	private final int error = 5;
	private final int nocase = 6;
	private final int setattendok = 7;
	private XListView listView;
	private MyListViewAdp1 adapter;
	private List<HashMap<String, String>> list;
	private Handler handler = new Handler() {
		/**
		 * ������Ϣ
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				list = (List<HashMap<String, String>>) msg.obj;
				adapter = new MyListViewAdp1(getActivity(), list);
				listView.setAdapter(adapter);
				listView.setXListViewListener(Fragment1.this);
				dialog.dismiss();
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Toast.makeText(
						// getActivity(),
						// "You select a item position :" + position
						// + " Long:" + id, Toast.LENGTH_SHORT).show();
						ID = list.get(position - 1).get("ID");
						CarPark = list.get(position - 1).get("Carpark");
						station = list.get(position - 1).get("Station");
						Issue = list.get(position - 1).get("Issue");
						BO = list.get(position - 1).get("BO");
						dialog = ProgressDialog.show(getActivity(), null,
								"Checking Data");
						System.out.println("username=" + username
								+ ",password=" + password + ",ID=" + ID
								+ ",CarPark=" + CarPark);
						new Thread() {
							@Override
							public void run() {
								String result = NetUtils.Checkattend(username,
										password, ID, CarPark);
								Message msg = new Message();
								if ("Ok".equals(result)) {
									msg.what = AthorizedUser;
								} else if ("nobody".equals(result)) {
									msg.what = NoOneAttend;
								} else if ("no case".equals(result)) {
									msg.what = nocase;
								} else if ("error".equals(result)) {
									msg.what = error;
								} else {
									msg.what = UnAthorizedUser;
									msg.obj = result;
									System.out.println("CheckAttend_Result="
											+ result);
								}
								handlerForAttend.sendMessage(msg);
							};
						}.start();
					}
				});
				break;
			case FAILED:
				Toast.makeText(getActivity(), "Network error", 0).show();
				dialog.dismiss();
				break;
			default:
				dialog.dismiss();
				break;
			}

		}

	};
	private Handler handlerForAttend = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			default:
				dialog.dismiss();
				break;
			case AthorizedUser:
				dialog.dismiss();
				Intent i = new Intent(getActivity(), SRPageActivity.class);
				i.putExtra("username", username);
				i.putExtra("password", password);
				i.putExtra("ID", ID);
				i.putExtra("Carpark", CarPark);
				i.putExtra("Station", station);
				i.putExtra("Issue", Issue);
				i.putExtra("BO", BO);
				startActivity(i);
				break;
			case UnAthorizedUser:
				dialog.dismiss();
				String attendermsg = (String) msg.obj;
				System.out.println(attendermsg);
				String[] parts = attendermsg.split(":");
				String att = parts[1];
				AlertDialog.Builder Alertdialog = new AlertDialog.Builder(
						getActivity());

				Alertdialog.setTitle("Hi,"
						+ username.substring(0, 1).toUpperCase()
						+ username.substring(1));
				Alertdialog.setMessage("We already have a attender " + att
						+ " for car park " + CarPark);
				Alertdialog.setCancelable(false);
				Alertdialog.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				Alertdialog.create().show();
				break;

			case NoOneAttend:
				dialog.dismiss();
				AlertDialog.Builder Alertdialog2 = new AlertDialog.Builder(
						getActivity());
				Alertdialog2.setTitle("Hi,"
						+ username.substring(0, 1).toUpperCase()
						+ username.substring(1));
				Alertdialog2.setMessage("Are you sure to attend this case?");
				Alertdialog2.setCancelable(false);

				Alertdialog2.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog2,
									int which) {
								dialog = ProgressDialog.show(getActivity(),
										null, "Attending case");
								new Thread() {
									@Override
									public void run() {
										String result = NetUtils
												.Registerattend(username,
														password, ID, CarPark);
										System.out.println("attending result="
												+ result);
										Message msg = new Message();
										if ("setattendok".equals(result)) {
											msg.what = setattendok;
										}
										handlerForAttend.sendMessage(msg);
									};
								}.start();

							}
						});

				Alertdialog2.setNegativeButton("No", null);
				Alertdialog2.create().show();

				break;
			case error:
				dialog.dismiss();
				break;
			case nocase:
				dialog.dismiss();
				break;
			case setattendok:
				dialog.dismiss();
				init();
				System.out.println("Reach handlerForAttendOk");
				AlertDialog.Builder Alertdialog3 = new AlertDialog.Builder(
						getActivity());
				Alertdialog3.setTitle("Hi,"
						+ username.substring(0, 1).toUpperCase()
						+ username.substring(1));
				Alertdialog3
						.setMessage("Well done!!You have already attend this case,please click on the issue card again to write service report!");
				Alertdialog3.setCancelable(false);
				Alertdialog3.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				Alertdialog3.create().show();

				break;
			}

		}
	};
	private String username;
	private String password;
	private String ID, CarPark, station, Issue, BO;
	private Intent i;
	private ProgressDialog dialog;
	private EditText search_text;
	private Button search_btn;

	public Fragment1(Intent i) {
		this.i = i;
		username = i.getStringExtra("username");
		password = i.getStringExtra("password");
	}

	public Fragment1() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("frag1 onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		// SystemClock.sleep(2000);
		view = inflater.inflate(R.layout.tracking_openissue_frag1, container,
				false);
		listView = (XListView) view.findViewById(R.id.lv_openissuelist);
		search_text = (EditText) view.findViewById(R.id.edtx_search);
		search_btn = (Button) view.findViewById(R.id.button_search);
		System.out.println("frag1 onCreateView");

		search_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// check search_text make sure there is content.
				if (TextUtils.isEmpty(search_text.getText().toString())) {
					Toast.makeText(getActivity(),
							"Make sure your input is not empty.", 2).show();
					return;
				}
				// Send content to server get reply.
				initSearch();

			}
		});

		search_text
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
							// search_text.requestFocus();
							initSearch();
							// return true;
						}
						return false;
					}
				});

		return view;
	}

	public void initSearch() {
		// Send content to server get reply.

		dialog = ProgressDialog.show(getActivity(), null, "Searching...");
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetSearchList(username, password,
							search_text.getText().toString());
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
					dialog.dismiss();
				}

			}
		}.start();
	}

	@Override
	public void onStart() {
		init();
		super.onStart();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		System.out.println("frag1 onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		// listView.setRefreshTime("3000");
	}

	@Override
	public void onRefresh() {
		init();
		onLoad();
	}

	@Override
	public void onLoadMore() {

	}

	public void init() {
		dialog = ProgressDialog.show(getActivity(), null, "Getting Data");
		new Thread() {

			@Override
			public void run() {

				try {
					String jsonstr = NetUtils.GetOpenIssueList(username,
							password);
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
					dialog.dismiss();
				}

			}
		}.start();

	}

	public ArrayList<HashMap<String, String>> Analysis(String jsonStr)
			throws JSONException {
		JSONArray jsonArray = null;
		ArrayList<HashMap<String, String>> lis = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Carpark", jsonObject.getString("Carpark"));
			map.put("Station", jsonObject.getString("Station"));
			map.put("ReportTime", jsonObject.getString("ReportTime"));
			map.put("Issue", jsonObject.getString("Issue"));
			map.put("ID", jsonObject.getString("ID"));
			map.put("Reportby", jsonObject.getString("Reportby"));
			map.put("AttendBy", jsonObject.getString("AttendBy"));
			map.put("Status", jsonObject.getString("Status"));
			map.put("BO", jsonObject.getString("BO"));
			map.put("Priority",jsonObject.getString("Priority"));// new add Piority
			lis.add(map);
		}
		return lis;
	}

}
