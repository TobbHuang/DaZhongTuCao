package com.example.dazhongtucao.view;

import com.example.dazhongtucao.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class OverlayShape {
	static public LinearLayout CreateOverlay(Context context,String type, String ShopName,
			int figure) {
		
		int times=3;
		
		LinearLayout.LayoutParams ll_p1 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		LinearLayout ll1 = new LinearLayout(context);
		ll1.setLayoutParams(ll_p1);
		ll1.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout ll2 = new LinearLayout(context);
		ll2.setLayoutParams(ll_p1);
		ll2.setOrientation(LinearLayout.HORIZONTAL);
		ll2.setBackgroundResource(R.drawable.shape_overlay);
		
		LinearLayout.LayoutParams ll_p2 = new LinearLayout.LayoutParams(
				10*times, 10*times);
		ll_p2.setMargins(2*times, 2*times, 2*times, 2*times);
		ll_p2.gravity=Gravity.CENTER_VERTICAL;
		
		ImageView iv1=new ImageView(context);
		iv1.setLayoutParams(ll_p2);
		iv1.setImageResource(R.drawable.restaurant);
		ll2.addView(iv1);
		
		LinearLayout.LayoutParams ll_p3 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll_p3.setMargins(0, 2*times, 2*times, 2*times);
		ll_p3.gravity=Gravity.CENTER_VERTICAL;
		
		TextView tv1=new TextView(context);
		tv1.setLayoutParams(ll_p3);
		tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		tv1.setText(ShopName);
		ll2.addView(tv1);
		
		ll1.addView(ll2);
		
		LinearLayout.LayoutParams ll_p4 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 10*times);
		
		LinearLayout ll3=new LinearLayout(context);
		ll3.setLayoutParams(ll_p4);
		ll3.setOrientation(LinearLayout.HORIZONTAL);
		ll3.setBackgroundResource(R.drawable.shape_overlay);
		
		LinearLayout.LayoutParams ll_p5 = new LinearLayout.LayoutParams(
				4*times, 8*times);
		ll_p5.setMargins(1*times, 2*times, 0, 2*times);
		ll_p5.gravity=Gravity.CENTER_VERTICAL;
		
		ImageView iv2;
		for(int i=0;i<(figure/10);i++){
			iv2=new ImageView(context);
			iv2.setLayoutParams(ll_p5);
			iv2.setImageResource(R.drawable.testbar);
			iv2.setScaleType(ScaleType.FIT_XY);
			ll3.addView(iv2);
		}
		
		ll1.addView(ll3);
		
		return ll1;

	}
}
