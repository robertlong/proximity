package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;

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
    static final int NUM_ITEMS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurant_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ActionBar bar = this.getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        ViewPager viewPager = (ViewPager) this.getActivity().findViewById(R.id.restaurant_pager);
        TabsAdapter mTabsAdapter = new TabsAdapter(this.getActivity(), viewPager);
        mTabsAdapter.addTab(bar.newTab().setText("Appetizers"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Entrees"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Desserts"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Wine"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Other"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("More"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Other"), ItemsListFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("More"), ItemsListFragment.class, null);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
            private final Context mContext;
            private final ActionBar mActionBar;
            private final ViewPager mViewPager;
            private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

            static final class TabInfo {
                private final Class<?> clss;
                private final Bundle args;

                TabInfo(Class<?> _class, Bundle _args) {
                    clss = _class;
                    args = _args;
                }
            }

            public TabsAdapter(Activity activity, ViewPager pager) {
                super(((FragmentActivity) activity).getSupportFragmentManager());
                mContext = activity;
                mActionBar = activity.getActionBar();
                mViewPager = pager;
                mViewPager.setAdapter(this);
                mViewPager.setOnPageChangeListener(this);
            }

            public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
                TabInfo info = new TabInfo(clss, args);
                tab.setTag(info);
                tab.setTabListener(this);
                mTabs.add(info);
                mActionBar.addTab(tab);
                notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                TabInfo info = mTabs.get(position);
                return Fragment.instantiate(mContext, info.clss.getName(), info.args);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                Object tag = tab.getTag();
                for (int i=0; i<mTabs.size(); i++) {
                    if (mTabs.get(i) == tag) {
                        mViewPager.setCurrentItem(i);
                    }
                }
            }

            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
    }
}