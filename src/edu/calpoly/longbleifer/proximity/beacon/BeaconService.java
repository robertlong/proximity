package edu.calpoly.longbleifer.proximity.beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import edu.calpoly.longbleifer.proximity.ProximityActivity;
import edu.calpoly.longbleifer.proximity.R;
import edu.calpoly.longbleifer.proximity.api.Client;
import edu.calpoly.longbleifer.proximity.api.TriggerHandler;
import edu.calpoly.longbleifer.proximity.models.Trigger;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class BeaconService extends Service implements IBeaconConsumer {

	protected static final String TAG = "BeaconService";
    private IBeaconManager iBeaconManager;
    private ArrayList<String> beaconHistory;
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
    	if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
    		iBeaconManager = IBeaconManager.getInstanceForApplication(this);
    		iBeaconManager.bind(this);
    	}
    	    
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
		beaconHistory = new ArrayList<String>();
		
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
                        
                        if (!beaconHistory.contains(uuid)) {
                        	Log.i(TAG, "Found new beacon ");
                        	Log.i(TAG, "UUID: " + uuid + " Major: " + major + " Minor: " + minor);
                        	
                        	Client.getTrigger(uuid, onFoundBeacon);
                        }
                        
                	}
                }
            }
        });

        startBeaconScan();
    }
	
	private TriggerHandler onFoundBeacon = new TriggerHandler() {
	    @Override
	    public void afterCreate(Trigger trigger) {
	    	Log.i(TAG, trigger.toString());
	    	
	    	Intent targetIntent = new Intent(context, ProximityActivity.class);
	    	targetIntent.putExtra("trigger-id", trigger.getId());
	    	PendingIntent intent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    	
	    	NotificationManager notificationManager =
	    			(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    	Notification  notification = new Notification.Builder(context)
	    	 .setSmallIcon(R.drawable.ic_launcher)
	         .setContentTitle(trigger.name)
	         .setContentText(trigger.message)
	         .setContentIntent(intent)
	         .setAutoCancel(true)
	         .build();
	    	
	    	notificationManager.notify(35289, notification);
	    	
	    	sendToPebble(trigger.name, trigger.message);
	    	
	    	beaconHistory.add(trigger.uuid);
	    	
	    	Log.i("NOTIFICATION", trigger.toString());
	    	startBeaconScan();
	    }
	};
	
	private void sendToPebble(String title, String notificationText) {
        title = title.trim();
        notificationText = notificationText.trim();
        if (title.trim().isEmpty() || notificationText.isEmpty()) {
            return;
        }

        // Create json object to be sent to Pebble
        final Map<String, Object> data = new HashMap<String, Object>();

        data.put("title", title);

        data.put("body", notificationText);
        final JSONObject jsonData = new JSONObject(data);
        final String notificationData = new JSONArray().put(jsonData).toString();

        // Create the intent to house the Pebble notification
        final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");
        i.putExtra("messageType", "PEBBLE_ALERT");
        i.putExtra("sender", getString(R.string.app_name));
        i.putExtra("notificationData", notificationData);

        sendBroadcast(i);
    }

}
