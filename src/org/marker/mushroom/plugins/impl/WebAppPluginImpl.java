package org.marker.mushroom.plugins.impl;

import java.io.IOException;

import net.sf.json.JSONObject;

//import org.marker.mushroom.baidu.BaiduGeocodingAPI;
//import org.marker.mushroom.baidu.BaiduLocation;
import org.marker.mushroom.beans.Jokes;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.plugins.AbstractPlugin;
import org.marker.mushroom.plugins.YLFMEngine;


public class WebAppPluginImpl extends AbstractPlugin{

	public WebAppPluginImpl() {
		super("mobileapp"); 
	}

	@Override
	public String menu() {
		return null;
	}

	
//	public String location() throws IOException{
//		String latitude  = request.getParameter("latitude");//纬度
//		String longitude = request.getParameter("longitude");//纬度
//	
//		
//		BaiduLocation lo = BaiduGeocodingAPI.get("8E8E0A8005f39dbc080f58e207ea62e0", latitude, longitude);
//		YLFMEngine e = getYLFMEngine("_data.html"); 
// 
//		e.put("data", JSONObject.fromObject( new ResultMessage(true, lo.getFormatted_address())).toString()); 
//		this.dispatcher(e);
//		return null;
//	}
	
	
	@Override
	public String action() {
		String latitude  = request.getParameter("latitude");//纬度
		String longitude = request.getParameter("longitude");//纬度
		String content   = request.getParameter("content");//内容
		String address   = request.getParameter("address");//地理位置
		System.out.println(latitude);
		
		Jokes joke = new Jokes();
		joke.setContent(content);
		joke.setLatitude(Double.valueOf(latitude));
		joke.setLongitude(Double.valueOf(longitude));
		joke.setAddress(address);
		commonDao.save(joke);
		
		try {
			response.sendRedirect("/"); 
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}

}
