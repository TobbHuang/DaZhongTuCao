package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.BoomConnect;
import com.example.dazhongtucao.connect.FBInfoConnect;
import com.example.dazhongtucao.connect.FocusShopConnect;
import com.example.dazhongtucao.connect.GetShopFBConnect;
import com.example.dazhongtucao.connect.GetTucaoConnect;
import com.example.dazhongtucao.connect.ShopInfoConnect;
import com.example.dazhongtucao.connect.TucaoInfoConnect;
import com.example.dazhongtucao.connect.ZanFBConnect;
import com.example.dazhongtucao.connect.ZanTCConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.FasterAnimationsContainer;
import com.example.dazhongtucao.tools.GenerateImage;
import com.example.dazhongtucao.tools.IsLogoExist;
import com.example.dazhongtucao.tools.RoundBitmap;
import com.example.dazhongtucao.view.ShopFBFrame;
import com.example.dazhongtucao.view.TucaoFrame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopMainpageActivity extends Activity {

	int ID;
	int TCOrFB;

	TextView tv_ShopWords;
	TextView tv_TucaoWords;
	TextView tv_select1, tv_select2;
	LinearLayout ll_ShopWords_list;
	LinearLayout ll_TucaoWords_list;
	LinearLayout ll_shopmainpage_shopwords;
	LinearLayout ll_shopmainpage_tucaowords;
	LinearLayout ll_newtucaowords;
	LinearLayout lly_turnback;
	Button btn_focus, btn_tucaota, btn_boom;
	ImageView iv_Logo;
	TextView tv_tag1, tv_tag2, tv_tag3;
	TextView tv_sum;
	
	AnimationDrawable animationDrawable;
	RelativeLayout rl_gif;
	ImageView iv_bombgif;
	FasterAnimationsContainer mFasterAnimationsContainer;
	private static final int[] IMAGE_RESOURCES = { R.drawable.bomb_p01,
			R.drawable.bomb_p02, R.drawable.bomb_p03, R.drawable.bomb_p04,
			R.drawable.bomb_p05, R.drawable.bomb_p06, R.drawable.bomb_p07,
			R.drawable.bomb_p08, R.drawable.bomb_p09, R.drawable.bomb_p10,
			R.drawable.bomb_p11, R.drawable.bomb_p12, R.drawable.bomb_p13,
			R.drawable.bomb_p14, R.drawable.bomb_p15, R.drawable.bomb_p16,
			R.drawable.bomb_p17, R.drawable.bomb_p18, R.drawable.bomb_p19 };
	
	private static final int ANIMATION_INTERVAL = 20;// 200ms

	int[] location1 = new int[2];
	int[] location2 = new int[2];

	String shopname;

	IsLogoExist isLogoExist = new IsLogoExist();

	// 1基本信息，2toast错误信息，3FB，4Tucao，5关注
	MyHandler1 myHandler1;
	MyHandler2 myHandler2;
	MyHandler3 myHandler3;
	MyHandler4 myHandler4;
	MyHandler5 myHandler5;
	MyHandler6 myHandler6;
	MyHandler7 myHandler7;

	TextView tv_shopname, tv_fansnum, tv_boomnum;

	ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_mainpage);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("客官请稍等...");
		progressDialog.setMessage("玩命加载数据中...");
		progressDialog.setCancelable(true);

		tv_ShopWords = (TextView) findViewById(R.id.tv_ShopWords);
		tv_TucaoWords = (TextView) findViewById(R.id.tv_TucaoWords);
		tv_select1 = (TextView) findViewById(R.id.tv_shopmainpage_select1);
		tv_select2 = (TextView) findViewById(R.id.tv_shopmainpage_select2);
		ll_ShopWords_list = (LinearLayout) findViewById(R.id.ll_ShopWords_list);
		ll_TucaoWords_list = (LinearLayout) findViewById(R.id.TucaoWords_list);
		ll_shopmainpage_shopwords = (LinearLayout) findViewById(R.id.ll_shopmainpage_shopwords);
		ll_shopmainpage_tucaowords = (LinearLayout) findViewById(R.id.ll_shopmainpage_tucaowords);
		ll_newtucaowords = (LinearLayout) findViewById(R.id.ll_shopmainpage_newtucaowords);
		tv_shopname = (TextView) findViewById(R.id.tv_shopmainpage_shopname);
		tv_fansnum = (TextView) findViewById(R.id.tv_shopmainpage_fansnum);
		tv_boomnum = (TextView) findViewById(R.id.tv_shopmainpage_boomnum);
		btn_focus = (Button) findViewById(R.id.btn_shopmainpage_focus);
		btn_focus.setOnClickListener(onClickListener);
		btn_tucaota = (Button) findViewById(R.id.btn_shopmainpage_tucaota);
		btn_tucaota.setOnClickListener(onClickListener);
		btn_boom = (Button) findViewById(R.id.btn_shopmainpage_boom);
		btn_boom.setOnClickListener(onClickListener);
		tv_tag1 = (TextView) findViewById(R.id.tv_shopmainpage_tag1);
		tv_tag2 = (TextView) findViewById(R.id.tv_shopmainpage_tag2);
		tv_tag3 = (TextView) findViewById(R.id.tv_shopmainpage_tag3);
		tv_sum = (TextView) findViewById(R.id.tv_shopmainpage_sum);
		
		rl_gif=(RelativeLayout)findViewById(R.id.rl_shopmainpage_gif);
		iv_bombgif=(ImageView)findViewById(R.id.iv_shopmainpage_bombgif);

		iv_Logo = (ImageView) findViewById(R.id.iv_shopmainpage_logo);
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.icon);
		RoundBitmap rb = new RoundBitmap();
		iv_Logo.setImageBitmap(rb.toRoundBitmap(bmp));

		tv_select1.getLocationOnScreen(location1);
		tv_select2.getLocationOnScreen(location2);

		tv_ShopWords.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				tv_select1.setVisibility(View.VISIBLE);
				tv_select2.setVisibility(View.GONE);

				/*
				 * Animation translateAnimation = new TranslateAnimation(
				 * location2[0], location1[0], location1[1], location1[1]);
				 * translateAnimation.setDuration(1000);
				 * tv_select1.startAnimation(translateAnimation);
				 */

				tv_ShopWords.setTextColor(ShopMainpageActivity.this
						.getResources().getColor(R.color.black));
				tv_TucaoWords.setTextColor(ShopMainpageActivity.this
						.getResources().getColor(R.color.darkGrey));

				ll_shopmainpage_shopwords.setVisibility(View.VISIBLE);
				ll_shopmainpage_tucaowords.setVisibility(View.GONE);
			}
		});

		tv_TucaoWords.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				tv_select2.setVisibility(View.VISIBLE);
				tv_select1.setVisibility(View.GONE);

				tv_ShopWords.setTextColor(ShopMainpageActivity.this
						.getResources().getColor(R.color.darkGrey));
				tv_TucaoWords.setTextColor(ShopMainpageActivity.this
						.getResources().getColor(R.color.black));

				ll_shopmainpage_tucaowords.setVisibility(View.VISIBLE);
				ll_shopmainpage_shopwords.setVisibility(View.GONE);
			}
		});

		lly_turnback = (LinearLayout) findViewById(R.id.lly_shopmainpage_turnback);
		lly_turnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		myHandler1 = new MyHandler1();
		myHandler2 = new MyHandler2();
		myHandler3 = new MyHandler3();
		myHandler4 = new MyHandler4();
		myHandler5 = new MyHandler5();
		myHandler6 = new MyHandler6();
		myHandler7 = new MyHandler7();

		progressDialog.show();

		new Thread(r1).start();
		// new Thread(r5).start();

	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_shopmainpage_focus:
				if (!ConstantData.isEnter) {
					Toast.makeText(ShopMainpageActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
				} else {
					new Thread(r4).start();
					btn_focus.setClickable(false);
				}
				break;
			case R.id.btn_shopmainpage_boom:
				if (!ConstantData.isEnter) {
					Toast.makeText(ShopMainpageActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
				} else {
					new Thread(r5).start();
					rl_gif.setVisibility(View.VISIBLE);
					mFasterAnimationsContainer = FasterAnimationsContainer
							.getInstance(iv_bombgif);
					mFasterAnimationsContainer.addAllFrames(IMAGE_RESOURCES,
							ANIMATION_INTERVAL);
					mFasterAnimationsContainer.start();
					new Thread(r7).start();
				}
				break;
			case R.id.btn_shopmainpage_tucaota:
				if (!ConstantData.isEnter) {
					Toast.makeText(ShopMainpageActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
				} else {
					ConstantData.CurrentSelectedShopname = shopname;
					startActivityForResult(new Intent(
							ShopMainpageActivity.this, TucaoActivity.class),
							110);
				}
				break;
			}

		}
	};

	// 1shopinfo,2FB,3Tucao,4关注,5丢炸弹
	Runnable r1 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ShopInfoConnect shopInfoConnect = new ShopInfoConnect();

			// progressDialog.show();

			JSONObject json = shopInfoConnect.ShopInfo(
					ConstantData.CurrentSelectedShopID, ConstantData.Longitude,
					ConstantData.Latitude);

			isLogoExist.isLogoExist(json);

			try {

				// progressDialog.dismiss();

				Message msg = new Message();
				Bundle b = new Bundle();

				b.putString("json", json.toString());
				b.putString("name", json.getString("Logo"));

				msg.setData(b);
				myHandler1.sendMessage(msg);
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
			GetShopFBConnect getShopFBConnect = new GetShopFBConnect();
			JSONObject json = getShopFBConnect
					.GetShopFB(ConstantData.CurrentSelectedShopID);
			try {
				if (json.getInt("Result") == 0) {
					Message msg = new Message();
					Bundle b = new Bundle();
					msg.setData(b);
					myHandler2.sendMessage(msg);
				} else {
					FBInfoConnect FBInfo = new FBInfoConnect();
					JSONObject json1;
					for (int i = 0; i < json.getInt("FBNum"); i++) {
						json1 = FBInfo.FBInfo(json.getInt("FBID" + i));

						isLogoExist.isLogoExist(json1);

						Message msg = new Message();
						Bundle b = new Bundle();
						b.putString("json", json1.toString());
						b.putInt("ID", json.getInt("FBID" + i));
						msg.setData(b);
						myHandler3.sendMessage(msg);
						if (!json1.getBoolean("Result"))
							break;
					}
					progressDialog.dismiss();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	Runnable r3 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			GetTucaoConnect getTucaoConnect = new GetTucaoConnect();
			JSONObject json = getTucaoConnect
					.GetTucao(ConstantData.CurrentSelectedShopID);
			try {
				if (json.getInt("Result") == 0) {
					Message msg = new Message();
					Bundle b = new Bundle();
					msg.setData(b);
					myHandler2.sendMessage(msg);
				} else {
					TucaoInfoConnect TucaoInfo = new TucaoInfoConnect();
					JSONObject json1;
					for (int i = 0; i < json.getInt("TCNum"); i++) {
						json1 = TucaoInfo.TucaoInfo(json.getInt("TCID" + i));

						isLogoExist.isLogoExist(json1);

						Message msg = new Message();
						Bundle b = new Bundle();
						b.putString("json", json1.toString());
						b.putInt("ID", json.getInt("TCID" + i));
						msg.setData(b);
						myHandler4.sendMessage(msg);
						if (json1.getInt("Result") == 0) {
							Toast.makeText(ShopMainpageActivity.this,
									"网络连接失败(┬＿┬)", Toast.LENGTH_SHORT).show();
							break;
						}
					}
					progressDialog.dismiss();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	Runnable r4 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			FocusShopConnect focusShop = new FocusShopConnect();
			String result = focusShop.FocusShop(ConstantData.UserID,
					ConstantData.Key, ConstantData.CurrentSelectedShopID);
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("result", result);
			msg.setData(b);
			myHandler5.sendMessage(msg);

		}

	};

	Runnable r5 = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			BoomConnect boomConnect = new BoomConnect();
			String result = boomConnect.Boom(ConstantData.UserID,
					ConstantData.Key, ConstantData.CurrentSelectedShopID);
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("result", result);
			msg.setData(b);
			myHandler6.sendMessage(msg);
		}

	};

	Runnable r6 = new Runnable() {

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
	
	Runnable r7=new Runnable() {
		public void run() {
			try {
				Thread.sleep(2500);
				Message msg=new Message();
				Bundle b=new Bundle();
				msg.setData(b);
				myHandler7.sendMessage(msg);
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

			// progressDialog.dismiss();

			Bundle b = msg.getData();

			try {
				JSONObject json = new JSONObject(b.getString("json"));

				int Result = json.getInt("Result");
				if (Result == 0) {
					Toast.makeText(ShopMainpageActivity.this,
							json.getString("Reason"), Toast.LENGTH_SHORT)
							.show();
					progressDialog.dismiss();
				} else {
					GenerateImage generateImage = new GenerateImage();
					generateImage.GenerateImage(iv_Logo, b.getString("name"));
					shopname = json.getString("Name");
					tv_shopname.setText(json.getString("Name"));
					tv_sum.setText("简介："+json.getString("Sum"));
					tv_fansnum.setText(json.getString("FansNum"));
					tv_boomnum.setText(json.getString("BoomNum"));
					for (int i = 0; i < json.getInt("TagNum"); i++) {
						switch (i) {
						case 0:
							tv_tag1.setText(json.getString("Tag0"));
							break;
						case 1:
							tv_tag2.setText(json.getString("Tag1"));
							break;
						case 2:
							tv_tag3.setText(json.getString("Tag2"));
							break;
						}
					}
					new Thread(r2).start();
					new Thread(r3).start();
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

			progressDialog.dismiss();

			Toast.makeText(ShopMainpageActivity.this, "网络连接失败(┬＿┬)",
					Toast.LENGTH_SHORT).show();

		}
	}

	class MyHandler3 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			final Bundle b = msg.getData();
			try {
				final JSONObject json = new JSONObject(b.getString("json"));

				if (!json.getBoolean("Result")) {
					Toast.makeText(ShopMainpageActivity.this,
							json.getString("Reason"), Toast.LENGTH_SHORT)
							.show();
				} else {
					ShopFBFrame shopFBFrame=new ShopFBFrame();
					LinearLayout ll = shopFBFrame.CreateShopFBFrame(json,
							ShopMainpageActivity.this);
					ll_ShopWords_list.addView(ll);
					ll.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ConstantData.tucaoJson = json;
							ConstantData.TucaoOrFB = 1;
							ConstantData.ID = b.getInt("ID");
							startActivity(new Intent(ShopMainpageActivity.this,
									ReplyInfoActivity.class));
						}
					});
					
					final ImageView iv = shopFBFrame.getZanImage();
					final TextView tv = shopFBFrame.getZanTv();

					iv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							ID = b.getInt("ID");
							TCOrFB=1;
							new Thread(r6).start();
							iv.setImageResource(R.drawable.zan2);
							iv.setClickable(false);
							tv.setText((Integer.parseInt(tv.getText()
									.toString()) + 1) + "");

						}
					});
					
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

			final Bundle b = msg.getData();
			try {
				final JSONObject json = new JSONObject(b.getString("json"));

				if (json.getInt("Result") == 0) {
					Toast.makeText(ShopMainpageActivity.this,
							json.getString("Reason"), Toast.LENGTH_SHORT)
							.show();
				} else {

					TucaoFrame tucaoFrame = new TucaoFrame();

					LinearLayout ll = tucaoFrame.CreateTucaoFrame(json,
							ShopMainpageActivity.this);

					ll_TucaoWords_list.addView(ll);
					ll.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ConstantData.tucaoJson = json;
							ConstantData.TucaoOrFB = 0;
							ConstantData.ID = b.getInt("ID");
							startActivity(new Intent(ShopMainpageActivity.this,
									ReplyInfoActivity.class));
						}
					});

					final ImageView iv = tucaoFrame.getZanImage();
					final TextView tv = tucaoFrame.getZanTv();

					iv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (!ConstantData.isEnter)
								Toast.makeText(ShopMainpageActivity.this,
										"请先登录", Toast.LENGTH_SHORT).show();
							else {
								iv.setImageResource(R.drawable.zan2);
								iv.setClickable(false);
								tv.setText((Integer.parseInt(tv.getText()
										.toString()) + 1) + "");
								try {
									ID = json.getInt("TCID");
									TCOrFB=0;
									new Thread(r6).start();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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

	class MyHandler5 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();
			String result = b.getString("result");
			Toast.makeText(ShopMainpageActivity.this, result,
					Toast.LENGTH_SHORT).show();
			int temp = Integer.parseInt(tv_fansnum.getText().toString());
			tv_fansnum.setText(++temp + "");
			btn_focus.setText("已关注");

		}
	}

	class MyHandler6 extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			Bundle b = msg.getData();

			Toast.makeText(ShopMainpageActivity.this, b.getString("result"),
					Toast.LENGTH_SHORT).show();

			if (!b.getString("result").equals("您的炸弹数量不足"))
				tv_boomnum.setText((Integer.parseInt(tv_boomnum.getText()
						.toString()) + 1) + "");

		}
	}
	
	class MyHandler7 extends Handler{
		@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				rl_gif.setVisibility(View.GONE);
			}
	}

	// 110吐槽
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 110 && resultCode == Activity.RESULT_OK) {

			JSONObject json = new JSONObject();

			try {
				json.put("NickName", ConstantData.UserName);
				json.put("TC", data.getExtras().getString("content"));
				json.put("Anger",
						Integer.parseInt(data.getExtras().getString("anger")));
				// json.put("ShopType", ConstantData.CurrentType);
				json.put("ShopName", ConstantData.CurrentSelectedShopname);
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
					ShopMainpageActivity.this);
			ll_newtucaowords.addView(ll);
			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(ShopMainpageActivity.this, "刷新才能执行操作哦",
							Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

}
