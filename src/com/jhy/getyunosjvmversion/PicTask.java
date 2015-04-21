package com.jhy.getyunosjvmversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

public class PicTask {
	public static String g_tempFile = "/mnt/sdcard/local/tmp/tempMobile.jpg";
	
	
    public static String getResponse(HttpResponse httpResponse) throws IOException, IllegalStateException{

        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader2 = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));

        for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
                .readLine()) {
            builder.append(s);
        } 
        
        return builder.toString();
    }
	/**
	 * 
	 * @param handler
	 * @param FilePath
	 * @param needReduce 是否需要生成缩略图
	 */
	public static boolean putPic(Handler handler, String feedid,File file){
		   HttpServer mService = new HttpServer();
		   StringBuffer urlSuffix = new StringBuffer("UserFeedbackServer/feed/upload");
		   urlSuffix.append("?feedid="+feedid);
		   /*File file = new File(FilePath);

			AssetManager am = ctx.getAssets();
			am.*/
		   try
		   {
			   HttpResponse mResponse;
			   mResponse = mService.sendFile(false, urlSuffix.toString(), file);
			   int code = mResponse.getStatusLine().getStatusCode();
			   if(code == HttpStatus.SC_OK)
			   {
				   String response = getResponse(mResponse);
				   JSONObject jsonObject = new JSONObject(response);
				   String result = jsonObject.getString("result");
				   
				   if(result.equals("true")){
					   return true;
				   } 
			   }

		   }catch(JSONException e){
			   Log.i("tuan", "json JSONException.");
		   }catch(Exception e){
			   Log.i("tuan", "Exception.");
			   e.printStackTrace();
		   }
		   return true;
	}

}
