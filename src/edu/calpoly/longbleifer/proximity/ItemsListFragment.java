package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;

import edu.calpoly.longbleifer.proximity.models.RestaurantItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ItemsListFragment extends Fragment {
	
	private ArrayList<RestaurantItem> items;
	private ListView listView;
	private LinearLayout layout;
	
	public void setItems(ArrayList<RestaurantItem> items) {
		this.items = items;
		Log.i("Proximity", "Set items");
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        this.layout = (LinearLayout) inflater.inflate(R.layout.items_list_fragment, container, false);
        this.listView = (ListView) layout.findViewById(R.id.listview);
        this.listView.setAdapter(new RestaurantItemArrayAdapter(this.getActivity(), items));
        
        return this.layout;
	}
}
