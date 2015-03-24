package com.example.dazhongtucao.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.R;
import com.example.dazhongtucao.tools.GenerateImage;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TucaoFrame {
	
	ImageView iv1,iv6;
	TextView tv2,tv7;
	
	public LinearLayout CreateTucaoFrame(JSONObject json, Context context) {

		int times = 2;

		String name = null, shopname = null, content = null, time = null;
		int num_zan = 0;
		int num_reply = 0;
		int angry_figure = 0;

		try {
			name = json.getString("NickName");
			angry_figure = json.getInt("Anger");
			shopname = json.getString("ShopName");
			content = json.getString("TC");
			time = json.getString("Time");
			num_reply = json.getInt("ReNum");
			num_zan = json.getInt("ZanNum");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LinearLayout.LayoutParams ll_p1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_p1.setMargins(0, 0, 0, 5 * times);
		LinearLayout ll1 = new LinearLayout(context);
		ll1.setLayoutParams(ll_p1);
		ll1.setOrientation(LinearLayout.VERTICAL);
		ll1.setBackgroundResource(R.drawable.drawable_selector_frame);

		LinearLayout.LayoutParams ll_p2 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 5 * times);
		TextView tv1 = new TextView(context);
		tv1.setLayoutParams(ll_p2);
		ll1.addView(tv1);

		LinearLayout.LayoutParams ll_p3 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout rl1 = new RelativeLayout(context);
		rl1.setLayoutParams(ll_p3);

		RelativeLayout.LayoutParams rl_p1 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout ll2 = new LinearLayout(context);
		ll2.setLayoutParams(rl_p1);
		ll2.setOrientation(LinearLayout.HORIZONTAL);
		
		GenerateImage generageImage=new GenerateImage();

		LinearLayout.LayoutParams ll_p4 = new LinearLayout.LayoutParams(
				30 * times, 30 * times);
		ll_p4.setMargins(5 * times, 0, 0, 0);
		iv1 = new ImageView(context);
		iv1.setLayoutParams(ll_p4);
		try {
			generageImage.GenerateImage(iv1, json.getString("Logo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ll2.addView(iv1);

		LinearLayout.LayoutParams ll_p5 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p5.gravity = Gravity.CENTER_VERTICAL;
		tv2 = new TextView(context);
		tv2.setLayoutParams(ll_p5);
		tv2.setText(" " + name);
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		ll2.addView(tv2);

		LinearLayout.LayoutParams ll_p6 = new LinearLayout.LayoutParams(
				15 * times, 15 * times);
		ll_p6.gravity = Gravity.CENTER_VERTICAL;
		for (int i = 0; i < angry_figure; i++) {
			ImageView iv2 = new ImageView(context);
			iv2.setLayoutParams(ll_p6);
			iv2.setImageResource(R.drawable.angry);
			//ll2.addView(iv2);
		}

		rl1.addView(ll2);

		RelativeLayout.LayoutParams rl_p2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rl_p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_p2.addRule(RelativeLayout.CENTER_VERTICAL);
		LinearLayout ll3 = new LinearLayout(context);
		ll3.setLayoutParams(rl_p2);
		ll3.setOrientation(LinearLayout.HORIZONTAL);

		ImageView iv3 = new ImageView(context);
		iv3.setLayoutParams(ll_p6);
		iv3.setImageResource(R.drawable.restaurant);
		ll3.addView(iv3);

		TextView tv3 = new TextView(context);
		ll_p5.setMargins(5, 0, 5, 0);
		tv3.setLayoutParams(ll_p5);
		tv3.setText(shopname);
		tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		ll3.addView(tv3);

		rl1.addView(ll3);

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
		//ll1.addView(iv4);

		LinearLayout.LayoutParams ll_p9 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		ll_p9.setMargins(0, 0, 0, 9 * times);
		RelativeLayout rl2 = new RelativeLayout(context);
		rl2.setLayoutParams(ll_p9);

		RelativeLayout.LayoutParams rl_p3 = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl_p3.setMargins(38 * times, 0, 0, 0);
		TextView tv5 = new TextView(context);
		tv5.setLayoutParams(rl_p3);
		tv5.setText(time);
		tv5.setTextColor(context.getResources().getColor(R.color.grey));
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
		rl_p5.setMargins(0, 0, 28 * times, 0);
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
