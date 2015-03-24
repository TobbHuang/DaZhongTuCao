package com.example.dazhongtucao.connect;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.data.ConstantData;

public class LoginConnect {
	public JSONObject Login(String Account, String Password) {
		JSONObject json = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.SO_TIMEOUT, 30000); // ��ʱ����
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// ���ӳ�ʱ

			String url = ConstantData.URL + "/Login?Account=" + Account
					+ "&Password=" + Password;
			HttpGet Get = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(Get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				json = new JSONObject(result);

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			json = new JSONObject();
			try {
				json.put("UserID", -2);//��¼ʧ��UserID����-1������ʧ�ܷ���-2
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
