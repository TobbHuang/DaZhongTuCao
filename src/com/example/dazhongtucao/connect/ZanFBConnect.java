package com.example.dazhongtucao.connect;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.example.dazhongtucao.data.ConstantData;

public class ZanFBConnect {
	public String zanFB(int FBID) {
		String result=null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.SO_TIMEOUT, 30000); // 超时设置
			httpClient.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// 连接超时

			String url = ConstantData.URL + "/ZanFB?UserID="
					+ ConstantData.UserID + "&Key=" + ConstantData.Key
					+ "&FBID=" + FBID;
			HttpGet Get = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(Get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
