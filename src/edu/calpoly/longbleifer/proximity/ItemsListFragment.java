package edu.calpoly.longbleifer.proximity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ItemsListFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.items_list_fragment, container, false);

        return layout;
	}
}
