package edu.calpoly.longbleifer.proximity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

public class BeaconService extends Service implements IBeaconConsumer {

	protected static final String TAG = "BeaconService";
    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);
    private HashMap<String, Trigger> beaconHistory;
    private Api api;
    private Region region;
    private Context context;
    
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
    	context = this;
    	Log.i(TAG, "Called onCreate");
    	region = new Region("main", null, null, null);
    	
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	iBeaconManager.bind(this);      
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    	Log.i(TAG, "Called onDestroy");
		iBeaconManager.unBind(this);
		super.onDestroy();
    }
    
    private void startBeaconScan() {
    	try {
            iBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {   }
    }
	
	private void stopBeaconScan() {
		try {
            iBeaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {   }
	}

	@Override
    public void onIBeaconServiceConnect() {
		beaconHistory = new HashMap<String, Trigger>();
		api = ApiClient.buildApi();
		
        iBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override 
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                if (iBeacons.size() > 0) {
                	stopBeaconScan();
                	
                	for(IBeacon beacon : iBeacons) {
                		String uuid = beacon.getProximityUuid();
                		int major = beacon.getMajor();
                		int minor = beacon.getMinor();
                		double distance = beacon.getAccuracy();
                		
                		Log.i(TAG, "I see a beacon "+ distance +" meters away.");
                        Log.i(TAG, "UUID: " + uuid + " Major: " + major + " Minor: " + minor);
                        
                        if (!beaconHistory.containsKey(uuid)) {
                        	Log.i(TAG, "Found new beacon ");
                        	Log.i(TAG, "UUID: " + uuid + " Major: " + major + " Minor: " + minor);
                        	
                        	api.findBeacon(uuid, onFoundBeacon);
                        }
                        
                	}
                }
            }
        });

        startBeaconScan();
    }
	
	private Callback<Trigger> onFoundBeacon = new Callback<Trigger>() {
	    @Override
	    public void success(Trigger trigger, Response response) {
	    	Log.i(TAG, trigger.toString());
	    	
	    	Intent targetIntent = new Intent(context, ProximityActivity.class);
	    	PendingIntent intent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    	
	    	NotificationManager notificationManager =
	    			(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    	Notification  notification = new Notification.Builder(context)
	    	 .setSmallIcon(R.drawable.ic_launcher)
	         .setContentTitle("Found beacon!")
	         .setContentText(trigger.toString())
	         .setContentIntent(intent)
	         .build();
	    	
	    	notificationManager.notify(35289, notification);
	    	
	    	Log.i("NOTIFICATION", trigger.toString());
	    	beaconHistory.put(trigger.uuid, trigger);
	    	startBeaconScan();
	    }

	    @Override
	    public void failure(RetrofitError retrofitError) {
	    	Log.e(TAG, retrofitError.getMessage());
	    	startBeaconScan();
	    }
	};

}
