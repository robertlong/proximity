package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FoursquareIntent {
	public static Intent build(Context context, JSONObject metadata) {
		try {
			String venueId = metadata.getString("venueId");
			return new Intent(Intent.ACTION_VIEW, Uri.parse("http://foursquare.com/venue/" + venueId));
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}