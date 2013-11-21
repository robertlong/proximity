package edu.calpoly.longbleifer.proximity.intents;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class DownloadIntent {
	public static Intent build(Context context, JSONObject metadata) {
		try {
			String url = metadata.getString("url");
			String fileName = metadata.getString("fileName");
			Uri uri = Uri.parse(url);
			DownloadManager.Request r = new DownloadManager.Request(uri);

			// This put the download in the same Download dir the browser uses
			r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

			// When downloading music and videos they will be listed in the player
			// (Seems to be available since Honeycomb only)
			r.allowScanningByMediaScanner();

			// Notify user when download is completed
			// (Seems to be available since Honeycomb only)
			r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

			// Start download
			DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
			dm.enqueue(r);
			return null;
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

