package edu.calpoly.longbleifer.proximity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BeaconServiceReceiver extends BroadcastReceiver 
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent();
            i.setAction("edu.calpoly.longbleifer.proximity.BeaconService");
            context.startService(i);
        }
    }
}