package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.LoginConnect;
import com.example.dazhongtucao.data.ConstantData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button btn_login_userlogin;
	EditText et_login_account, et_login_password;

	MyHandler myHandler;

	ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("客官请稍等...");
		progressDialog.setMessage("玩命加载数据中...");
		progressDialog.setCancelable(true);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;

		btn_login_userlogin = (Button) findViewById(R.id.btn_login_userlogin);
		btn_login_userlogin.setOnClickListener(clickListener);

		et_login_account = (EditText) findViewById(R.id.et_login_Account);
		et_login_password = (EditText) findViewById(R.id.et_login_Password);

		TextView tv_register = (TextView) findViewById(R.id.tv_login_register);
		tv_register.setOnClickListener(clickListener);
		
		TextView tv_back=(TextView)findViewById(R.id.tv_login_back);
		tv_back.setOnClickListener(clickListener);

		myHandler = new MyHandler();

	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_login_userlogin:
				progressDialog.show();
				new Thread(runnable).start();
				break;
			case R.id.tv_login_register:
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
				break;
			case R.id.tv_login_back:
				finish();
				break;
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			LoginConnect login = new LoginConnect();
			JSONObject json = login.Login(
					et_login_account.getText().toString(), et_login_password
							.getText().toString());
			Message msg = new Message();
			Bundle bundle = new Bundle();

			try {
				bundle.putInt("UserID", json.getInt("UserID"));
				if (json.getInt("UserID") != -2 && json.getInt("UserID") != -1)
					bundle.putString("Key", json.getString("Key"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			msg.setData(bundle);
			myHandler.sendMessage(msg);

		}

	};

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			progressDialog.dismiss();

			Bundle bundle = msg.getData();
			int UserID = bundle.getInt("UserID");
			if (UserID == -2) {
				Toast.makeText(LoginActivity.this, "网络出问题啦(┬＿┬)",
						Toast.LENGTH_SHORT).show();
				;
			} else if (UserID == -1) {
				Toast.makeText(LoginActivity.this, "用户名或密码错误o(︶︿︶)o",
						Toast.LENGTH_SHORT).show();
			} else {
				ConstantData.UserID = UserID;
				ConstantData.Key = bundle.getString("Key");
				ConstantData.isEnter = true;
				Toast.makeText(LoginActivity.this, "登录成功╭(′▽`)╯",
						Toast.LENGTH_SHORT).show();
				Intent login=new Intent();
				setResult(RESULT_OK, login);
				finish();
			}
		}
	}

}
