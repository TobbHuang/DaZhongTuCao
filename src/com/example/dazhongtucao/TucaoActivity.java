package com.example.dazhongtucao;

import com.example.dazhongtucao.connect.TucaoConnect;
import com.example.dazhongtucao.data.ConstantData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TucaoActivity extends Activity {
	
	TextView tv_back,tv_send,tv_shopname;
	EditText et_tucao;
	MyHandler myHandler;
	ImageView iv_weibo,iv_qq;
	
	int isWeibo=0,isQQ=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tucao);
		
		tv_back=(TextView)findViewById(R.id.tv_tucao_back);
		tv_back.setOnClickListener(onClickListener);
		
		tv_send=(TextView)findViewById(R.id.tv_tucao_send);
		tv_send.setOnClickListener(onClickListener);
		
		et_tucao=(EditText)findViewById(R.id.et_tucao_tucao);
		
		tv_shopname=(TextView)findViewById(R.id.tv_tucao_shopname);
		tv_shopname.setText(ConstantData.CurrentSelectedShopname);
		
		iv_weibo=(ImageView)findViewById(R.id.iv_tucao_weibo);
		iv_weibo.setOnClickListener(onClickListener);
		iv_qq=(ImageView)findViewById(R.id.iv_tucao_qq);
		iv_qq.setOnClickListener(onClickListener);
		
		myHandler=new MyHandler();
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.tv_tucao_back:
				finish();
				break;
			case R.id.tv_tucao_send:
				if(et_tucao.getText().toString().equals("")){
					new AlertDialog.Builder(TucaoActivity.this)
					.setMessage("请输入你的吐槽")
					.setPositiveButton("确定", null).show();
				}else{
					if(!ConstantData.isEnter){
						new AlertDialog.Builder(TucaoActivity.this)
						.setMessage("请先登录")
						.setPositiveButton("确定", null).show();
					}
					else{
						new Thread(r).start();
					}
				}
				break;
			case R.id.iv_tucao_weibo:
				if (isWeibo == 0) {
					iv_weibo.setImageResource(R.drawable.ic_weibo1);
					isWeibo = 1;
				} else {
					iv_weibo.setImageResource(R.drawable.ic_weibo2);
					isWeibo = 0;
				}
				break;
			case R.id.iv_tucao_qq:
				if (isQQ == 0) {
					iv_qq.setImageResource(R.drawable.ic_qq1);
					isQQ = 1;
				} else {
					iv_qq.setImageResource(R.drawable.ic_qq2);
					isQQ = 0;
				}
				break;
			}
		}
	};
	
	Runnable r=new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			TucaoConnect tucao=new TucaoConnect();
			String result=tucao.Tucao(ConstantData.UserID, ConstantData.Key,
					ConstantData.CurrentSelectedShopID, et_tucao.getText()
							.toString(), 3);

			Message msg=new Message();
			Bundle b=new Bundle();
			b.putString("result", result);
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
			Toast.makeText(TucaoActivity.this, b.getString("result"), Toast.LENGTH_SHORT).show();
			
			Intent intent=new Intent();
			intent.putExtra("anger", 3+"");
			intent.putExtra("content", et_tucao.getText().toString());
			
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
}
