package com.example.dazhongtucao.data;

import org.json.JSONObject;

public class ConstantData {
	
	public static String URL="http://10.1.1.124:8080/DZTC";
	
	//登录信息
	public static int UserID;
	public static String Key;
	public static String UserName;
	public static String UserLogo1;
	public static String UserLogo2;
	
	//经纬度
	public static double Latitude;
	public static double Longitude;
	
	//各种状态
	public static boolean isEnter=false;
	
	//各种选择
	public static String CurrentType;
	public static String CurrentSelectedType;
	public static String CurrentSelectedShopname;
	public static int CurrentSelectedShopID;
	public static int CurrentSelectedUserID;
	public static String CurrentSelectedUsername;
	
	//回复吐槽相关
	public static JSONObject tucaoJson;
	public static String tucaoLogo;
	public static int TucaoOrFB;//0为吐槽，1为发布
	public static int ID;
	
}
