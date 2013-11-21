package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TwitterIntent {
	public static Intent build(Context context, JSONObject metadata) {
		try {
			Intent intent;
			String twitterId = metadata.getString("twitterId");
			try {
			    // get the Twitter app if possible
			    context.getPackageManager().getPackageInfo("com.twitter.android", 0);
			    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + twitterId));
			    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			} catch (Exception e) {
			    // no Twitter app, revert to browser
			    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + twitterId));
			}
			return intent;
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
