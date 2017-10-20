package com.secureparking.sr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.secureparking.sr.R;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.secureparking.ulti.NetUtils;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class IssueListView extends Activity implements IXListViewListener {

	private Toolbar toolbar = null;
	private static final int PROFILE_SETTING = 100000;
	//save our header or result
	private AccountHeader headerResult = null;
	private Drawer result = null;



	private XListView lv_issue;
	private List<HashMap<String, String>> li;
	private final int SUCCESS = 0;
	private final int FAILED = 1;
	private ProgressDialog dialog;
	private String username;
	private String password;
	private Handler handler = new Handler() {

		/**
		 * ������Ϣ
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				li = (List<HashMap<String, String>>) msg.obj;
				MyAadpter adapter = new MyAadpter();
				lv_issue.setAdapter(adapter);
				lv_issue.setXListViewListener(IssueListView.this);
				lv_issue.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Toast.makeText(IssueListView.this,
						// "You select a item position :"+position+" Long:"+id,
						// Toast.LENGTH_SHORT).show();
						Intent i = new Intent(IssueListView.this,
								SRPageActivity.class);
						i.putExtra("username", username);
						i.putExtra("password", password);
						i.putExtra("ID", li.get(position - 1).get("ID"));
						i.putExtra("Carpark",
								li.get(position - 1).get("Carpark"));
						i.putExtra("Station",
								li.get(position - 1).get("Station"));
						i.putExtra("Issue", li.get(position - 1).get("Issue"));
						startActivity(i);
					}
				});

				break;
			case FAILED:
				Toast.makeText(
						IssueListView.this,
						"you can't get new data,please check your internet connection",
						0).show();
				break;
			default:
				break;
			}
			dialog.dismiss();
		}

	};
	private String tag = "IssueListView";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tracking_openissue_frag1);
		dialog = ProgressDialog.show(IssueListView.this, null, "Loading data");
		lv_issue = (XListView) findViewById(R.id.lv_openissuelist);
		lv_issue.setPullLoadEnable(true);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
		// getSupportFragmentManager().beginTransaction().replace(arg0, hf,
		// "Home").commit();
		init();


//		//if you want to update the items at a later time it is recommended to keep it in a variable
//		PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
//		SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
//		//toolbar = (Toolbar)findViewById(R.id.toolsbar);
//		//setSupportActionBar(toolbar);
//
//		// Create a few sample profile
//		// NOTE you have to define the loader logic too. See the CustomApplication for more details
//		final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withIcon(R.drawable.profile2).withIdentifier(100);
////        final IProfile profile2 = new ProfileDrawerItem().withName("Demo User").withEmail("demo@github.com").withIcon("https://avatars2.githubusercontent.com/u/3597376?v=3&s=460").withIdentifier(101);
////        final IProfile profile3 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.profile2).withIdentifier(102);
////        final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(R.drawable.profile3).withIdentifier(103);
////        final IProfile profile5 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(R.drawable.profile4).withIdentifier(104);
////        final IProfile profile6 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(R.drawable.profile5).withIdentifier(105);105
//
//		// Create the AccountHeader
//		headerResult = new AccountHeaderBuilder()
//				.withActivity(this)
//				.withTranslucentStatusBar(true)
//				.withHeaderBackground(R.drawable.header)
//				.addProfiles(
//						profile
////                        profile2,
////                        profile3,
////                        profile4,
////                        profile5,
////                        profile6,
//						//don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
////                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(R.drawable.profile).withIdentifier(PROFILE_SETTING),
////                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(R.drawable.profile).withIdentifier(100001)
//				)
//				.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
//					@Override
//					public boolean onProfileChanged(View view, IProfile profile, boolean current) {
//						//sample usage of the onProfileChanged listener
//						//if the clicked item has the identifier 1 add a new profile ;)
//						if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
//							int count = 100 + headerResult.getProfiles().size() + 1;
//							IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
//							if (headerResult.getProfiles() != null) {
//								//we know that there are 2 setting elements. set the new profile above them ;)
//								headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
//							} else {
//								headerResult.addProfiles(newProfile);
//							}
//						}
//
//						//false if you have not consumed the event and it should close the drawer
//						return false;
//					}
//				})
//				.withSavedInstance(savedInstanceState)
//				.build();
//
//
//		//create the drawer and remember the `Drawer` result object
//		Drawer result = new DrawerBuilder()
//				.withActivity(this)
//				.withToolbar(toolbar)
//				.withHasStableIds(true)
//				// .withItemAnimator(new AlphaCrossFadeAnimator())
//				.withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
//				.addDrawerItems(
//						new PrimaryDrawerItem().withName(R.string.drawer_item1).withDescription(R.string.drawer_item1_compact_header_desc).withIcon(R.drawable.settingicon).withIdentifier(1).withSelectable(true),
//						new PrimaryDrawerItem().withName(R.string.drawer_item2).withDescription(R.string.drawer_item2_compact_header_desc).withIcon(R.drawable.settingicon).withIdentifier(2).withSelectable(true),
//						new PrimaryDrawerItem().withName(R.string.drawer_item3).withDescription(R.string.drawer_item3_compact_header_desc).withIcon(R.drawable.settingicon).withIdentifier(3).withSelectable(true)
//				)
//				.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//					@Override
//					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//						// do something with the clicked item :D
//
//						if (drawerItem != null) {
//							Intent intent = null;
//							if (drawerItem.getIdentifier() == 1) {
//								//intent = new Intent(DrawerActivity.this, CompactHeaderDrawerActivity.class);
//								Toast.makeText(IssueListView.this,"this is item1",Toast.LENGTH_LONG).show();
//								return true;
//							} else if (drawerItem.getIdentifier() == 2) {
//								//intent = new Intent(DrawerActivity.this, ActionBarActivity.class);
//								Toast.makeText(IssueListView.this,"this is item2",Toast.LENGTH_LONG).show();
//								return true;
//							} else if (drawerItem.getIdentifier() == 3) {
//								// intent = new Intent(DrawerActivity.this, MultiDrawerActivity.class);
//								Toast.makeText(IssueListView.this,"this is item3",Toast.LENGTH_LONG).show();
//								return true;
//							}
//						}else{
//							return false;
//						}
//						return false;
//
//					}
//				})
//				.withSavedInstance(savedInstanceState)
//				.withShowDrawerOnFirstLaunch(true)
//				.withShowDrawerUntilDraggedOpened(true).build();
//
//
	}

	// private void geneItems() {
	// for (int i = 0; i != 5; ++i) {
	// items.add("refresh cnt " + (++start));
	// }
	// }

	private void onLoad() {
		lv_issue.stopRefresh();
		lv_issue.stopLoadMore();
		// lv_issue.setRefreshTime("Yes");
	}

	@Override
	public void onRefresh() {
		// if(refreshflag){
		// refreshflag=false;

		init();
		onLoad();

		// refreshflag=true;

		//
		// }
	}

	@Override
	public void onLoadMore() {

	}

	private void init() {
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
				}
			}
		}.start();
	}

	class MyAadpter extends BaseAdapter {

		private TextView tv_caprark;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return li.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				LayoutInflater inflater = getLayoutInflater();
				view = inflater.inflate(R.layout.issuecontentopen, null);
			} else {
				view = convertView;
			}
			tv_caprark = (TextView) view
					.findViewById(R.id.tv_openissue_carpark);
			TextView tv_station = (TextView) view
					.findViewById(R.id.tv_openissue_station);
			TextView tv_reporttime = (TextView) view
					.findViewById(R.id.tv_openissue_reporttime);
			TextView tv_issue = (TextView) view
					.findViewById(R.id.tv_openissue_content);
			TextView tv_id = (TextView) view.findViewById(R.id.tv_openissue_ID);

			HashMap<String, String> newInfo = li.get(position);
			tv_caprark.setText(newInfo.get("Carpark"));
			tv_station.setText(newInfo.get("Station"));
			tv_reporttime.setText(newInfo.get("ReportTime"));
			tv_issue.setText(newInfo.get("Issue"));
			tv_id.setText(newInfo.get("ID"));
			return view;
		}

	}

	/**
	 * ����
	 * 
	 * @throws JSONException
	 */
	private static ArrayList<HashMap<String, String>> Analysis(String jsonStr)
			throws JSONException {
		/******************* ���� ***********************/
		JSONArray jsonArray = null;

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		jsonArray = new JSONArray(jsonStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Carpark", jsonObject.getString("Carpark"));
			map.put("Station", jsonObject.getString("Station"));
			map.put("ReportTime", jsonObject.getString("ReportTime"));
			map.put("Issue", jsonObject.getString("Issue"));
			map.put("ID", jsonObject.getString("ID"));
			list.add(map);
		}
		return list;
	}

}
