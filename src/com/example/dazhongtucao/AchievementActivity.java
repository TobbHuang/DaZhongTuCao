package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.dazhongtucao.connect.AchievementConnect;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.view.AchievementFrame1;
import com.example.dazhongtucao.view.AchievementFrame2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AchievementActivity extends Activity {
	
	MyHandler handler;
	
	LinearLayout ll_achievementframe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_achievement);
		
		ll_achievementframe=(LinearLayout)findViewById(R.id.ll_achievement_achievementframe);
		
		LinearLayout ll_back=(LinearLayout)findViewById(R.id.ll_achievement_turnback);
		ll_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		handler=new MyHandler();
		
		new Thread(r).start();
		
	}
	
	Runnable r=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			AchievementConnect Achievement=new AchievementConnect();
			JSONObject json=Achievement.Achievement(ConstantData.UserID);
			
			Message msg=new Message();
			Bundle b=new Bundle();
			b.putString("json", json.toString());
			msg.setData(b);
			handler.sendMessage(msg);
			
		}
		
	};
	
	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Bundle b=msg.getData();
			try {
				JSONObject json=new JSONObject(b.getString("json"));
				
				if (json.getInt("Result") == 0) {
					Toast.makeText(AchievementActivity.this,
							json.getString("Reason"), Toast.LENGTH_SHORT)
							.show();
				} else {
					for(int i=0;i<json.getInt("AchievedNum");i++){
						String name=json.getString("AName"+i);
						int imageID = 0;
						if(name.equals("吐槽5个商家")){
							imageID=R.drawable.ic_achievement_tucao1;
						}
						else if(name.equals("吐槽10条")){
							imageID=R.drawable.ic_achievement_tucao1;
						}
						else if(name.equals("扔炸弹10个")){
							imageID=R.drawable.ic_achievement_bomb1;
						}
						else if(name.equals("吐槽被评论5次")){
							imageID=R.drawable.ic_achievement_bomb1;
						}
						ll_achievementframe.addView(AchievementFrame1
								.createAchievementFrame1(
										AchievementActivity.this, imageID,
										name, json.getString("A" + i),
										json.getString("AReward" + i),
										json.getString("ATime" + i)));

					}
					for(int i=0;i<json.getInt("UnAchievedNum");i++){
						String name=json.getString("UAName"+i);
						int imageID = 0;
						if(name.equals("吐槽5个商家")){
							imageID=R.drawable.ic_achievement_tucao2;
						}
						else if(name.equals("吐槽10条")){
							imageID=R.drawable.ic_achievement_tucao2;
						}
						else if(name.equals("扔炸弹10个")){
							imageID=R.drawable.ic_achievement_bomb2;
						}
						ll_achievementframe.addView(AchievementFrame2
								.createAchievementFrame2(
										AchievementActivity.this, imageID,
										name, json.getString("UA" + i),
										json.getString("UAReward" + i)));

					}
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	
}
