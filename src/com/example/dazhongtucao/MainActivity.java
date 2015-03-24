package com.example.dazhongtucao;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.example.dazhongtucao.connect.BoomConnect;
import com.example.dazhongtucao.connect.FBInfoConnect;
import com.example.dazhongtucao.connect.GetTucaoConnect;
import com.example.dazhongtucao.connect.NearConnect;
import com.example.dazhongtucao.connect.ShopBriefInfoConnect;
import com.example.dazhongtucao.connect.TucaoCircleConnect;
import com.example.dazhongtucao.connect.TucaoInfoConnect;
import com.example.dazhongtucao.connect.UserInfoConnect;
import com.example.dazhongtucao.connect.ZanFBConnect;
import com.example.dazhongtucao.connect.ZanTCConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.FasterAnimationsContainer;
import com.example.dazhongtucao.tools.GenerateImage;
import com.example.dazhongtucao.tools.IsLogoExist;
import com.example.dazhongtucao.tools.StickyScrollView;
import com.example.dazhongtucao.view.OverlayShape;
import com.example.dazhongtucao.view.ShopFBCircleFrame;
import com.example.dazhongtucao.view.TucaoCircleFrame;
import com.example.dazhongtucao.view.TucaoFrame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	long mExitTime;
	int[] ShopID;
	int ShopNum;
	int currentShopID;
	String currentShopname;
	int[] BoomNum;
	String[] ShopName;
	int ID;
	int TCOrFB;

	// 记录当前页面的ID（0，1，2），以便双击刷新
	int currentTabID = 0;
	int currentTab;

	boolean isFirstLocation = true;

	ProgressDialog progressDialog = null;
	ProgressBar pb_tucaoframe;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker = null;

	// 地图实例
	private MapView mMapView = null;
	private BaiduMap mBaiduMap;

	/*
	 * private Marker mMarkerA; BitmapDescriptor bdA ;
	 */

	int screenHeight, screenWidth;
	boolean isMapPlus = false;// 标记当前地图是否为放大状态

	StickyScrollView scv;
	RelativeLayout mapFrame;
	ImageView ivMapPlusMinus;

	LinearLayout ll_tab21, ll_tab31;
	ScrollView sv_tab22, sv_tab32;
	TextView tv_nickname, tv_userboomnum, tv_sign, tv_focusnum, tv_fansnum,
			tv_tcnum;
	TextView tv_shopname, tv_boomnum;
	LinearLayout ll_mainpage_tucaoframe, ll_newtucaoframe;
	LinearLayout ll_tucaocircle;
	ImageView iv_userlogo;

	RelativeLayout rl_tucaoframe;
	LinearLayout ll_shopintroducation;
	
	RelativeLayout rl_gif;
	ImageView iv_bombgif;
	AnimationDrawable animationDrawable;
	FasterAnimationsContainer mFasterAnimationsContainer;
	private static final int[] IMAGE_RESOURCES = { R.drawable.bomb_p01,
			R.drawable.bomb_p02, R.drawable.bomb_p03, R.drawable.bomb_p04,
			R.drawable.bomb_p05, R.drawable.bomb_p06, R.drawable.bomb_p07,
			R.drawable.bomb_p08, R.drawable.bomb_p09, R.drawable.bomb_p10,
			R.drawable.bomb_p11, R.drawable.bomb_p12, R.drawable.bomb_p13,
			R.drawable.bomb_p14, R.drawable.bomb_p15, R.drawable.bomb_p16,
			R.drawable.bomb_p17, R.drawable.bomb_p18, R.drawable.bomb_p19 };
	
	private static final int ANIMATION_INTERVAL = 20;// 200ms

	IsLogoExist isLogoExist = new IsLogoExist();

	MyHandler1 handler1;
	MyHandler2 handler2;
	MyHandler3 handler3;
	MyHandler4 handler4;
	MyHandler5 handler5;
	MyHandler6 handler6;
	MyHandler7 handler7;
	MyHandler8 handler8;
	MyHandler9 handler9;
	MyHandler10 handler10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 屏幕参数
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenHeight = metric.heightPixels;
		screenWidth = metric.widthPixels;

		// 初始化地图，这个必须要有并且要在最先执行
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		pb_tucaoframe = (ProgressBar) findViewById(R.id.pb_mainpage_tucaoframe);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("客官请稍等...");
		progressDialog.setMessage("玩命加载数据中...");
		progressDialog.setCancelable(true);

		initTabHost();

		// 获得地图实例
		mMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mMapView.getMap();

		// 删除缩放控件
		mMapView.removeViewAt(2);

		// 定位
		mCurrentMode = LocationMode.NORMAL;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker));
		mBaiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// 设置缩放等级
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);

		// LinearLayout ll=OverlayShape.CreateOverlay(this, "restaurant",
		// "桂苑餐厅", 3);
		// bdA = BitmapDescriptorFactory.fromView(ll);
		// initOverlay();

		// 覆盖物点击事件
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				LatLng ll = marker.getPosition();
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				Toast.makeText(MainActivity.this, "" + marker.getTitle(),
						Toast.LENGTH_SHORT).show();
				int i = Integer.parseInt(marker.getTitle());
				currentShopID = ShopID[i];
				currentShopname = ShopName[i];
				tv_shopname.setText(ShopName[i]);
				tv_boomnum.setText(BoomNum[i] + "");
				ll_mainpage_tucaoframe.removeAllViews();
				ll_newtucaoframe.removeAllViews();

				/*
				 * LinearLayout.LayoutParams p=new
				 * LayoutParams(LayoutParams.MATCH_PARENT, 10); TextView tv=new
				 * TextView(MainActivity.this); tv.setLayoutParams(p);
				 * tv.setBackgroundResource(MainActivity.this.getResources()
				 * .getColor(R.color.lightGrey));
				 * ll_mainpage_tucaoframe.addView(tv);
				 */

				new Thread(r4).start();

				return true;
			}
		});

		scv = (StickyScrollView) findViewById(R.id.scv_mainpage);
		mMapView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					scv.requestDisallowInterceptTouchEvent(false);
				} else {
					scv.requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});

		/*
		 * Resources res=getResources(); Bitmap
		 * bmp=BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
		 * ImageView iv=(ImageView)findViewById(R.id.TestImage); RoundBitmap
		 * rb=new RoundBitmap(); iv.setImageBitmap(rb.toRoundBitmap(bmp));
		 */

		ll_mainpage_tucaoframe = (LinearLayout) findViewById(R.id.ll_mainpage_tucaoframe);
		ll_newtucaoframe = (LinearLayout) findViewById(R.id.ll_mainpage_newtucaoframe);

		rl_tucaoframe = (RelativeLayout) findViewById(R.id.rl_mainpage_tucaoframe);
		ll_shopintroducation = (LinearLayout) findViewById(R.id.ll_mainpage_shopintroducation);

		ll_tucaocircle = (LinearLayout) findViewById(R.id.ll_mainpage_tucaocircle);

		Button btn_tadeshouye = (Button) findViewById(R.id.btn_mainpage_tadeshouye);
		btn_tadeshouye.setOnClickListener(clickListener);

		Button btn_xiangtarenzhadan = (Button) findViewById(R.id.btn_mainpage_xiangtarengzhadan);
		btn_xiangtarenzhadan.setOnClickListener(clickListener);

		Button btn_tucaota = (Button) findViewById(R.id.btn_mainpage_tucaota);
		btn_tucaota.setOnClickListener(clickListener);

		// 实现地图放大缩小

		scv.setScrollable(true);
		mapFrame = (RelativeLayout) findViewById(R.id.map_frame);
		mapFrame.getLayoutParams().height = (int) (screenHeight * 0.3);
		ivMapPlusMinus = (ImageView) findViewById(R.id.iv_map_plusminus);
		ivMapPlusMinus.setOnClickListener(clickListener);

		Button btn_woyaotucao = (Button) findViewById(R.id.btn_woyaotucao);
		btn_woyaotucao.setOnClickListener(clickListener);

		RelativeLayout rly_tab2_search = (RelativeLayout) findViewById(R.id.rly_tab2_search);
		rly_tab2_search.setOnClickListener(clickListener);

		Button btn_mainpage_login = (Button) findViewById(R.id.btn_mainpage_login);
		btn_mainpage_login.setOnClickListener(clickListener);

		Button btn_mainpage_register = (Button) findViewById(R.id.btn_mainpage_register);
		btn_mainpage_register.setOnClickListener(clickListener);

		Button btn_mainpage_login2 = (Button) findViewById(R.id.btn_mainpage_login2);
		btn_mainpage_login2.setOnClickListener(clickListener);

		Button btn_mainpage_register2 = (Button) findViewById(R.id.btn_mainpage_register2);
		btn_mainpage_register2.setOnClickListener(clickListener);

		ll_tab21 = (LinearLayout) findViewById(R.id.ll_mainpage_tab21);
		ll_tab31 = (LinearLayout) findViewById(R.id.ll_mainpage_tab31);
		sv_tab22 = (ScrollView) findViewById(R.id.sv_mainpage_tab22);
		sv_tab32 = (ScrollView) findViewById(R.id.sv_mainpage_tab32);

		tv_nickname = (TextView) findViewById(R.id.tv_mainpage_nickname);
		tv_userboomnum = (TextView) findViewById(R.id.tv_mainpage_userboomnum);
		tv_sign = (TextView) findViewById(R.id.tv_mainpage_sign);
		tv_focusnum = (TextView) findViewById(R.id.tv_mainpage_focusnum);
		tv_fansnum = (TextView) findViewById(R.id.tv_mainpage_fansnum);
		tv_tcnum = (TextView) findViewById(R.id.tv_mainpage_tcnum);

		tv_shopname = (TextView) findViewById(R.id.tv_mainpage_shopname);
		tv_boomnum = (TextView) findViewById(R.id.tv_mainpage_boomnum);

		iv_userlogo = (ImageView) findViewById(R.id.iv_mainpage_userlogo);
		
		RelativeLayout rl_myinfo=(RelativeLayout)findViewById(R.id.rl_mainpage_myinfo);
		rl_myinfo.setOnClickListener(clickListener);

		RelativeLayout rl_achievement = (RelativeLayout) findViewById(R.id.rl_mainpage_myachievement);
		rl_achievement.setOnClickListener(clickListener);
		RelativeLayout rl_closeaccount = (RelativeLayout) findViewById(R.id.rl_mainpage_closeaccount);
		rl_closeaccount.setOnClickListener(clickListener);
		Button btn_exit = (Button) findViewById(R.id.btn_mainpage_exit);
		btn_exit.setOnClickListener(clickListener);

		rl_gif=(RelativeLayout)findViewById(R.id.rl_mainpage_gif);
		iv_bombgif=(ImageView)findViewById(R.id.iv_mainpage_bombgif);
		
		

		handler1 = new MyHandler1();
		handler2 = new MyHandler2();
		handler3 = new MyHandler3();
		handler4 = new MyHandler4();
		handler5 = new MyHandler5();
		handler6 = new MyHandler6();
		handler7 = new MyHandler7();
		handler8 = new MyHandler8();
		handler9 = new MyHandler9();
		handler10 = new MyHandler10();

	}

	// 点击事件
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_mainpage_tadeshouye:
				ConstantData.CurrentSelectedShopID = currentShopID;
				ConstantData.CurrentSelectedShopname = currentShopname;
				startActivity(new Intent(MainActivity.this,
						ShopMainpageActivity.class));
				break;
			case R.id.btn_mainpage_xiangtarengzhadan:
				if (ConstantData.isEnter) {
					rl_gif.setVisibility(View.VISIBLE);
					mFasterAnimationsContainer = FasterAnimationsContainer
							.getInstance(iv_bombgif);
					mFasterAnimationsContainer.addAllFrames(IMAGE_RESOURCES,
							ANIMATION_INTERVAL);
					mFasterAnimationsContainer.start();

					new Thread(r8).start();
					new Thread(r6).start();
				} else {
					startActivityForResult(new Intent(MainActivity.this,
							LoginActivity.class), 100);
				}
				break;
			case R.id.btn_mainpage_tucaota:
				if (ConstantData.isEnter) {
					ConstantData.CurrentSelectedShopID = currentShopID;
					ConstantData.CurrentSelectedShopname = currentShopname;
					startActivityForResult(new Intent(MainActivity.this,
							TucaoActivity.class), 110);
				} else {
					startActivityForResult(new Intent(MainActivity.this,
							LoginActivity.class), 100);
				}
				break;
			case R.id.iv_map_plusminus:
				
				System.out
						.println("Attention !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Attention!!!!!!!!\n"
								+ ConstantData.UserID
								+ " "
								+ ConstantData.Key
								+ " "
								+ ConstantData.UserName
								+ " "
								+ ConstantData.UserLogo1
								+ " "
								+ ConstantData.UserLogo2
								+ " "
								+ ConstantData.Latitude
								+ " "
								+ ConstantData.Longitude
								+ " "
								// + ConstantData.isEnter+" "
								+ ConstantData.CurrentType
								+ " "
								+ ConstantData.CurrentSelectedType
								+ " "
								+ ConstantData.CurrentSelectedShopname
								+ " "
								+ ConstantData.CurrentSelectedShopID
								+ " "
								+ ConstantData.CurrentSelectedUserID
								+ " "
								+ ConstantData.CurrentSelectedUsername
								+ " "
								//+ ConstantData.tucaoJson.toString()
								//+ " "
								+ ConstantData.tucaoLogo
								+ " "
								+ ConstantData.TucaoOrFB
								+ " "
								+ ConstantData.ID);
				if (isMapPlus) {
					mapFrame.getLayoutParams().height = (int) (screenHeight * 0.3);
					ivMapPlusMinus.setImageResource(R.drawable.ic_plus_map);
					isMapPlus = false;
					scv.setScrollable(true);
					ll_shopintroducation.setVisibility(View.VISIBLE);
					rl_tucaoframe.setVisibility(View.VISIBLE);
				} else {
					mapFrame.getLayoutParams().height = (int) (screenHeight * 0.75);
					ivMapPlusMinus.setImageResource(R.drawable.ic_minus_map);
					isMapPlus = true;
					scv.setScrollable(false);
					ll_shopintroducation.setVisibility(View.GONE);
					rl_tucaoframe.setVisibility(View.GONE);
				}
				break;
			case R.id.btn_woyaotucao:
				startActivity(new Intent(MainActivity.this, TypeActivity.class));
				break;
			case R.id.rly_tab2_search:
				startActivity(new Intent(MainActivity.this,
						SearchActivity.class));
				break;
			case R.id.rl_mainpage_myinfo:
				ConstantData.CurrentSelectedUserID=ConstantData.UserID;
				startActivity(new Intent(MainActivity.this,
						UserMainpageActivity.class));
				break;
			case R.id.rl_mainpage_myachievement:
				startActivity(new Intent(MainActivity.this,
						AchievementActivity.class));
				break;
			case R.id.rl_mainpage_closeaccount:
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage("确定切换账号吗？")
						.setCancelable(false)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										ConstantData.isEnter = false;
										ll_tab31.setVisibility(View.VISIBLE);
										sv_tab32.setVisibility(View.GONE);
										ll_tab21.setVisibility(View.VISIBLE);
										sv_tab22.setVisibility(View.GONE);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				
				break;
			case R.id.btn_mainpage_exit:
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						MainActivity.this);
				builder1.setMessage("确定退出大众吐槽吗？")
						.setCancelable(false)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert1 = builder1.create();
				alert1.show();
				break;
			case R.id.btn_mainpage_login2:
				startActivityForResult(new Intent(MainActivity.this,
						LoginActivity.class), 100);
				break;
			case R.id.btn_mainpage_register2:
				startActivity(new Intent(MainActivity.this,
						RegisterActivity.class));
				break;
			case R.id.btn_mainpage_login:
				startActivityForResult(new Intent(MainActivity.this,
						LoginActivity.class), 100);
				break;
			case R.id.btn_mainpage_register:
				startActivity(new Intent(MainActivity.this,
						RegisterActivity.class));
				break;
			}
		}
	};

	// TabHost初始化
	void initTabHost() {
		// 获取TabHost对象
		final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);

		// 如果没有继承TabActivity时，通过该种方法加载启动tabHost
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("", getResources().getDrawable(R.drawable.tab1))
				.setContent(R.id.view1));

		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("", getResources().getDrawable(R.drawable.tab2))
				.setContent(R.id.view2));

		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("", getResources().getDrawable(R.drawable.tab3))
				.setContent(R.id.view3));

		final TabWidget tabWidget = tabHost.getTabWidget();

		for (currentTab = 0; currentTab < tabWidget.getChildCount(); currentTab++) {
			View v = tabWidget.getChildAt(currentTab);
			v.setBackgroundDrawable(getResources().getDrawable(R.color.white));
		}

	}

	// 定位监听
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			ConstantData.Latitude = location.getLatitude();
			BigDecimal b1 = new BigDecimal(ConstantData.Latitude);
			ConstantData.Latitude = b1.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			ConstantData.Longitude = location.getLongitude();
			BigDecimal b2 = new BigDecimal(ConstantData.Longitude);
			ConstantData.Longitude = b2.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue();

			if (isFirstLocation) {
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				isFirstLocation = false;

				RelativeLayout rl_mapprogress = (RelativeLayout) findViewById(R.id.rl_mainpage_mapprogress);
				rl_mapprogress.setVisibility(View.GONE);

				new Thread(r1).start();
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	public Marker initOverlay(Marker marker, double La, double Lo,
			BitmapDescriptor bdA) {
		// add marker overlay
		LatLng llA = new LatLng(La, Lo);

		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA);
		marker = (Marker) (mBaiduMap.addOverlay(ooA));
		return marker;
	}

	Runnable r1 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			NearConnect nearConnect = new NearConnect();
			JSONObject json = nearConnect.Near(ConstantData.Longitude,
					ConstantData.Latitude);

			Message msg = new Message();
			Bundle b = new Bundle();

			b.putString("json", json.toString());
			msg.setData(b);
			handler2.sendMessage(msg);
		}

	};

	Runnable r2 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShopBriefInfoConnect shopBrief = new ShopBriefInfoConnect();

			for (int i = 0; i < ShopNum; i++) {
				JSONObject json = shopBrief.ShopBriefInfo(ShopID[i]);

				try {
					json.put("i", i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				Bundle b = new Bundle();

				b.putString("json", json.toString());
				msg.setData(b);
				handler3.sendMessage(msg);

				if (i == 0) {
					currentShopID = ShopID[0];
					Message msg1 = new Message();
					Bundle b1 = new Bundle();

					try {
						currentShopname = json.getString("Name");
						b1.putString("ShopName", json.getString("Name"));
						b1.putInt("BoomNum", json.getInt("BoomNum"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					msg1.setData(b1);
					handler6.sendMessage(msg1);
					new Thread(r4).start();
				}

			}
		}

	};

	Runnable r3 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			UserInfoConnect userInfoConnect = new UserInfoConnect();
			JSONObject json = userInfoConnect.UserInfo(ConstantData.UserID);

			isLogoExist.isLogoExist(json);

			Message msg = new Message();
			Bundle b = new Bundle();

			b.putString("json", json.toString());

			msg.setData(b);
			handler1.sendMessage(msg);

		}

	};

	Runnable r4 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			GetTucaoConnect getTucao = new GetTucaoConnect();
			JSONObject json = getTucao.GetTucao(currentShopID);
			try {
				if (json.getInt("Result") == 0) {
					Message msg = new Message();
					Bundle b = new Bundle();
					b.putString("Reason", json.getString("Reason"));
					msg.setData(b);
					handler4.sendMessage(msg);
				} else {
					TucaoInfoConnect tucaoInfo = new TucaoInfoConnect();
					JSONObject json1 = null;
					for (int i = 0; i < json.getInt("TCNum"); i++) {
						json1 = tucaoInfo.TucaoInfo(json.getInt("TCID" + i));

						isLogoExist.isLogoExist(json1);

						Message msg = new Message();
						Bundle b = new Bundle();
						b.putString("json", json1.toString());
						msg.setData(b);
						handler5.sendMessage(msg);
					}
					Message msg = new Message();
					Bundle b = new Bundle();
					msg.setData(b);
					handler8.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	Runnable r5 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			TucaoCircleConnect tucaoCircle = new TucaoCircleConnect();
			TucaoInfoConnect tucaoInfo = new TucaoInfoConnect();
			FBInfoConnect FBInfo = new FBInfoConnect();
			JSONObject json = tucaoCircle.TucaoCircle(ConstantData.UserID);
			try {
				if (json.getInt("Result") == 0) {
					Toast.makeText(MainActivity.this, "网络连接失败",
							Toast.LENGTH_SHORT).show();
				} else {
					int IDNum = json.getInt("IDNum");
					JSONObject json1;
					for (int i = 0; i < IDNum; i++) {
						int type = json.getInt("Type" + i);
						if (type == 0) {
							json1 = tucaoInfo.TucaoInfo(json.getInt("ID" + i));
						} else {
							json1 = FBInfo.FBInfo(json.getInt("ID" + i));
						}

						isLogoExist.isLogoExist(json1);

						Message msg = new Message();
						Bundle b = new Bundle();
						b.putInt("ID", json.getInt("ID" + i));
						b.putString("json", json1.toString());
						b.putInt("type", type);
						b.putInt("i", i);
						msg.setData(b);
						handler7.sendMessage(msg);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	Runnable r6 = new Runnable() {
		public void run() {
			BoomConnect boomConnect = new BoomConnect();
			String result = boomConnect.Boom(ConstantData.UserID,
					ConstantData.Key, currentShopID);
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("result", result);
			msg.setData(b);
			handler9.sendMessage(msg);
		}
	};

	Runnable r7 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (TCOrFB == 0) {
				ZanTCConnect zanTC = new ZanTCConnect();
				zanTC.zanTC(ID);
			} else {
				ZanFBConnect zanFB = new ZanFBConnect();
				zanFB.zanFB(ID);
			}
		}
	};
	
	Runnable r8=new Runnable() {
		public void run() {
			try {
				Thread.sleep(2500);
				Message msg=new Message();
				Bundle b=new Bundle();
				msg.setData(b);
				handler10.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class MyHandler1 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			try {
				JSONObject json = new JSONObject(b.getString("json"));

				int Result = json.getInt("Result");

				if (Result == 0) {
					Toast.makeText(MainActivity.this, json.getString("Reason"),
							Toast.LENGTH_SHORT).show();
					pb_tucaoframe.setVisibility(View.GONE);
				} else {

					GenerateImage gi = new GenerateImage();
					gi.GenerateImage(iv_userlogo, json.getString("Logo"));

					ConstantData.UserName = json.getString("NickName");
					ConstantData.UserLogo1 = json.getString("Logo");

					// ConstantData.CurrentType = json.getString("ShopType");

					tv_nickname.setText(json.getString("NickName"));
					tv_userboomnum.setText("" + json.getInt("BombNum"));
					tv_sign.setText(json.getString("Sign"));
					tv_focusnum.setText(json.getInt("FocusNum") + "");
					tv_fansnum.setText(json.getInt("FansNum") + "");
					tv_tcnum.setText(json.getInt("TCNum") + "");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyHandler2 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			try {
				JSONObject json = new JSONObject(b.getString("json"));

				int Result = json.getInt("Result");

				if (Result == 0) {
					Toast.makeText(MainActivity.this, json.getString("Reason"),
							Toast.LENGTH_SHORT).show();
					pb_tucaoframe.setVisibility(View.INVISIBLE);
				} else {
					ShopNum = json.getInt("ShopNum");
					ShopID = new int[ShopNum];
					BoomNum = new int[ShopNum];
					ShopName = new String[ShopNum];
					for (int i = 0; i < ShopNum; i++) {
						ShopID[i] = json.getInt("ShopID" + i);
					}
					new Thread(r2).start();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyHandler3 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			try {
				JSONObject json = new JSONObject(b.getString("json"));

				int Result = json.getInt("Result");

				if (Result == 0) {
					Toast.makeText(MainActivity.this, json.getString("Reason"),
							Toast.LENGTH_SHORT).show();
					pb_tucaoframe.setVisibility(View.GONE);
				} else {
					Marker mMarker = null;
					LinearLayout ll = OverlayShape.CreateOverlay(
							MainActivity.this, "restaurant",
							json.getString("Name"), json.getInt("BoomNum"));
					BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(ll);
					mMarker = initOverlay(mMarker, json.getDouble("La"),
							json.getDouble("Lo"), bdA);
					mMarker.setTitle("" + json.getInt("i"));

					BoomNum[json.getInt("i")] = json.getInt("BoomNum");
					ShopName[json.getInt("i")] = json.getString("Name");

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyHandler4 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			Toast.makeText(MainActivity.this, b.getString("Reason"),
					Toast.LENGTH_SHORT).show();

			pb_tucaoframe.setVisibility(View.GONE);

		}
	}

	class MyHandler5 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			final Bundle b = msg.getData();
			try {
				final JSONObject json = new JSONObject(b.getString("json"));
				if (json.getInt("Result") == 0) {
					Toast.makeText(MainActivity.this, json.getString("Reason"),
							Toast.LENGTH_SHORT).show();
				} else {

					TucaoFrame tucaoFrame = new TucaoFrame();

					LinearLayout ll = tucaoFrame.CreateTucaoFrame(json,
							MainActivity.this);
					ll.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ConstantData.tucaoJson = json;
							ConstantData.TucaoOrFB = 0;
							try {
								ConstantData.ID = json.getInt("TCID");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(MainActivity.this,
									ReplyInfoActivity.class));
						}
					});
					
					ll_mainpage_tucaoframe.addView(ll);

					final ImageView iv_zan = tucaoFrame.getZanImage();
					final TextView tv_zannum = tucaoFrame.getZanTv();
					final TextView tv_name=tucaoFrame.getName();
					final ImageView iv_logo=tucaoFrame.getLogo();

					iv_zan.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (!ConstantData.isEnter) {
								startActivityForResult(
										new Intent(MainActivity.this,
												LoginActivity.class), 100);
							} else {
								try {
									ID = json.getInt("TCID");
									TCOrFB=0;
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								new Thread(r7).start();
								iv_zan.setImageResource(R.drawable.zan2);
								iv_zan.setClickable(false);
								tv_zannum.setText((Integer.parseInt(tv_zannum.getText()
										.toString()) + 1) + "");
							}
						}
					});
					
					iv_logo.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedUserID = json
										.getInt("UserID");
								ConstantData.CurrentSelectedUsername = json
										.getString("NickName");
								ConstantData.UserLogo1 = json.getString("Logo");
								startActivity(new Intent(MainActivity.this,
										UserMainpageActivity.class));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

					tv_name.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedUserID = json
										.getInt("UserID");
								ConstantData.CurrentSelectedUsername = json
										.getString("NickName");
								ConstantData.UserLogo1 = json.getString("Logo");
								startActivity(new Intent(MainActivity.this,
										UserMainpageActivity.class));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyHandler6 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();
			tv_shopname.setText(b.getString("ShopName"));
			tv_boomnum.setText(b.getInt("BoomNum") + "");

		}
	}

	class MyHandler7 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			final Bundle b = msg.getData();
			int type = b.getInt("type");
			try {
				final JSONObject json = new JSONObject(b.getString("json"));
				if (type == 0) {
					TucaoCircleFrame tucaoCircleFrame=new TucaoCircleFrame();
					LinearLayout ll = tucaoCircleFrame
							.CreateTucaoCircleFrame(json, MainActivity.this,
									b.getInt("i"), screenWidth);
					ll_tucaocircle.addView(ll);
					ll.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ConstantData.tucaoJson = json;
							ConstantData.TucaoOrFB = 0;
							try {
								ConstantData.ID = json.getInt("TCID");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(MainActivity.this,
									ReplyInfoActivity.class));
						}
					});
					
					final ImageView iv_zan = tucaoCircleFrame.getZanImage();
					final TextView tv_zannum = tucaoCircleFrame.getZanTv();
					ImageView iv_logo=tucaoCircleFrame.getLogo();
					TextView tv_username=tucaoCircleFrame.getUserName();
					TextView tv_shopname=tucaoCircleFrame.getShopName();

					iv_zan.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ID = json.getInt("ID");
								TCOrFB=0;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							new Thread(r7).start();
							iv_zan.setImageResource(R.drawable.zan2);
							iv_zan.setClickable(false);
							tv_zannum.setText((Integer.parseInt(tv_zannum.getText()
									.toString()) + 1) + "");

						}
					});
					
					iv_logo.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedUserID = json
										.getInt("UserID");
								ConstantData.CurrentSelectedUsername = json
										.getString("NickName");
								ConstantData.UserLogo1 = json.getString("Logo");
								startActivity(new Intent(MainActivity.this,
										UserMainpageActivity.class));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					
					tv_username.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedUserID = json
										.getInt("UserID");
								ConstantData.CurrentSelectedUsername = json
										.getString("NickName");
								ConstantData.UserLogo1 = json.getString("Logo");
								startActivity(new Intent(MainActivity.this,
										UserMainpageActivity.class));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					
					tv_shopname.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedShopID = json.getInt("ShopID");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(MainActivity.this,
									ShopMainpageActivity.class));
						}
					});
					
				} else {
					
					ShopFBCircleFrame shopFBCircleFrame=new ShopFBCircleFrame();
					
					LinearLayout ll = shopFBCircleFrame
							.CreateShopFBCircleFrame(json, MainActivity.this,
									b.getInt("i"), screenWidth);
					ll_tucaocircle.addView(ll);
					ll.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ConstantData.tucaoJson = json;
							ConstantData.TucaoOrFB = 1;
							ConstantData.ID = b.getInt("ID");
							startActivity(new Intent(MainActivity.this,
									ReplyInfoActivity.class));
						}
					});
					
					final ImageView iv = shopFBCircleFrame.getZanImage();
					final TextView tv = shopFBCircleFrame.getZanTv();
					ImageView iv_logo=shopFBCircleFrame.getLogo();
					TextView tv_name=shopFBCircleFrame.getName();

					iv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ID = json.getInt("ID");
								TCOrFB=1;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							new Thread(r7).start();
							iv.setImageResource(R.drawable.zan2);
							iv.setClickable(false);
							tv.setText((Integer.parseInt(tv.getText()
									.toString()) + 1) + "");

						}
					});
					
					//因接口问题此功能暂时被干掉
					
					iv_logo.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedShopID = json.getInt("ShopID");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(MainActivity.this,
									ShopMainpageActivity.class));
						}
					});
					
					tv_name.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								ConstantData.CurrentSelectedShopID = json.getInt("ShopID");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							startActivity(new Intent(MainActivity.this,
									ShopMainpageActivity.class));
						}
					});
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class MyHandler8 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			pb_tucaoframe.setVisibility(View.GONE);
		}
	}

	class MyHandler9 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			if (!b.getString("result").equals("您的炸弹数量不足")) {
				tv_boomnum.setText((Integer.parseInt(tv_boomnum.getText()
						.toString()) + 1) + "");
				tv_userboomnum.setText((Integer.parseInt(tv_userboomnum
						.getText().toString()) - 1) + "");
			}

			Toast.makeText(MainActivity.this, b.getString("result"),
					Toast.LENGTH_SHORT).show();
		}
	}
	
	class MyHandler10 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			try {
				rl_gif.setVisibility(View.GONE);
				//mFasterAnimationsContainer.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mMapView = null;

		// bdA.recycle();

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// 100登录后个人信息,110吐槽
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			ll_tab31.setVisibility(View.GONE);
			sv_tab32.setVisibility(View.VISIBLE);
			ll_tab21.setVisibility(View.GONE);
			sv_tab22.setVisibility(View.VISIBLE);

			ll_tucaocircle.removeAllViews();

			new Thread(r3).start();
			new Thread(r5).start();

		} else if (requestCode == 110 && resultCode == Activity.RESULT_OK) {
			tv_tcnum.setText((Integer.parseInt(tv_tcnum.getText().toString()) + 1)
					+ "");

			JSONObject json = new JSONObject();

			try {
				json.put("NickName", ConstantData.UserName);
				json.put("TC", data.getExtras().getString("content"));
				json.put("Anger",
						Integer.parseInt(data.getExtras().getString("anger")));
				// json.put("ShopType", ConstantData.CurrentType);
				json.put("ShopName", currentShopname);
				json.put("Time", "刚刚");
				json.put("ReNum", 0);
				json.put("ZanNum", 0);
				json.put("Logo", ConstantData.UserLogo1);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			TucaoFrame tucaoFrame = new TucaoFrame();

			LinearLayout ll = tucaoFrame.CreateTucaoFrame(json,
					MainActivity.this);
			ll_newtucaoframe.addView(ll);
			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "刷新才能执行操作哦",
							Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
