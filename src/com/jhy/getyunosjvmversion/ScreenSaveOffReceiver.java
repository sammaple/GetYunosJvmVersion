package com.jhy.getyunosjvmversion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenSaveOffReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		int state = arg1.getIntExtra("screen_saver_state", 1);
		if (state == 0 ) {
			Log.d(ScreenSaveOffReceiver.class.getName(), "yunbo keji  out sreenout");
		}else{

			Log.d(ScreenSaveOffReceiver.class.getName(), "yunbo keji  out sreenin");
		}
	}

}
