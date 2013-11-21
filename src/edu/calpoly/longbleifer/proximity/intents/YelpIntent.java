package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class YelpIntent {
	public static Intent build(Context context, JSONObject metadata) {
		try {
			String yelpId = metadata.getString("yelpId");
			return new Intent(Intent.ACTION_VIEW, Uri.parse("http://mobile.yelp.com/biz/" + yelpId));
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
