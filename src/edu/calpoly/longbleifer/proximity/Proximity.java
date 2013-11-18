package edu.calpoly.longbleifer.proximity;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class Proximity extends Application {
	
	public static final String TAG = "Proximity";
	
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this, true);
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
