package com.example.dazhongtucao.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class GetImageBitmap {
	public Bitmap GenerateImage(String name) {
		return ShowImage(name);
	}

	public Bitmap ShowImage(String name) {

		// …˙≥…Õº∆¨
		String imgFilePath = Environment.getExternalStorageDirectory()
				+ "/DZTC/" + name + ".jpg";

		// œ‘ æÕº∆¨
		Bitmap bm = BitmapFactory.decodeFile(imgFilePath);

		 RoundBitmap roundBitmap = new RoundBitmap();

		return roundBitmap.toRoundBitmap(bm);
	}
}
