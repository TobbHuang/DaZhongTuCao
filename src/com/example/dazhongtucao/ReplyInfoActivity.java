package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.GetFBReConnect;
import com.example.dazhongtucao.connect.GetTCReConnect;
import com.example.dazhongtucao.connect.ReFBInfoConnect;
import com.example.dazhongtucao.connect.ReInfoConnect;
import com.example.dazhongtucao.connect.ZanFBConnect;
import com.example.dazhongtucao.connect.ZanTCConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.IsLogoExist;
import com.example.dazhongtucao.view.ReplyFrame;
import com.example.dazhongtucao.view.ShopFBReplyFrame;
import com.example.dazhongtucao.view.TucaoReplyFrame;

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

public class ReplyInfoActivity extends Activity {
	
	LinearLayout ll_tucaoframe,ll_replyframe,ll_newreplyframe,ll_turnback;
	TextView tv_renum,tv_zannum;
	Button btn_zan;
	
	IsLogoExist isLogoExist=new IsLogoExist();
	
	MyHandler myHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reply_info);
		
		ll_tucaoframe=(LinearLayout)findViewById(R.id.ll_replyinfo_tucaoframe);
		ll_replyframe=(LinearLayout)findViewById(R.id.ll_replyinfo_replyframe);
		ll_newreplyframe=(LinearLayout)findViewById(R.id.ll_replyinfo_newreplyframe);
		ll_turnback=(LinearLayout)findViewById(R.id.ll_replyinfo_turnback);
		ll_turnback.setOnClickListener(onClickListener);
		tv_renum=(TextView)findViewById(R.id.tv_replyinfo_renum);
		tv_zannum=(TextView)findViewById(R.id.tv_replyinfo_zannum);
		Button btn_reply=(Button)findViewById(R.id.btn_replyinfo_reply);
		btn_reply.setOnClickListener(onClickListener);
		btn_zan=(Button)findViewById(R.id.btn_replyinfo_zan);
		btn_zan.setOnClickListener(onClickListener);
		
		if (ConstantData.TucaoOrFB == 0) {
			ll_tucaoframe.addView(TucaoReplyFrame.CreateTucaoReplyFrame(
					ConstantData.tucaoJson, ReplyInfoActivity.this));
		} else {
			ll_tucaoframe.addView(ShopFBReplyFrame.CreateShopFBFrame(
					ConstantData.tucaoJson, ReplyInfoActivity.this));
		}
		
		try {
			tv_renum.setText(ConstantData.tucaoJson.getInt("ReNum")+"");
			tv_zannum.setText(ConstantData.tucaoJson.getInt("ZanNum")+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myHandler=new MyHandler();
		
		if (ConstantData.TucaoOrFB == 0)
			new Thread(r1).start();
		else
			new Thread(r2).start();
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.ll_replyinfo_turnback:
				finish();
				break;
			case R.id.btn_replyinfo_reply:
				if (ConstantData.isEnter) {
					Intent intent = new Intent(ReplyInfoActivity.this,
							ReplyActivity.class);
					startActivityForResult(intent, 100);
				}
				else{
					Toast.makeText(ReplyInfoActivity.this, "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_replyinfo_zan:
				if (ConstantData.isEnter) {
					btn_zan.setText("ÒÑÔÞ¹ý");
					btn_zan.setClickable(false);
					tv_zannum.setText((Integer.parseInt(tv_zannum.getText().toString())+1)+"");
					new Thread(r3).start();
				}
				else{
					Toast.makeText(ReplyInfoActivity.this, "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			
			String Re=data.getExtras().getString("Re");
			
			JSONObject json=new JSONObject();
			try {
				json.put("Re", Re);
				json.put("Time", "¸Õ¸Õ");
				json.put("NickName", ConstantData.UserName);
				json.put("Logo", ConstantData.UserLogo1);
				
				ReplyFrame replyFrame=new ReplyFrame();
				
				LinearLayout ll = replyFrame.CreateReplyFrame(json,
						ReplyInfoActivity.this);
				
				ll_newreplyframe.addView(ll);
				
				tv_renum.setText((Integer.parseInt(tv_renum.getText()
						.toString()) + 1) + "");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	Runnable r1=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			GetTCReConnect getTCRe=new GetTCReConnect();
			ReInfoConnect reInfo=new ReInfoConnect();
			JSONObject json=getTCRe.GetTCRe(ConstantData.ID);
			JSONObject json1;
			
			try {
				for(int i=0;i<json.getInt("ReNum");i++){
					json1=reInfo.ReInfo(json.getInt("ReID"+i));
					
					isLogoExist.isLogoExist(json1);
					
					Message msg=new Message();
					Bundle b=new Bundle();
					b.putString("json", json1.toString());
					msg.setData(b);
					myHandler.sendMessage(msg);
					
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
			GetFBReConnect getTCRe = new GetFBReConnect();
			ReFBInfoConnect reFBInfo = new ReFBInfoConnect();
			JSONObject json = getTCRe.GetFBRe(ConstantData.ID);
			JSONObject json1;
			try {
				for (int i = 0; i < json.getInt("ReFBNum"); i++) {
					json1 = reFBInfo.ReFBInfo(json.getInt("ReFBID" + i));
					
					isLogoExist.isLogoExist(json1);

					Message msg = new Message();
					Bundle b = new Bundle();
					b.putString("json", json1.toString());
					msg.setData(b);
					myHandler.sendMessage(msg);

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
			if(ConstantData.TucaoOrFB==0){
				ZanTCConnect zanTC = new ZanTCConnect();
				zanTC.zanTC(ConstantData.ID);
			}else{
				ZanFBConnect zanFB = new ZanFBConnect();
				zanFB.zanFB(ConstantData.ID);
			}
		}
	};
	
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Bundle b=msg.getData();
			try {
				final JSONObject json=new JSONObject(b.getString("json"));
				
				ReplyFrame replyFrame=new ReplyFrame();
				
				LinearLayout ll =replyFrame.CreateReplyFrame(json,
						ReplyInfoActivity.this);
				
				ImageView iv=replyFrame.getLogo();
				TextView tv=replyFrame.getName();
				
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							ConstantData.CurrentSelectedUserID = json
									.getInt("UserID");
							ConstantData.CurrentSelectedUsername = json
									.getString("NickName");
							ConstantData.UserLogo1 = json.getString("Logo");
							startActivity(new Intent(ReplyInfoActivity.this,
									UserMainpageActivity.class));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							ConstantData.CurrentSelectedUserID = json
									.getInt("UserID");
							ConstantData.CurrentSelectedUsername = json
									.getString("NickName");
							ConstantData.UserLogo1 = json.getString("Logo");
							startActivity(new Intent(ReplyInfoActivity.this,
									UserMainpageActivity.class));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				ll_replyframe.addView(ll);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
