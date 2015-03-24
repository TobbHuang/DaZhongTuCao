package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.FocusUserConnect;
import com.example.dazhongtucao.connect.MyTCConnect;
import com.example.dazhongtucao.connect.TucaoInfoConnect;
import com.example.dazhongtucao.connect.UserInfoConnect;
import com.example.dazhongtucao.connect.ZanTCConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.GenerateImage;
import com.example.dazhongtucao.view.TucaoFrame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserMainpageActivity extends Activity {
	
	int ID;
	
	LinearLayout ll_tucaowords;
	Button btn_focus;
	TextView tv_focusnum,tv_fansnum,tv_sign;
	
	MyHandler1 myHandler1;
	MyHandler2 myHandler2;
	MyHandler3 myHandler3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_mainpage);
		
		btn_focus=(Button)findViewById(R.id.btn_usermainpage_focus);
		btn_focus.setOnClickListener(onClickListener);
		
		TextView tv_name=(TextView)findViewById(R.id.tv_usermainpage_username);
		tv_name.setText(ConstantData.CurrentSelectedUsername);
		
		ImageView iv_logo=(ImageView)findViewById(R.id.iv_usermainpage_logo);
		
		GenerateImage gi=new GenerateImage();
		gi.GenerateImage(iv_logo, ConstantData.UserLogo1);
		
		ll_tucaowords=(LinearLayout)findViewById(R.id.ll_usermainpage_tucaowords);
		
		tv_focusnum=(TextView)findViewById(R.id.tv_usermainpage_focusnum);
		tv_fansnum=(TextView)findViewById(R.id.tv_usermainpage_fansnum);
		tv_sign=(TextView)findViewById(R.id.tv_usermainpage_sign);
		
		LinearLayout ll_back=(LinearLayout)findViewById(R.id.ll_usermainpage_turnback);
		ll_back.setOnClickListener(onClickListener);
		
		myHandler1=new MyHandler1();
		myHandler2=new MyHandler2();
		myHandler3=new MyHandler3();
		
		new Thread(r1).start();
		new Thread(r2).start();
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_usermainpage_focus:
				if (!ConstantData.isEnter) {
					Toast.makeText(UserMainpageActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
				} else if (ConstantData.UserID == ConstantData.CurrentSelectedUserID) {
					Toast.makeText(UserMainpageActivity.this,
							"不能关注自己哦亲(＃－－)/ .", Toast.LENGTH_SHORT).show();
				} else {
					new Thread(r4).start();
				}
				break;
			case R.id.ll_usermainpage_turnback:
				finish();
				break;
			}
		}
	};
	
	Runnable r1=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			UserInfoConnect userInfo=new UserInfoConnect();
			JSONObject json=userInfo.UserInfo(ConstantData.CurrentSelectedUserID);
			
			Message msg=new Message();
			Bundle b=new Bundle();
			b.putString("json", json.toString());
			msg.setData(b);
			myHandler1.sendMessage(msg);
		}
	};
	
	Runnable r2=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			MyTCConnect myTC=new MyTCConnect();
			TucaoInfoConnect tcInfo=new TucaoInfoConnect();
			JSONObject json=myTC.MyTC(ConstantData.CurrentSelectedUserID);
			JSONObject json1=null;
			try {
				for(int i=0;i<json.getInt("TCNum");i++){
					json1=tcInfo.TucaoInfo(json.getInt("TCID"+i));
					
					Message msg=new Message();
					Bundle b=new Bundle();
					b.putString("json", json1.toString());
					msg.setData(b);
					myHandler2.sendMessage(msg);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	Runnable r3=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ZanTCConnect zanTC=new ZanTCConnect();
			zanTC.zanTC(ID);
		}
	};
	
	Runnable r4=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			FocusUserConnect focusUser=new FocusUserConnect();
			String result=focusUser.FocusShop(ConstantData.UserID, ConstantData.Key,
					ConstantData.CurrentSelectedUserID);
			
			Message msg=new Message();
			Bundle b=new Bundle();
			b.putString("result", result);
			msg.setData(b);
			myHandler3.sendMessage(msg);
			
		}
	};
	
	class MyHandler1 extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Bundle b=msg.getData();
			try {
				JSONObject json=new JSONObject(b.getString("json"));
				tv_focusnum.setText(json.getInt("FocusNum")+"");
				tv_fansnum.setText(json.getInt("FansNum")+"");
				tv_sign.setText("个人简介："+json.getString("Sign"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	class MyHandler2 extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Bundle b=msg.getData();
			
			try {
				final JSONObject json=new JSONObject(b.getString("json"));
				
				TucaoFrame tucaoFrame=new TucaoFrame();
				
				LinearLayout ll = tucaoFrame.CreateTucaoFrame(json,
						UserMainpageActivity.this);

				ll_tucaowords.addView(ll);
				ll.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ConstantData.tucaoJson = json;
						ConstantData.TucaoOrFB = 0;
						try {
							ConstantData.ID = json.getInt("UserID");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(new Intent(UserMainpageActivity.this,
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
							Toast.makeText(UserMainpageActivity.this,
									"请先登录", Toast.LENGTH_SHORT).show();
						else {
							iv.setImageResource(R.drawable.zan2);
							iv.setClickable(false);
							tv.setText((Integer.parseInt(tv.getText()
									.toString()) + 1) + "");
							try {
								ID = json.getInt("TCID");
								new Thread(r3).start();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				
				
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
			String result = b.getString("result");
			if (!result.equals("已关注过该用户")) {

				tv_focusnum.setText((Integer.parseInt(tv_focusnum.getText()
						.toString()) + 1) + "");

				btn_focus.setText("已关注");
			}
			Toast.makeText(UserMainpageActivity.this, b.getString("result"),
					Toast.LENGTH_SHORT).show();
		}
	}
	
}
