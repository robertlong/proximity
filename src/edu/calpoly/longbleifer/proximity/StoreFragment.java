package edu.calpoly.longbleifer.proximity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoreFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
		
		Log.i("proximity", "Created Fragment.");
        
        return inflater.inflate(R.layout.store_fragment, container, false);
	}
}