package com.scout_zong.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.scout_zong.bean.FangjianBean;
import com.scout_zong.bean.HomeBean;
import com.scout_zong.jdbc.DBContent;

public class SousuoReuqest extends BaseRequest {
	public static final int TAG=3;
	private ArrayList<HomeBean> arrayList;
	private ArrayList<FangjianBean> fangxing;
	private String sousuo;
	private DBContent db;

	@Override
	public String getJson() {
		lodingData();
		try {
			JSONObject object;
			JSONArray array=new JSONArray();
			for (int i = 0; i < this.arrayList.size(); i++) {
				object=new JSONObject();
				HomeBean bean=this. arrayList.get(i);
				object.put("Homename",bean.getName());
				object.put("HomeAdder",bean.getDizhi());
				object.put("list", bean.getList());
				object.put("log", bean.getlog());
				array.add(object);
				System.out.println("asdfasdfasdfasdf");
			}
			
			return array.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return "1";
		}

	}

	public SousuoReuqest(String sousuo) {
		try {
			 this.sousuo =sousuo;
			db = new DBContent();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("��ȡJSON����");
		}
	}

//	private String suijishu() {
//		StringBuilder str = new StringBuilder();// ����䳤�ַ���
//		Random random = new Random();
//		// ����������֣�����ӵ��ַ���
//		for (int i = 0; i < 8; i++) {
//			str.append(random.nextInt(10));
//		}
//		// ���ַ���ת��Ϊ���ֲ����
//		int num = Integer.parseInt(str.toString());
//		return num + "";
//	}
private void lodingData() {
	arrayList=new ArrayList<>();
	db = new DBContent();
	arrayList = new ArrayList<>();
	ResultSet rs = db.mehuchaxun(sousuo);
	try {
		while (rs.next()) {
			String string=rs.getString("fangjian");
			String [] fangxingString = string.split(",");  
			String string1=rs.getString("fangjianshu");
			String [] fangjianshu = string1.split(",");  
			String string2=rs.getString("jiage");
			String [] jiage = string2.split(",");  
			fangxing = new ArrayList<>();
			for (int i = 0; i < fangxingString.length; i++) {
				fangxing.add(new FangjianBean(fangxingString[i], Integer.valueOf(jiage[i]), Integer.valueOf(fangjianshu[i])));
			}
//			fangxing = new ArrayList<>();
//			fangxing.add(new FangjianBean(fangjianName, jiage, shangjianshu))
			System.out.println( rs.getString("name")+ rs.getString("adder")+ rs.getString("log")+"");
			arrayList.add(new HomeBean( rs.getString("name"), rs.getString("adder"), fangxing,rs.getString("log")));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
