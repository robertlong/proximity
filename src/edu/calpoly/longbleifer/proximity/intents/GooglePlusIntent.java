package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GooglePlusIntent {
	public static Intent build(Context context, JSONObject metadata) {
		try {
			String googlePlusId = metadata.getString("googlePlusId");
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + googlePlusId));
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
