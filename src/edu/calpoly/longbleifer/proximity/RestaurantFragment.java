package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import edu.calpoly.longbleifer.proximity.models.RestaurantItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RestaurantFragment extends Fragment {
	
	private HashMap<String, ArrayList<RestaurantItem>> itemsByCategory;
	private edu.calpoly.longbleifer.proximity.models.Tab tab;
	private ViewPager viewPager;
	private TabsAdapter tabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
        return inflater.inflate(R.layout.restaurant_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
    	int position = this.getArguments().getInt("id");
		this.tab = ProximityActivity.trigger.tabs().get(position);
        
        this.itemsByCategory = new HashMap<String, ArrayList<RestaurantItem>>();
        this.parseItems();
        
        this.viewPager = (ViewPager) this.getActivity().findViewById(R.id.restaurant_pager);
        this.tabsAdapter = new TabsAdapter(this.getFragmentManager(), itemsByCategory);
        this.viewPager.setAdapter(tabsAdapter);
        
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) this.getActivity().findViewById(R.id.tabs);
        Log.i("Proximity", this.viewPager.toString());
        tabs.setViewPager(this.viewPager);
        
     
    }
    
    private void parseItems() {
    	try {
			JSONObject metadata = new JSONObject(this.tab.metadata);
			Iterator<?> categories = metadata.keys();

	        while( categories.hasNext() ){
	            String category = (String)categories.next();
	            if(metadata.get(category) instanceof JSONArray ){
	            	ArrayList<RestaurantItem> itemArray = new ArrayList<RestaurantItem>();
	            	JSONArray items = (JSONArray) metadata.get(category);
	            	for (int i = 0; i < items.length(); i++) {
	            		JSONObject properties = items.getJSONObject(i);
	            		
	            		if (properties.has("name") && properties.has("price")) {
		            		RestaurantItem item = new RestaurantItem(properties.getString("name"), properties.getDouble("price"));
		            		
		            		if (properties.has("description")) {
		            			item.description = properties.getString("description");
		            		}
		            		
		            		if (properties.has("image")) {
		            			item.image = properties.getString("image");
		            		}
		            		
		            		itemArray.add(item);
		            	}
	            	}
	            	
	            	this.itemsByCategory.put(category, itemArray);
	            }
	        }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    public class TabsAdapter extends FragmentStatePagerAdapter {
    	private final HashMap<String, ArrayList<RestaurantItem>> itemsByCategory;
    	private final ArrayList<String> categories;

        public TabsAdapter(FragmentManager fm, HashMap<String, ArrayList<RestaurantItem>> itemsByCategory) {
        	super(fm);
        	this.itemsByCategory = itemsByCategory;	
        	this.categories = new ArrayList<String>(itemsByCategory.keySet());
        }

        @Override
        public CharSequence getPageTitle(int position) {
                return categories.get(position);
        }

        @Override
        public int getCount() {
        	return this.itemsByCategory.size();
        }

        @Override
        public Fragment getItem(int position) {
        	String category = categories.get(position);
            ArrayList<RestaurantItem> items = this.itemsByCategory.get(category);
            ItemsListFragment itemsListFragment = new ItemsListFragment();
            Log.i("Proximity", "GetItem items: " + items.size());
            itemsListFragment.setItems(items);
            return itemsListFragment;
        }
        
    }
}