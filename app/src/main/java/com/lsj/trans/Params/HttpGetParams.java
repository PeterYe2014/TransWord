package com.lsj.trans.Params;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Map;


public class HttpGetParams extends HttpParams{
	
	@Override
	public HttpParams put(String key, String value){
		params.put(key, value);
		return this;
	}

	@Override
	public String Send(String baseUrl) throws Exception {
		String fullUrl = baseUrl+"?" + getRequestData(params).toString();
//		URL url = new URL(URLEncoder.encode(fullUrl,"utf-8"));
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setRequestMethod("GET");
//		con.setConnectTimeout(5000);
//		// 获取响应
//		int responseCode = con.getResponseCode();
//		if(responseCode == HttpURLConnection.HTTP_OK){
//			InputStream inputStream = con.getInputStream();
//			return ReadInputStream(inputStream);
//		}
//		else
//			throw new Exception("连接错误");

		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(fullUrl);
		HttpResponse response = client.execute(httpGet);
		return ReadInputStream(response.getEntity().getContent());

	}
	public StringBuilder getRequestData(Map<String,String> param){

		StringBuilder builder =  new StringBuilder();
		for(Map.Entry<String,String> entry: param.entrySet()){
			builder.append(entry.getKey())
					.append("=")
					.append(entry.getValue())
					.append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		Log.i(getClass().getName(),builder.toString());
		return  builder;
	}

}
