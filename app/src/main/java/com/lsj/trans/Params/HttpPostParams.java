package com.lsj.trans.Params;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpPostParams extends HttpParams{
	@Override
	public HttpParams put(String key, String value){
		params.put(key, value);
		return this;
	}

	@Override
	public String Send(String base) throws Exception {
		URL url = new URL(base);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(3000);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		// 获取请求数据
		byte[] data = getRequestData(params,"UTF-8").toString().getBytes();
		con.setRequestProperty("Content-Length",String.valueOf(data.length));
		OutputStream os = con.getOutputStream();
		os.write(data);
		// 获取响应
		int responseCode = con.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK){
			InputStream inputStream = con.getInputStream();
			return ReadInputStream(inputStream);
		}
		else
			throw new Exception("连接错误");
	}
	public StringBuilder getRequestData(Map<String,String> prarams, String encode){

		StringBuilder builder = new StringBuilder();
		try{
			for(Map.Entry<String,String> entity: prarams.entrySet()){
				builder.append(entity.getKey())
						.append("=")
						.append(URLEncoder.encode(entity.getValue(),encode))
						.append("&");
			}
			Log.i("info",builder.toString());
			// 删除最后一个 &
			builder.deleteCharAt(builder.length()-1);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return  builder;
	}

}
