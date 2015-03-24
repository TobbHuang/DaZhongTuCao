package com.example.dazhongtucao.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dazhongtucao.R;
import com.example.dazhongtucao.data.ConstantData;
import com.example.dazhongtucao.tools.GenerateImage;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ReplyFrame {
	
	ImageView iv;
	TextView tv1;
	
	public LinearLayout CreateReplyFrame(JSONObject json, Context context) {

		int times = 2;

		String name = null,  content = null, time = null;

		try {
			if (ConstantData.TucaoOrFB == 0) {
				name = json.getString("NickName");
				content = json.getString("Re");
				time = json.getString("Time");
			} else {
				name = json.getString("ReFBUser");
				content = json.getString("ReFB");
				time = json.getString("ReFBTime");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LinearLayout.LayoutParams ll_p1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		LinearLayout ll1=new LinearLayout(context);
		ll1.setLayoutParams(ll_p1);
		ll1.setOrientation(LinearLayout.VERTICAL);
		ll1.setBackgroundColor(context.getResources().getColor(R.color.white));
		
		LinearLayout.LayoutParams ll_p2 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p2.setMargins(5*times, 5*times, 5*times, 5*times);
		
		LinearLayout ll2=new LinearLayout(context);
		ll2.setLayoutParams(ll_p2);
		ll2.setOrientation(LinearLayout.HORIZONTAL);
		
		LinearLayout.LayoutParams ll_p3 = new LayoutParams(30 * times,
				30 * times);
		
		iv=new ImageView(context);
		iv.setLayoutParams(ll_p3);
		GenerateImage gi=new GenerateImage();
		try {
			gi.GenerateImage(iv, json.getString("Logo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ll2.addView(iv);
		
		LinearLayout.LayoutParams ll_p4 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		LinearLayout ll3=new LinearLayout(context);
		ll3.setLayoutParams(ll_p4);
		ll3.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams ll_p5 = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p5.setMargins(5*times, 2*times, 0, 0);
		
		tv1=new TextView(context);
		tv1.setLayoutParams(ll_p5);
		tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		tv1.setText(name);
		ll3.addView(tv1);
		
		TextView tv2=new TextView(context);
		tv2.setLayoutParams(ll_p5);
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
		tv2.setText(time);
		tv2.setTextColor(context.getResources().getColor(R.color.darkGrey));
		ll3.addView(tv2);
		
		ll2.addView(ll3);
		
		ll1.addView(ll2);
		
		LinearLayout.LayoutParams ll_p6 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_p6.setMargins(40*times, 2*times, 10*times, 5*times);
		
		TextView tv3=new TextView(context);
		tv3.setLayoutParams(ll_p6);
		tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		tv3.setText(content);
		ll1.addView(tv3);
		
		LinearLayout.LayoutParams ll_p7 = new LayoutParams(
				LayoutParams.MATCH_PARENT, 1*times);
		
		TextView tv4 = new TextView(context);
		tv4.setLayoutParams(ll_p7);
		tv4.setBackgroundColor(context.getResources().getColor(R.color.grey));
		ll1.addView(tv4);

		return ll1;
	}
	
	public ImageView getLogo(){
		return iv;
	}
	
	public TextView getName(){
		return tv1;
	}
	
}
