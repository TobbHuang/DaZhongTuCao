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

public class ShopListConnect {
	public JSONObject ShopList(String Type) {
		JSONObject json = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.SO_TIMEOUT, 30000); // ��ʱ����
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// ���ӳ�ʱ

			String url = ConstantData.URL + "/SearchShopByType?Type=" + Type
					+ "&Lo=" + ConstantData.Longitude + "&La="
					+ ConstantData.Latitude;
			HttpGet Get = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(Get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				json = new JSONObject(result);
				json.put("Result", true);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			json = new JSONObject();
			try {
				json.put("Result", false);
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
