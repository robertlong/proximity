package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import edu.calpoly.longbleifer.proximity.models.Tab;
import android.content.Context;
import android.content.Intent;

public class IntentBuilder {
	public static Intent build(Context context, Tab tab) {
		try {
			JSONObject metadata = new JSONObject(tab.metadata);
			String type = metadata.getString("intentType");
			
			if (type.equals("Facebook")) {
				return FacebookIntent.build(context, metadata);
			} else if (type.equals("Web")) {
				return WebIntent.build(context, metadata);
			} else if (type.equals("Download")) {
				return DownloadIntent.build(context, metadata);
			} else {
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
