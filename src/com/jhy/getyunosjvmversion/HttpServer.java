package com.jhy.getyunosjvmversion;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpServer {
	
	
	   public static String makeUriString(boolean isSsl, String urlSuffix){
	        String us = "";
	        
	        if(isSsl){
	        	us = "https" + "://" + "www.upseven.net/"+urlSuffix;
	        	//us = "https" + "://" + "www.hellokitty1.88ip.cn:1100/"+urlSuffix;
	        }else{
	        	//us = "http" + "://" + "www.hellokitty1.88ip.cn:1100/"+urlSuffix;
	        	us = "http" + "://" + "www.upseven.net/"+urlSuffix;
	        }
	        return us;
	    }
	   
	   
	/**
	 * 
	 * @param isSsl
	 * @param urlSuffix บ๓ืบ
	 * @param body
	 * @return
	 * @throws IOException
	 */
	public HttpResponse sendHttpClient(boolean isSsl, String urlSuffix, String body)
			throws IOException {
		HttpClient client = new DefaultHttpClient();
		String us = makeUriString(isSsl, urlSuffix);
		HttpPost method = new HttpPost(URI.create(us));
		/*IShareApp app = MyApplication.getInstance().getApp();
		/*if(!app.getSessionId().equals("")){
			method.addHeader("Cookie", "JSESSIONID="+app.getSessionId()+"; Path=/; HttpOnly");
		}
		if(app.getUser().authority!=null && !app.getUser().authority.equals("")){
			method.addHeader("SetAuthority", URLEncoder.encode(app.getUser().authority));
		}*/
		if(body != null && !body.equals("")){
			StringEntity entity = new StringEntity(body, "UTF-8");
			method.setEntity(entity);
		}
		return client.execute(method);
	}
	
	public HttpResponse sendFile(boolean isSsl, String urlSuffix, File file) throws ClientProtocolException, IOException{
        HttpClient httpclient = new DefaultHttpClient();  
        //IShareApp app = MyApplication.getInstance().getApp();
		String us = makeUriString(isSsl, urlSuffix);
		HttpPost post = new HttpPost(URI.create(us));
		FileEntity fileentity = new FileEntity(file, "application/x-zip-compressed;");
		fileentity.setChunked(true);
		post.setEntity(fileentity);

		post.addHeader("Content-Type", "application/x-zip-compressed;");

		/*if(app.getUser().authority!=null && !app.getUser().authority.equals("")){
			post.addHeader("SetAuthority", URLEncoder.encode(app.getUser().authority));
		}
		if(!app.getSessionId().equals("")){
			post.addHeader("Cookie", "JSESSIONID="+app.getSessionId()+"; Path=/; HttpOnly");
		}
		*/
		return httpclient.execute(post);
	}
}
