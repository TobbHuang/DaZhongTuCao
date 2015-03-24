package com.example.dazhongtucao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.LogoConnect;
import com.example.dazhongtucao.connect.ShopInfoConnect;
import com.example.dazhongtucao.connect.ShopListConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.GetImageBitmap;
import com.example.dazhongtucao.tools.IsLogoExist;

import Decoder.BASE64Decoder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ShopListActivity extends Activity {

	ListView list_selectshop;
	int TotalShopNum;
	int[] ShopID;
	ArrayList<HashMap<String, Object>> mylist;

	ProgressBar pb_shoplist;

	IsLogoExist isLogoExist = new IsLogoExist();

	MyHandler myHandler;
	MyHandler2 myHandler2;
	MyHandler3 myHandler3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_list);

		list_selectshop = (ListView) findViewById(R.id.list_selectshiop);
		mylist = new ArrayList<HashMap<String, Object>>();
		list_selectshop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ConstantData.CurrentSelectedShopID = ShopID[arg2];
				HashMap<String, Object> map = (HashMap<String, Object>) list_selectshop
						.getItemAtPosition(arg2);
				ConstantData.CurrentSelectedShopname = (String) map
						.get("shopname");
				startActivity(new Intent(ShopListActivity.this,
						ShopMainpageActivity.class));
			}
		});

		myHandler = new MyHandler();
		myHandler2 = new MyHandler2();
		myHandler3 = new MyHandler3();

		pb_shoplist = (ProgressBar) findViewById(R.id.pb_shoplist);

		LinearLayout ll_shoplist_back = (LinearLayout) findViewById(R.id.ll_shoplist_turnback);
		ll_shoplist_back.setOnClickListener(onClickListener);

		new Thread(r1).start();

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ll_shoplist_turnback:
				finish();
				break;
			}
		}
	};

	Runnable r1 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShopListConnect shopListConnect = new ShopListConnect();
			try {
				JSONObject json = shopListConnect
						.ShopList(ConstantData.CurrentSelectedType);
				if (json.getBoolean("Result")) {
					TotalShopNum = json.getInt("ShopNum");
					if (TotalShopNum != 0) {
						ShopID = new int[TotalShopNum];
						for (int i = 0; i < TotalShopNum; i++) {
							ShopID[i] = json.getInt("ShopID" + i);
						}
						new Thread(r2).start();
					} else {
						Message msg = new Message();
						Bundle b = new Bundle();
						msg.setData(b);
						myHandler3.sendMessage(msg);
					}
				} else {
					Message msg = new Message();
					Bundle b = new Bundle();
					msg.setData(b);
					myHandler2.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	Runnable r2 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShopInfoConnect shopInfoConnect = new ShopInfoConnect();
			JSONObject json = null;
			GetImageBitmap getImageBp = new GetImageBitmap();
			for (int i = 0; i < TotalShopNum; i++) {
				json = shopInfoConnect.ShopInfo(ShopID[i],
						ConstantData.Longitude, ConstantData.Latitude);
				try {
					if (json.getInt("Result") == 1) {

						isLogoExist.isLogoExist(json);

						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("logo", getImageBp.GenerateImage(json
								.getString("Logo")));
						map.put("shopname", json.getString("Name"));
						map.put("type", R.drawable.restaurant);
						map.put("shopwords", "【商家发布】测试中…");
						map.put("tucaonum", json.getInt("TCNum") + "");
						map.put("fansnum", json.getInt("FansNum") + "");
						map.put("distance", json.getInt("Distance") + "m");
						mylist.add(map);

					} else {
						Message msg = new Message();
						Bundle b = new Bundle();
						msg.setData(b);
						myHandler2.sendMessage(msg);

						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (json.getInt("Result") == 1) {
					Message msg = new Message();
					Bundle b = new Bundle();
					msg.setData(b);
					myHandler.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			SimpleAdapter adapter = new SimpleAdapter(
					ShopListActivity.this,
					mylist,
					R.layout.shopitem,
					new String[] { "logo", "shopname", "type", "shopwords",
							"tucaonum", "fansnum", "distance" },
					new int[] { R.id.iv_shopitem_logo, R.id.tv_shopitem_name,
							R.id.iv_shopitem_type, R.id.tv_shopitem_shopwords,
							R.id.tv_shopitem_tucaonum,
							R.id.tv_shopitem_fansnum, R.id.tv_shopitem_distance });
			adapter.setViewBinder(new MyViewBinder());
			list_selectshop.setAdapter(adapter);

			pb_shoplist.setVisibility(View.GONE);

		}
	}

	class MyHandler2 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Toast.makeText(ShopListActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
					.show();

			pb_shoplist.setVisibility(View.GONE);
		}
	}

	class MyHandler3 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			pb_shoplist.setVisibility(View.GONE);
		}
	}

	public class MyViewBinder implements ViewBinder {
		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			if ((view instanceof ImageView) & (data instanceof Bitmap)) {
				ImageView iv = (ImageView) view;
				Bitmap bm = (Bitmap) data;
				iv.setImageBitmap(bm);
				return true;
			}
			return false;
		}
	}

}
