package edu.calpoly.longbleifer.proximity;

import org.json.JSONObject;

import edu.calpoly.longbleifer.proximity.api.Client;
import edu.calpoly.longbleifer.proximity.api.TriggerHandler;
import edu.calpoly.longbleifer.proximity.models.Trigger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TriggerLoadingActivity extends Activity {
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.trigger_loading_activity);
		
		this.context = this;
		
		Intent intent = this.getIntent();
        
        if (intent != null) {
        	Log.i("Proximity", "Downloading trigger with uuid: " + intent.getData().getLastPathSegment());
        	String uuid = intent.getData().getLastPathSegment().toLowerCase();
        	Client.getTrigger(uuid, onFoundBeacon);
        } else {
        	Intent targetIntent = new Intent(context, HistoryActivity.class);
	    	targetIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    	context.startActivity(targetIntent);
        }
		
		super.onCreate(savedInstanceState);
	}
	
	private TriggerHandler onFoundBeacon = new TriggerHandler() {
	    @Override
	    public void afterCreate(Trigger trigger) {
	    	
	    	Intent targetIntent = new Intent(context, ProximityActivity.class);
	    	targetIntent.putExtra("trigger-id", trigger.getId());
	    	targetIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    	context.startActivity(targetIntent);
	    	
	    	//beaconHistory.add(trigger.uuid);
	    }

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			Intent targetIntent = new Intent(context, HistoryActivity.class);
	    	targetIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    	context.startActivity(targetIntent);
			super.onFailure(e, errorResponse);
		}
	    
	};

}
