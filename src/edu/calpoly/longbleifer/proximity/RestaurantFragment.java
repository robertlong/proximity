package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.calpoly.longbleifer.proximity.models.RestaurantItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RestaurantFragment extends Fragment {
	
	private HashMap<String, ArrayList<RestaurantItem>> itemsByCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurant_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        itemsByCategory = new HashMap<String, ArrayList<RestaurantItem>>();
        ArrayList<RestaurantItem> items = new ArrayList<RestaurantItem>();
        ArrayList<RestaurantItem> items2 = new ArrayList<RestaurantItem>();
        items.add(new RestaurantItem("Test Item", 1.00));
        items2.add(new RestaurantItem("Test Item1", 1.00));
        itemsByCategory.put("test", items);
        itemsByCategory.put("Test 2", items2);

        final ActionBar bar = this.getActivity().getActionBar();
        bar.removeAllTabs();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        ViewPager viewPager = (ViewPager) this.getActivity().findViewById(R.id.restaurant_pager);
        TabsAdapter mTabsAdapter = new TabsAdapter(this.getActivity(), viewPager, itemsByCategory);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
            private final Context context;
            private final ActionBar actionBar;
            private final ViewPager viewPager;
            private final HashMap<String, ArrayList<RestaurantItem>> itemsByCategory;

            public TabsAdapter(Activity activity, ViewPager pager, HashMap<String, ArrayList<RestaurantItem>> itemsByCategory) {
                super(((FragmentActivity) activity).getSupportFragmentManager());
                this.itemsByCategory = itemsByCategory;
                this.context = activity;
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
                return new ItemsListFragment();
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