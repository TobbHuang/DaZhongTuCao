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

public class ReFBConnect {
	public JSONObject ReFB(int UserID, String Key, int FBID, String Re) {
		JSONObject json = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.SO_TIMEOUT, 30000); // ��ʱ����
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// ���ӳ�ʱ

			String url = ConstantData.URL + "/ReFB?UserID=" + UserID + "&Key="
					+ Key + "&FBID=" + FBID + "&Re=" + Re;
			HttpGet Get = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(Get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				json = new JSONObject();
				json.put("Result", 1);
				json.put("Reason", "�ظ��ɹ�(^_^)�ϡ�");
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			json = new JSONObject();
			try {
				json.put("Result", 0);
				json.put("Reason", "��������ʧ��(�Уߩ�)");
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
