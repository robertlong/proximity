package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.calpoly.longbleifer.proximity.models.RestaurantItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
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
        
        if (this.tabsAdapter == null) {
        	int position = this.getArguments().getInt("id");
    		this.tab = ProximityActivity.trigger.tabs().get(position);
            
            this.itemsByCategory = new HashMap<String, ArrayList<RestaurantItem>>();
            this.parseItems();
            
            final ActionBar bar = this.getActivity().getActionBar();
            bar.removeAllTabs();
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
            
            this.viewPager = (ViewPager) this.getActivity().findViewById(R.id.restaurant_pager);
            this.tabsAdapter = new TabsAdapter(this.getActivity(), viewPager, itemsByCategory);
        
            if (savedInstanceState != null) {
                bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
            }
        }
        
        this.viewPager.setAdapter(tabsAdapter);
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

    public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
            private final ActionBar actionBar;
            private final ViewPager viewPager;
            private final HashMap<String, ArrayList<RestaurantItem>> itemsByCategory;

            public TabsAdapter(Activity activity, ViewPager pager, HashMap<String, ArrayList<RestaurantItem>> itemsByCategory) {
                super(((FragmentActivity) activity).getSupportFragmentManager());
                this.itemsByCategory = itemsByCategory;
                this.actionBar = activity.getActionBar();
                this.viewPager = pager;
                this.viewPager.setAdapter(this);
                this.viewPager.setOnPageChangeListener(this);
                ArrayList<String> categories = new ArrayList<String>(this.itemsByCategory.keySet());
                for (String category : categories) {
                	this.addTab(category);
                }
            }

            public void addTab(String category) {
            	Tab tab = this.actionBar.newTab().setText(category);
                tab.setTabListener(this);
                actionBar.addTab(tab);
                notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return this.itemsByCategory.size();
            }

            @Override
            public Fragment getItem(int position) {
            	ArrayList<String> categories = new ArrayList<String>(this.itemsByCategory.keySet());
            	String category = categories.get(position);
                ArrayList<RestaurantItem> items = this.itemsByCategory.get(category);
                ItemsListFragment itemsListFragment = new ItemsListFragment();
                Log.i("Proximity", "GetItem items: " + items.size());
                itemsListFragment.setItems(items);
                return itemsListFragment;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
            	viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
    }
}