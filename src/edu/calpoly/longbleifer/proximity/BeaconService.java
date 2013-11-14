package edu.calpoly.longbleifer.proximity;

import java.util.Collection;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BeaconService extends Service implements IBeaconConsumer {
	
	protected static final String TAG = "BeaconService";
    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);
    private HashMap<String, Trigger> beaconHistory;
    private Api api;
    
    public static Boolean started = false;
    

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Created service");
		iBeaconManager.bind(this);
		started = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Destroyed service");
		iBeaconManager.unBind(this);
		started = false;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
    public void onIBeaconServiceConnect() {
		beaconHistory = new HashMap<String, Trigger>();
		api = ApiClient.buildApi();
		
        iBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override 
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                if (iBeacons.size() > 0) {
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
                        
                        beaconHistory.put(uuid, new Trigger(uuid));
                	}
                    
                }
            }
        });

        try {
            iBeaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }
	
	private Callback<Trigger> onFoundBeacon = new Callback<Trigger>() {
	    @Override
	    public void success(Trigger trigger, Response response) {
	    	Log.i(TAG, trigger.toString());
	    }

	    @Override
	    public void failure(RetrofitError retrofitError) {
	    	Log.e(TAG, retrofitError.toString());
	    }
	};

}
