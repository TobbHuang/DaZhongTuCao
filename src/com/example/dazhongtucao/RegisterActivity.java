package com.example.dazhongtucao;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.RegisterConnect;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText et_NickName, et_Account, et_Password;
	Button btn_register;
	MyHandler myHandler;

	ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("客官请稍等...");
		progressDialog.setMessage("玩命加载数据中...");
		progressDialog.setCancelable(true);

		et_NickName = (EditText) findViewById(R.id.et_register_NickName);
		et_Account = (EditText) findViewById(R.id.et_register_Account);
		et_Password = (EditText) findViewById(R.id.et_register_Password);

		btn_register = (Button) findViewById(R.id.btn_register_register);
		btn_register.setOnClickListener(clickListener);

		myHandler = new MyHandler();

	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_register_register:
				progressDialog.show();
				;
				new Thread(runnable).start();
				break;
			}
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			RegisterConnect register = new RegisterConnect();
			JSONObject json = register.Register(et_NickName.getText()
					.toString(), et_Account.getText().toString(), et_Password
					.getText().toString());
			try {
				String result = json.getString("result");

				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("result", result);
				msg.setData(bundle);

				myHandler.sendMessage(msg);

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

			progressDialog.dismiss();

			Bundle bundle = msg.getData();
			String result = bundle.getString("result");
			if (result.equals("error")) {
				Toast.makeText(RegisterActivity.this, "网络出问题啦(┬＿┬)",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(RegisterActivity.this, result,
						Toast.LENGTH_SHORT).show();
				finish();
			}

		}
	}

}
