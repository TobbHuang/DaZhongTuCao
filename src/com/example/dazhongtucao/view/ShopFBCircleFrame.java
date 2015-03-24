package com.example.dazhongtucao.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.dazhongtucao.R;
import com.example.dazhongtucao.tools.GenerateImage;

public class ShopFBCircleFrame {
	
	ImageView iv6;
	TextView tv7;
	ImageView iv1;
	TextView tv2;
	
	public LinearLayout CreateShopFBCircleFrame(JSONObject json,
			Context context, int index, int width) {

		int times = 2;

		String name = null, content = null, time = null;
		int num_zan = 0;
		int num_reply = 0;

		try {
			name = json.getString("ShopName");
			content = json.getString("FB");
			time = json.getString("Time");
			num_reply = json.getInt("ReNum");
			num_zan = json.getInt("ZanNum");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LinearLayout.LayoutParams ll_p1 = new LinearLayout.LayoutParams(
				(int) (width * 0.75), LayoutParams.WRAP_CONTENT);
		ll_p1.setMargins(0, 5 * times, 5 * times, 0);
		ll_p1.gravity = Gravity.RIGHT;
		LinearLayout ll1 = new LinearLayout(context);
		ll1.setLayoutParams(ll_p1);
		ll1.setOrientation(LinearLayout.VERTICAL);
		switch (index % 4) {
		case 0:
			ll1.setBackgroundResource(R.drawable.shape_tucaocircle1);
			break;
		case 1:
			ll1.setBackgroundResource(R.drawable.shape_tucaocircle2);
			break;
		case 2:
			ll1.setBackgroundResource(R.drawable.shape_tucaocircle3);
			break;
		case 3:
			ll1.setBackgroundResource(R.drawable.shape_tucaocircle4);
			break;
		}

		LinearLayout.LayoutParams ll_p2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 5 * times);
		TextView tv1 = new TextView(context);
		tv1.setLayoutParams(ll_p2);
		ll1.addView(tv1);

		LinearLayout.LayoutParams ll_p3 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p3.setMargins(10 * times, 10 * times, 10 * times, 0);
		RelativeLayout rl1 = new RelativeLayout(context);
		rl1.setLayoutParams(ll_p3);

		RelativeLayout.LayoutParams rl_p1 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout ll2 = new LinearLayout(context);
		ll2.setLayoutParams(rl_p1);
		ll2.setOrientation(LinearLayout.HORIZONTAL);
		
		GenerateImage generageImage=new GenerateImage();

		LinearLayout.LayoutParams ll_p4 = new LinearLayout.LayoutParams(
				25 * times, 25 * times);
		iv1 = new ImageView(context);
		iv1.setLayoutParams(ll_p4);
		try {
			generageImage.GenerateImage( iv1, json.getString("Logo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ll2.addView(iv1);

		LinearLayout.LayoutParams ll_p61 = new LinearLayout.LayoutParams(
				15 * times, 15 * times);
		ll_p61.gravity = Gravity.CENTER_VERTICAL;
		ll_p61.setMargins(2 * times, 0, 2 * times, 0);

		ImageView iv3 = new ImageView(context);
		iv3.setLayoutParams(ll_p61);
		iv3.setImageResource(R.drawable.restaurant);
		ll2.addView(iv3);

		LinearLayout.LayoutParams ll_p5 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p5.gravity = Gravity.CENTER_VERTICAL;
		tv2 = new TextView(context);
		tv2.setLayoutParams(ll_p5);
		tv2.setText(name);
		tv2.setTextColor(context.getResources().getColor(R.color.orange));
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		ll2.addView(tv2);

		TextView tv3 = new TextView(context);
		tv3.setLayoutParams(ll_p5);
		tv3.setText("∑¢≤º¡À");
		tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		ll2.addView(tv3);

		rl1.addView(ll2);

		ll1.addView(rl1);

		LinearLayout.LayoutParams ll_p7 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll_p7.setMargins(38 * times, 5 * times, 60 * times, 3 * times);
		TextView tv4 = new TextView(context);
		tv4.setLayoutParams(ll_p7);
		tv4.setText(content);
		tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		ll1.addView(tv4);

		LinearLayout.LayoutParams ll_p8 = new LinearLayout.LayoutParams(
				60 * times, 60 * times);
		ll_p8.setMargins(38 * times, 0, 0, 3 * times);
		ImageView iv4 = new ImageView(context);
		iv4.setLayoutParams(ll_p8);
		iv4.setImageResource(R.drawable.icon);
		// ll1.addView(iv4);

		LinearLayout.LayoutParams ll_p9 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll_p9.setMargins(0, 0, 0, 9 * times);
		RelativeLayout rl2 = new RelativeLayout(context);
		rl2.setLayoutParams(ll_p9);

		RelativeLayout.LayoutParams rl_p3 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl_p3.setMargins(10 * times, 0, 0, 0);
		TextView tv5 = new TextView(context);
		tv5.setLayoutParams(rl_p3);
		tv5.setText(time);
		tv5.setTextColor(context.getResources().getColor(R.color.darkGrey));
		tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		rl2.addView(tv5);

		RelativeLayout.LayoutParams rl_p4 = new RelativeLayout.LayoutParams(
				18 * times, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl_p4.setMargins(0, 0, 5 * times, 0);
		rl_p4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_p4.addRule(RelativeLayout.CENTER_VERTICAL);
		TextView tv6 = new TextView(context);
		tv6.setLayoutParams(rl_p4);
		tv6.setText(num_reply + "");
		tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
		rl2.addView(tv6);

		RelativeLayout.LayoutParams rl_p5 = new RelativeLayout.LayoutParams(
				20 * times, 20 * times);
		rl_p5.setMargins(0, 0, 30 * times, 0);
		rl_p5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_p5.addRule(RelativeLayout.CENTER_VERTICAL);
		ImageView iv5 = new ImageView(context);
		iv5.setLayoutParams(rl_p5);
		iv5.setImageResource(R.drawable.ic_reply);
		rl2.addView(iv5);

		RelativeLayout.LayoutParams rl_p6 = new RelativeLayout.LayoutParams(
				12 * times, RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl_p6.setMargins(0, 0, 48 * times, 0);
		rl_p6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_p6.addRule(RelativeLayout.CENTER_VERTICAL);
		tv7 = new TextView(context);
		tv7.setLayoutParams(rl_p6);
		tv7.setText(num_zan + "");
		tv7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
		rl2.addView(tv7);

		RelativeLayout.LayoutParams rl_p7 = new RelativeLayout.LayoutParams(
				20 * times, 20 * times);
		rl_p7.setMargins(0, 0, 65 * times, 0);
		rl_p7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_p7.addRule(RelativeLayout.CENTER_VERTICAL);
		iv6 = new ImageView(context);
		iv6.setLayoutParams(rl_p7);
		iv6.setImageResource(R.drawable.zan1);
		rl2.addView(iv6);

		ll1.addView(rl2);

		/*
		 * LinearLayout.LayoutParams ll_p10 = new LinearLayout.LayoutParams(
		 * LinearLayout.LayoutParams.WRAP_CONTENT, 10*times); TextView tv8=new
		 * TextView(context); tv8.setLayoutParams(ll_p10);
		 * 
		 * ll1.addView(tv8);
		 */

		return ll1;
	}
	
	public ImageView getZanImage(){
		return iv6;
	}
	
	public TextView getZanTv(){
		return tv7;
	}
	
	public ImageView getLogo(){
		return iv1;
	}
	
	public TextView getName(){
		return tv2;
	}
	
}
