package com.jhy.getyunosjvmversion.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class GetVersionService extends Service {

	public class GetVersionServiceImp extends IGetVersionService.Stub{

		@Override
		public String getVersion() throws RemoteException {
			// TODO Auto-generated method stub
			Log.d("jvm",""+123);
			return "213";
		}
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("jvm","binder");
		return new GetVersionServiceImp();
	}

}
