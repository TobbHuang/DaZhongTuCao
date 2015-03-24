package com.example.dazhongtucao.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.connect.LogoConnect;

import Decoder.BASE64Decoder;
import android.os.Environment;

public class IsLogoExist {
	public void isLogoExist(JSONObject json) {
		// 创建文件夹
		File f = new File(Environment.getExternalStorageDirectory() + "/DZTC/");
		if (!f.exists()) {
			f.mkdirs();
		}

		// 生成图片
		String imgFilePath = null;
		try {
			imgFilePath = Environment.getExternalStorageDirectory() + "/DZTC/"
					+ json.getString("Logo") + ".jpg";
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(imgFilePath);
		if (!file.exists()) {
			try {
				LogoConnect logoConnect = new LogoConnect();

				// 对字节数组字符串进行Base64解码并生成图片
				BASE64Decoder decoder = new BASE64Decoder();
				// Base64解码
				byte[] b = decoder.decodeBuffer(logoConnect.Logo(json
						.getString("Logo")));

				OutputStream out = new FileOutputStream(file);
				out.write(b);
				out.close();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
