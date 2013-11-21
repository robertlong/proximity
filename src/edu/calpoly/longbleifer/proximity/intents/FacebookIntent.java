package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FacebookIntent {
	public static Intent build(Context context, JSONObject metadata) {
		
		try {
			String fbId = metadata.getString("facebookId");
			
			try {
				context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
				return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + fbId));
			} catch (Exception e) {
				return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + fbId));
			}
			
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
