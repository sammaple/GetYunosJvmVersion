package com.jhy.getyunosjvmversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected static final int THREAD = 0;
	private static final String TAG = "InjectActivity";
	Button bt, button_static,bt_install,bt_button_install_cmd_slient,button_appstore,button_resourceids;
	Button button_injecttest,button_jni,button_jni_runtime;
	TextView tx;

	String[] vm_property = { "java.vm.name", "java.vm.specification.vendor",
			"java.vm.vendor", "java.vm.specification.name",

			"java.specification.name", "java.specification.vendor",

			"java.vendor", "ro.yunos.version",

			"lemur.vm.version" };
	
	 /*private static int sA = 1;  
	    public static void setA(int a) {  
	        sA = a;  
	    }  
	    public static int getA() {  
	        return sA;  
	    }  */
	  static{

			System.loadLibrary("inso");
	  }

	public native void setA(int i);
    public native int getA();
	
	class Test extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Log.d("xxxx", "new threadin!");
			while(true){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bt = (Button) findViewById(R.id.button_start);
		tx = (TextView) findViewById(R.id.textView_judge);
		button_static = (Button) findViewById(R.id.button_static);
		bt_install = (Button) findViewById(R.id.button_install);
		bt_button_install_cmd_slient = (Button) findViewById(R.id.button_install_cmd_slient);
		
		button_appstore = (Button) findViewById(R.id.button_appstore);
		button_resourceids = (Button) findViewById(R.id.button_resourceids);
		
		button_injecttest = (Button) findViewById(R.id.button_injecttest);
		
		button_jni = (Button) findViewById(R.id.button_jni);
		button_jni_runtime =  (Button) findViewById(R.id.button_jni_runtime);

		bt.setOnClickListener(this);
		button_static.setOnClickListener(this);
		bt_install.setOnClickListener(this);
		bt_button_install_cmd_slient.setOnClickListener(this);
		button_appstore.setOnClickListener(this);
		button_resourceids.setOnClickListener(this);
		
		button_injecttest.setOnClickListener(this);
		button_jni.setOnClickListener(this);
		button_jni_runtime.setOnClickListener(this);
		

		
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.button_start) {
			///startJudge();
				
			/*Intent intent = new Intent();
			intent = intent.setAction(Intent.ACTION_MAIN);
			intent.setPackage("com.softwinner.TvdFileManager");
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);*/
			AssetManager am = getAssets();
			MediaPlayer mp =new MediaPlayer();
			try {
				mp.setDataSource(am.openFd("HTC.mp3").getFileDescriptor());
				mp.prepare();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mp.start();

		} else if (arg0.getId() == R.id.button_static) {
			//startStaticIP();

			//new PutZIPTask().start();
			boolean isopend =false;
			Log.i("xxx","wifi is enabled.");       
			 WifiManager wifiManager = (WifiManager)
                     this.getSystemService(Context.WIFI_SERVICE);
			 if(wifiManager.isWifiEnabled()){

	            	tx.setText(tx.getText()+",wifi已经打开");
	            	isopend = true;
			 }else{

				 wifiManager.setWifiEnabled(true);
			 }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();                                                  
            if (wifiInfo == null) {                                                                        
            	Log.i("xxx","getConnectionInfo error");       
            	tx.setText(tx.getText()+",getConnectionInfo error!");                                                                      
            }else{

                String wifiMAC = wifiInfo.getMacAddress();                                  
            	Log.i("xxx",""+wifiMAC); 

            	tx.setText(tx.getText()+wifiMAC);
            	tx.setText(tx.getText()+","+wifiInfo.toString());
            }
            if(!isopend){

   			 wifiManager.setWifiEnabled(false);
            }
			
		}else if (arg0.getId() == R.id.button_install) {
			//install_usepackagemanager();
			//new runPackage().start();
			/*Install(Environment.getExternalStorageDirectory()+"tmp.apk","com.farproc.wifi.analyzer");
			PackageManager packageManager = ctx.getPackageManager();*/

			PackageInfo pi;
			PackageManager packageManager = getPackageManager();
				try {
					pi = packageManager.getPackageInfo("com.androd.bugreporter",
							PackageManager.GET_ACTIVITIES);

					Log.i("tuan","1--------------- "+ pi.versionName);
					
					pi = packageManager.getPackageInfo("com.androd.bugreporter",0);

					Log.i("tuan","2--------------- "+ pi.versionName);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else if (arg0.getId() == R.id.button_install_cmd_slient) {
			//install_usepackagemanager();
			try {
				execArrayCommand(new String[]{"sh","-c","pm install /mnt/sdcard/tmp.apk"});
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else if(arg0.getId() == R.id.button_appstore){

			/*Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("appstore://start?module=myeducation"));
			startActivity(intent);*
			
			/*for(int i=0;i<100;i++){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Test().run();*/
			
			/*Timer mFixedTransferLogTimer = new Timer();
			mFixedTransferLogTimer.scheduleAtFixedRate(new TimerTask() {                
				@Override                
				public void run() {                    
					Log.d("xxxx","hahaha==================="); 
					}           
				}, 3000, 10 * 1000);  */
			
			Toast.makeText(this, "jhy", Toast.LENGTH_LONG).show();
			
		
		}else if(arg0.getId() == R.id.button_resourceids){
			//int resId = getResources().getIdentifier("button_appstore", "id" , getPackageName());
			//((Button)findViewById(resId)).setText("哈哈");
			
			try{

				  Field field=R.id.class.getField("button_appstore");

				  int i= field.getInt(new R.id());

				  Log.d("jjjjj",i+"");
				  ((Button)findViewById(i)).setText("哈哈");
				 // return i;

				}catch(Exception e){

				  Log.e("jjjjj",e.toString());


				}
		}else if(arg0.getId() == R.id.button_injecttest){
			/*Object bind_activity = getSystemService("activity");
			Log.d(TAG,"bind activity is:"+bind_activity);
			
			Field[] fields = bind_activity.getClass().getDeclaredFields();
			int index_files =0 ;
			for (Field field : fields) {
				Log.d(TAG,"bind activity fileds %d [] is:"+
						index_files+field.getName());
				index_files++;
			}*/
			
			injectService();


		      
		      
		}else if(arg0.getId() == R.id.button_jni){
			Log.i("TTT", "z1:" + getA());  
            //setA(getA() + 1);  
			setA(2);  
			Log.i("TTT ", "z2:" + getA());  
		}else if(arg0.getId() == R.id.button_jni_runtime){
			

			Class<?> a = null;
			try {
				a = Class.forName("android.os.JHY_JNITest");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("TTT", "a class:"+a);  
			
			for (Method i : a.getMethods()) {

				Log.i("TTT", "a class method:"+i);  
			}
			
			Method m = null;
			
			try {
				m = a.getDeclaredMethod("test_run", new Class[]{int.class,double.class,String.class});
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.i("TTT", "a class method:"+m);  
			try {
				m.setAccessible(true);
				
				m.invoke(a.newInstance(), new Object[]{56,3.4,"haha"});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	
	void injectService(){
		Class a = null;
		try {
			a = Class.forName("android.os.ServiceManager");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG,"ServiceManager is:"+a);
		
		Field[] fields = a.getDeclaredFields();
		int index_files =0 ;
		for (Field field : fields) {
			Log.d(TAG,"ServiceManager fileds %d [] is:"+
					index_files+field.getName());
			index_files++;
		}
		
		
		Field localField = null;
		try {
			localField = a.getDeclaredField("sCache");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      localField.setAccessible(true);
	      HashMap f =null;
	      try {
			 f = (HashMap)localField.get(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      for (Object key : f.keySet()) {

				Log.d(TAG,"ServiceManager is:"+key.toString());
				Log.d(TAG,"ServiceManager is:"+f.get(key));
		}
	}
	
	class PutZIPTask extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//super.run();
			AssetManager am = getAssets();
			InputStream inputStream = null; 

			  try { 

			   inputStream = am.open("juling.jhy.zip"); 

			  } catch (IOException e) { 

			   Log.e("tag", e.getMessage()); 

			  } 
			  

			File file = new File(Environment.getExternalStorageDirectory(),"temp.zip");
			try {
				FileOutputStream fs = new FileOutputStream(file);
				byte buffer[] = new byte[4048];
				while( -1 != inputStream.read(buffer)){
					Log.e("tag","writing..."); 
					fs.write(buffer);
				}
				

				if(inputStream!=null){
					inputStream.close();
					inputStream = null;
				}
				fs.flush();
				fs.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PicTask.putPic(null, ""+UUID.randomUUID(), file);
		}
		
	}
	
	class runPackage extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//super.run();
			install_usepackagemanager();
		}
		
	}

	private void install_usepackagemanager() {
		PackageManager pm = this.getPackageManager(); //获得PackageManager对象
		
		AssetManager assetManager = getAssets(); 
		  
		
		InputStream inputStream = null; 

		  try { 

		   inputStream = assetManager.open("com.farproc.wifi.analyzer.apk"); 

		  } catch (IOException e) { 

		   Log.e("tag", e.getMessage()); 

		  } 
		  

		File file = new File(Environment.getExternalStorageDirectory(),"tmp.apk");
		try {
			FileOutputStream fs = new FileOutputStream(file);
			byte buffer[] = new byte[4048];
			while( -1 != inputStream.read(buffer)){
				Log.e("tag","writing..."); 
				fs.write(buffer);
			}
			

			if(inputStream!=null){
				inputStream.close();
				inputStream = null;
			}
			fs.flush();
			fs.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		
		//File file = new File(Environment.getExternalStorageDirectory(),"HtmlUI1.apk");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		
		startActivity(intent);
		
	}
	
	private void startStaticIP() {

		int netStatus = -1;
		try {
			ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectManager
					.getActiveNetworkInfo();
			if (activeNetworkInfo != null) {
				if (activeNetworkInfo.isAvailable()
						&& activeNetworkInfo.isConnected()) {
					netStatus = 1;
				}else if(activeNetworkInfo.isConnected()){
					netStatus = 2;
				}else{
					netStatus = 3;
				}
				tx.setText(String.valueOf(netStatus));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * ConnectivityManager mService = (ConnectivityManager)
		 * getSystemService(Context.CONNECTIVITY_SERVICE); EthernetManager
		 * mEthManager = (EthernetManager)
		 * getSystemService(Context.ETHERNET_SERVICE); if
		 * (mEthManager.getState() == EthernetManager.ETHERNET_STATE_ENABLED) {
		 * EthernetDevInfo mInterfaceInfo = mEthManager.getSavedConfig(); String
		 * mIp; String mMask; String mGw; String mDns; mIp = "192.168.0.118";
		 * mMask = "255.255.255.0"; mGw = "192.168.0.1"; mDns = "192.168.0.1";
		 * mInterfaceInfo
		 * .setConnectMode(EthernetDevInfo.ETHERNET_CONN_MODE_MANUAL);
		 * mInterfaceInfo.setIpAddress(mIp); mInterfaceInfo.setNetMask(mMask);
		 * mInterfaceInfo.setDnsAddr(mDns); mInterfaceInfo.setGateWay(mGw); try
		 * { mEthManager.updateDevInfo(mInterfaceInfo); Thread.sleep(500); }
		 * catch (Exception e) { e.printStackTrace(); } } else {
		 * Toast.makeText(this, "Ethernet state disabled!", 5000).show(); }
		 */
	}

	Handler mhadler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case THREAD:
				tx.setText((String) msg.obj);
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void startJudge() {
		// TODO Auto-generated method stub
		// Message m = mhadler.obtainMessage(THREAD, "test");
		// m.sendToTarget();

		try {
			// execCommand("getprop");
			execCommand("");
			// execCommand("ls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Properties p = System.getProperties();

		StringBuilder strb = new StringBuilder(tx.getText());
		for (String str : vm_property) {
			System.err.println(p.get(str));

			strb.append(str + ":" + p.get(str) + "\n");
		}

		tx.setText(strb.toString());

	}
	
	
	public void Install(String fileName,String packageName)//  
	   {  
	      
	    Uri uri = Uri.fromFile(new File(fileName));  
	  
	       int installFlags = 0;  
	       PackageManager pm = getPackageManager();  
	       try {  
	           PackageInfo pi = pm.getPackageInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);//  
	           if(pi != null) {  
	               //installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;  
	           }  
	       } catch (NameNotFoundException e) {  
	       }  
	       Toast.makeText(MainActivity.this, "安装开始", Toast.LENGTH_SHORT).show();  
	       PackageInstallObserver observer = new PackageInstallObserver();  
	       //pm.installPackage(uri, observer, installFlags, packageName);//下面有详细  
	   }  
	   //用于显示结果  
	   class PackageInstallObserver extends IPackageInstallObserver.Stub {  
	       public void packageInstalled(String packageName, int returnCode) {//如果returnCode == 1就为成功  
	             //give me file
	       }  
	   };  
	   
	public void execArrayCommand(String[] command) throws IOException {
		Runtime runtime = Runtime.getRuntime();

		Process proc = runtime.exec(command);

		try {

			if (proc.waitFor() != 0) {

				System.err.println("exit value = " + proc.exitValue());

			} else {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(proc.getInputStream()));
				int read;
				char[] buffer = new char[4096];
				StringBuffer output = new StringBuffer();
				while ((read = reader.read(buffer)) > 0) {
					output.append(buffer, 0, read);
				}
				reader.close();

				// Waits for the command to finish.
				// process.waitFor();

				System.out.println(output.toString());
				Message m = mhadler.obtainMessage(THREAD, output.toString());
				m.sendToTarget();
			}

		} catch (InterruptedException e) {

			System.err.println(e);

		}

	}
	
	public void execCommand(String command) throws IOException {
		Runtime runtime = Runtime.getRuntime();

		Process proc = runtime.exec(new String[] { "sh", "-c",
				"getprop|grep uuid" });

		try {

			if (proc.waitFor() != 0) {

				System.err.println("exit value = " + proc.exitValue());

			} else {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(proc.getInputStream()));
				int read;
				char[] buffer = new char[4096];
				StringBuffer output = new StringBuffer();
				while ((read = reader.read(buffer)) > 0) {
					output.append(buffer, 0, read);
				}
				reader.close();

				// Waits for the command to finish.
				// process.waitFor();

				System.out.println(output.toString());
				Message m = mhadler.obtainMessage(THREAD, output.toString());
				m.sendToTarget();
			}

		} catch (InterruptedException e) {

			System.err.println(e);

		}

	}

}
