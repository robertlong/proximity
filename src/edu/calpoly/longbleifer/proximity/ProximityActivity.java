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

import android.os.Bundle;
import android.os.RemoteException;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ProximityActivity extends Activity implements IBeaconConsumer {
	
	protected static final String TAG = "Proximity";
    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);
    private HashMap<String, Trigger> beaconHistory;
    private Api api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity);
		
		iBeaconManager.bind(this);
	}
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        iBeaconManager.unBind(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proximity, menu);
		return true;
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
	    	
	    	// TODO Render a view with the trigger data.
	    	TextView log = (TextView) findViewById(R.id.beacon_log);
	    	log.append("\n " + trigger.toString());
	    }

	    @Override
	    public void failure(RetrofitError retrofitError) {
	    	Log.e(TAG, retrofitError.toString());
	    }
	};
}
