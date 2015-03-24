package com.example.dazhongtucao.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShopFBReplyFrame {
	public static LinearLayout CreateShopFBFrame(JSONObject json,
			Context context) {

		int times = 2;
		LinearLayout ll1 = new LinearLayout(context);
		try {

			LinearLayout.LayoutParams ll_p1 = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll_p1.setMargins(0, 0, 0, 5*times);

			ll1.setLayoutParams(ll_p1);
			ll1.setOrientation(LinearLayout.VERTICAL);
			ll1.setBackgroundResource(R.drawable.tucao_frame_normal);

			/*
			 * LinearLayout.LayoutParams ll_p2 = new LayoutParams(
			 * LayoutParams.MATCH_PARENT, 10*times);
			 * 
			 * TextView tv1=new TextView(context); tv1.setLayoutParams(ll_p2);
			 * tv1
			 * .setTextColor(context.getResources().getColor(R.color.lightGrey
			 * )); ll1.addView(tv1);
			 */

			LinearLayout.LayoutParams ll_p2 = new LayoutParams(
					LayoutParams.WRAP_CONTENT, 5 * times);

			TextView tv1 = new TextView(context);
			tv1.setLayoutParams(ll_p2);
			ll1.addView(tv1);

			LinearLayout.LayoutParams ll_p3 = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ll_p3.setMargins(5 * times, 0, 0, 0);
			ll_p3.gravity = Gravity.CENTER_VERTICAL;

			TextView tv2 = new TextView(context);
			tv2.setLayoutParams(ll_p3);
			tv2.setText(json.getString("ShopName"));
			tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			ll1.addView(tv2);
			
			LinearLayout.LayoutParams ll_p4 = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll_p4.setMargins(5 * times, 5 * times, 5 * times, 3 * times);
			
			TextView tv3=new TextView(context);
			tv3.setLayoutParams(ll_p4);
			tv3.setText(json.getString("FB"));
			tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			ll1.addView(tv3);
			
			LinearLayout.LayoutParams ll_p5 = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll_p5.setMargins(0, 0, 0, 5 * times);
			
			RelativeLayout rl1=new RelativeLayout(context);
			rl1.setLayoutParams(ll_p5);
			
			RelativeLayout.LayoutParams rl_p1 = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			rl_p1.setMargins(5*times, 0, 0, 0);
			
			TextView tv4=new TextView(context);
			tv4.setLayoutParams(rl_p1);
			tv4.setText(json.getString("Time"));
			tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv4.setTextColor(context.getResources().getColor(R.color.grey));
			rl1.addView(tv4);
			
			RelativeLayout.LayoutParams rl_p2 = new RelativeLayout.LayoutParams(
					18*times, LayoutParams.WRAP_CONTENT);
			rl_p2.setMargins(0, 0, 5*times, 0);
			rl_p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rl_p2.addRule(RelativeLayout.CENTER_VERTICAL);
			
			TextView tv5=new TextView(context);
			tv5.setLayoutParams(rl_p2);
			tv5.setText(json.getInt("ReNum")+"");
			tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
			//rl1.addView(tv5);
			
			RelativeLayout.LayoutParams rl_p3 = new RelativeLayout.LayoutParams(
					15 * times, 15 * times);
			rl_p3.setMargins(0, 0, 25*times, 0);
			rl_p3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rl_p3.addRule(RelativeLayout.CENTER_VERTICAL);
			
			ImageView iv1=new ImageView(context);
			iv1.setLayoutParams(rl_p3);
			iv1.setImageResource(R.drawable.ic_reply);
			//rl1.addView(iv1);
			
			RelativeLayout.LayoutParams rl_p4 = new RelativeLayout.LayoutParams(
					18*times, LayoutParams.WRAP_CONTENT);
			rl_p4.setMargins(0, 0, 40*times, 0);
			rl_p4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rl_p4.addRule(RelativeLayout.CENTER_VERTICAL);
			
			TextView tv7=new TextView(context);
			tv7.setLayoutParams(rl_p4);
			tv7.setText(json.getInt("ZanNum")+"");
			tv7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
			//rl1.addView(tv7);
			
			RelativeLayout.LayoutParams rl_p5 = new RelativeLayout.LayoutParams(
					15 * times, 15 * times);
			rl_p5.setMargins(0, 0, 57*times, 0);
			rl_p5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rl_p5.addRule(RelativeLayout.CENTER_VERTICAL);
			
			ImageView iv2=new ImageView(context);
			iv2.setLayoutParams(rl_p5);
			iv2.setImageResource(R.drawable.zan1);
			//rl1.addView(iv2);
			
			ll1.addView(rl1);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ll1;

	}
}
