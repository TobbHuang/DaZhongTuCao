package com.example.dazhongtucao;

import com.example.dazhongtucao.data.ConstantData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TypeActivity extends Activity {
	
	RelativeLayout rl_restaurant, rl_hotel, rl_shopping, rl_attraction, rl_ktv,
			rl_film, rl_sports, rl_trip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_type);
		
		RelativeLayout rl_search=(RelativeLayout)findViewById(R.id.rl_type_search);
		rl_search.setOnClickListener(clickListener);
		
		LinearLayout ll=(LinearLayout)findViewById(R.id.lly_type_turnback);
		ll.setOnClickListener(clickListener);
		
		rl_restaurant=(RelativeLayout)findViewById(R.id.rl_type_restaurant);
		rl_restaurant.setOnClickListener(clickListener);
		
		rl_hotel=(RelativeLayout)findViewById(R.id.rl_type_hotel);
		rl_hotel.setOnClickListener(clickListener);
		
		rl_shopping=(RelativeLayout)findViewById(R.id.rl_type_shopping);
		rl_shopping.setOnClickListener(clickListener);
		
		rl_attraction=(RelativeLayout)findViewById(R.id.rl_type_attraction);
		rl_attraction.setOnClickListener(clickListener);
		
		rl_ktv=(RelativeLayout)findViewById(R.id.rl_type_ktv);
		rl_ktv.setOnClickListener(clickListener);
		
		rl_film=(RelativeLayout)findViewById(R.id.rl_type_film);
		rl_film.setOnClickListener(clickListener);
		
		rl_sports=(RelativeLayout)findViewById(R.id.rl_type_sports);
		rl_sports.setOnClickListener(clickListener);
		
		rl_trip=(RelativeLayout)findViewById(R.id.rl_type_trip);
		rl_trip.setOnClickListener(clickListener);
		
	}
	
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.rl_type_restaurant:
				ConstantData.CurrentSelectedType="餐饮";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_hotel:
				ConstantData.CurrentSelectedType="酒店";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_shopping:
				ConstantData.CurrentSelectedType="购物";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_attraction:
				ConstantData.CurrentSelectedType="景点";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_ktv:
				ConstantData.CurrentSelectedType="KTV";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_film:
				ConstantData.CurrentSelectedType="电影";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_sports:
				ConstantData.CurrentSelectedType="运动";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_trip:
				ConstantData.CurrentSelectedType="旅行";
				startActivity(new Intent(TypeActivity.this,ShopListActivity.class));
				break;
			case R.id.rl_type_search:
				Intent intent=new Intent(TypeActivity.this,SearchActivity.class);
				startActivity(intent);
				break;
			case R.id.lly_type_turnback:
				finish();
				break;
				
			}
		}
	};

}
