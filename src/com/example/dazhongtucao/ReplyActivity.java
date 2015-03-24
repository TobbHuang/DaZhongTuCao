package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.ReFBConnect;
import com.example.dazhongtucao.connect.ReplyConnect;
import com.example.dazhongtucao.data.ConstantData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReplyActivity extends Activity {
	
	EditText et_reply;
	
	MyHandler myHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reply);
		
		TextView tv_turnback=(TextView)findViewById(R.id.tv_reply_back);
		tv_turnback.setOnClickListener(onClickListener);
		
		TextView tv_send=(TextView)findViewById(R.id.tv_reply_send);
		tv_send.setOnClickListener(onClickListener);
		
		et_reply=(EditText)findViewById(R.id.et_reply_tucao);
		
		myHandler=new MyHandler();
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.tv_reply_back:
				finish();
				break;
			case R.id.tv_reply_send:
				if(!et_reply.getText().toString().equals("")){
					new Thread(r).start();
					Intent intent=new Intent();
					intent.putExtra("Re", et_reply.getText().toString());
					setResult(RESULT_OK, intent);
				}
				break;
			}
		}
		
	};
	
	Runnable r=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			JSONObject json=null;
			
			if (ConstantData.TucaoOrFB == 0) {
				ReplyConnect reply = new ReplyConnect();
				json = reply.Reply(ConstantData.ID, et_reply
						.getText().toString());
			}
			else{
				ReFBConnect ReFB=new ReFBConnect();
				json = ReFB.ReFB(ConstantData.UserID, ConstantData.Key,
						ConstantData.ID, et_reply.getText().toString());
			}
			
			Message msg=new Message();
			Bundle b=new Bundle();
			b.putString("json", json.toString());
			msg.setData(b);
			myHandler.sendMessage(msg);
		}
	};
	
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Bundle b=msg.getData();
			
			try {
				JSONObject json = new JSONObject(b.getString("json"));
				
				
				
				Toast.makeText(ReplyActivity.this, json.getString("Reason"),
						Toast.LENGTH_SHORT).show();
				
				finish();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
