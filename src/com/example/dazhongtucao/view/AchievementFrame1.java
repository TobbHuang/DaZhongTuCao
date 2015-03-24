package com.example.dazhongtucao.view;

import com.example.dazhongtucao.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class AchievementFrame1 {
	public static LinearLayout createAchievementFrame1(Context context,
			int ImageID, String AName, String AContent, String AReward,
			String ATime) {
		
		int times=2;
		
		LinearLayout.LayoutParams ll_p1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_p1.setMargins(0, 0, 0, 10*times);
		
		LinearLayout ll1=new LinearLayout(context);
		ll1.setLayoutParams(ll_p1);
		ll1.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams ll_p2 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 1*times);
		
		TextView tv1=new TextView(context);
		tv1.setLayoutParams(ll_p2);
		tv1.setBackgroundColor(context.getResources().getColor(R.color.grey));
		ll1.addView(tv1);
		
		LinearLayout.LayoutParams ll_p3 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 120*times);
		
		LinearLayout ll2=new LinearLayout(context);
		ll2.setLayoutParams(ll_p3);
		ll2.setOrientation(LinearLayout.HORIZONTAL);
		ll2.setBackgroundResource(R.drawable.bg_achievement1);
		
		LinearLayout.LayoutParams ll_p4 = new LinearLayout.LayoutParams(
				70*times, 70*times);
		ll_p4.setMargins(10*times, 0, 0, 0);
		ll_p4.gravity=Gravity.CENTER_VERTICAL;
		
		ImageView iv1=new ImageView(context);
		iv1.setLayoutParams(ll_p4);
		iv1.setImageResource(ImageID);
		ll2.addView(iv1);
		
		LinearLayout.LayoutParams ll_p5 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll_p5.setMargins(15*times, 30*times, 0, 0);
		
		LinearLayout ll3=new LinearLayout(context);
		ll3.setLayoutParams(ll_p5);
		ll3.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams ll_p6 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView tv2=new TextView(context);
		tv2.setLayoutParams(ll_p6);
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tv2.setText(AName);
		ll3.addView(tv2);
		
		LinearLayout.LayoutParams ll_p7 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p7.setMargins(0, 5*times, 0, 0);
		
		TextView tv3=new TextView(context);
		tv3.setLayoutParams(ll_p7);
		tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv3.setTextColor(context.getResources().getColor(R.color.darkGrey));
		tv3.setText(AContent);
		ll3.addView(tv3);
		
		LinearLayout ll4=new LinearLayout(context);
		ll4.setLayoutParams(ll_p7);
		ll4.setOrientation(LinearLayout.HORIZONTAL);
		
		LinearLayout.LayoutParams ll_p8 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView tv4=new TextView(context);
		tv4.setLayoutParams(ll_p8);
		tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv4.setTextColor(context.getResources().getColor(R.color.darkGrey));
		tv4.setText("奖励：");
		ll4.addView(tv4);
		
		TextView tv5=new TextView(context);
		tv5.setLayoutParams(ll_p8);
		tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv5.setTextColor(context.getResources().getColor(R.color.theme));
		tv5.setText(AReward);
		ll4.addView(tv5);
		
		LinearLayout.LayoutParams ll_p9 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p9.setMargins(10*times, 0, 0, 0);
		
		TextView tv6=new TextView(context);
		tv6.setLayoutParams(ll_p9);
		tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv6.setTextColor(context.getResources().getColor(R.color.darkGrey));
		tv6.setText("达成时间："+ATime);
		ll4.addView(tv6);
		
		ll3.addView(ll4);
		
		ll2.addView(ll3);
		
		ll1.addView(ll2);
		
		TextView tv7=new TextView(context);
		tv7.setLayoutParams(ll_p2);
		tv7.setBackgroundColor(context.getResources().getColor(R.color.grey));
		ll1.addView(tv7);
		
		return ll1;

	}
}
