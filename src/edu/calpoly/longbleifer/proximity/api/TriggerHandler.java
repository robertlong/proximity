package edu.calpoly.longbleifer.proximity.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import edu.calpoly.longbleifer.proximity.models.Tab;
import edu.calpoly.longbleifer.proximity.models.Trigger;

public class TriggerHandler extends JsonHttpResponseHandler {
	@Override
    public void onSuccess(JSONObject response) {
		
		try {
			String uuid = response.getString("uuid");
			double lat = response.getDouble("latitude");
			double lng = response.getDouble("longitude");
			String name = response.getString("name");
			Trigger trigger = new Trigger(uuid, lat, lng, name);
			trigger.save();
			
			JSONArray jsonTabs = response.getJSONArray("tabs");
			
			for(int i = 0; i < jsonTabs.length(); i++) {
				JSONObject jsonTab = jsonTabs.getJSONObject(i);
				String type = jsonTab.getString("type");
				String title = jsonTab.getString("title");
				int position = jsonTab.getInt("position");
				String metadata = jsonTab.getJSONObject("metadata").toString();
				new Tab(type, title, position, metadata, trigger).save();
			}
			
			this.afterCreate(trigger);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
	
	public void afterCreate(Trigger trigger) {
		
	}
}
