package com.baha;

import java.util.List;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Yann on 07/01/2017.
 */

public class BahaApplication extends Application {

	public static BahaApplication app;

	public static Resources RESOURCES;

	@Override
	public void onCreate() {
		super.onCreate();
		if (isMainProcess()) {
			app = this;
			RESOURCES = getResources();
		}
	}

	private boolean isMainProcess() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = android.os.Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}
}
