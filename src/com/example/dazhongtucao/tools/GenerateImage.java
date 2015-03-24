package com.example.dazhongtucao.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

public class GenerateImage {
	public boolean GenerateImage( ImageView temp, String name) {

		try {
			
			ShowImage(temp, name);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean ShowImage(ImageView temp, String name) {

		try {

			// …˙≥…Õº∆¨
			String imgFilePath = Environment.getExternalStorageDirectory()+"/DZTC/" + name + ".jpg";

			// œ‘ æÕº∆¨
			Bitmap bm = BitmapFactory.decodeFile(imgFilePath);
			
			RoundBitmap roundBitmap=new RoundBitmap();

			temp.setImageBitmap(roundBitmap.toRoundBitmap(bm));

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
