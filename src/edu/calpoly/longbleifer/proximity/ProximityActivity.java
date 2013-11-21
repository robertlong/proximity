package edu.calpoly.longbleifer.proximity;

import java.util.List;

import edu.calpoly.longbleifer.proximity.intents.IntentBuilder;
import edu.calpoly.longbleifer.proximity.models.Tab;
import edu.calpoly.longbleifer.proximity.models.Trigger;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProximityActivity extends FragmentActivity {
	
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence title;
    public static Trigger trigger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proximity);
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        
        trigger = null;
        
        Intent intent = this.getIntent();
        
        if (intent != null) {
        	long triggerId = intent.getExtras().getLong("trigger-id", -1);
        	
        	if (triggerId != -1){
        		trigger = Trigger.find(triggerId);
        	}
        		
        }
        
        if (trigger == null) {
        	Intent historyActivity = new Intent(this, HistoryActivity.class);
        	historyActivity.setFlags(historyActivity.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        	this.startActivity(historyActivity);
        }
        
        this.title = trigger.name;
        
        setupDrawer();
        
        if (savedInstanceState == null) {
        	setMainContent(0);
        }
        
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

	
	@Override
    protected void onDestroy() {
        super.onDestroy();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.proximity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        // boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
	
	private void setupDrawer() {
        // set up the drawer's list view with items and click listener
        drawerList.setAdapter(new ArrayAdapter<Tab>(this, R.layout.drawer_list_item, trigger.tabs()));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setMainContent(position);
        }
    }
	
	private void setMainContent(int position) {
		Fragment fragment = null;
		Bundle args = new Bundle();
		List<Tab> tabs = trigger.tabs();
		final ActionBar bar = getActionBar();
		boolean updateSelectedItem = true;
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
        if (position <= tabs.size() - 1) {
        	Tab tab = tabs.get(position);
        	String type = tab.type;
        	args.putInt("id", position);
        	
        	if (type.equals("Info")) {
    			fragment = new InfoFragment();
    			fragment.setArguments(args);
    		}
    		else if (type.equals("Restaurant")) {
    			fragment = new RestaurantFragment();
    			fragment.setArguments(args);
    		}
    		else if (type.equals("Store")) {
    			fragment = new StoreFragment();
    			fragment.setArguments(args);
    		} 
    		else if (type.equals("Intent")) {
    			Intent intent = IntentBuilder.build(this, tab);
    			if (intent != null) {
    				this.startActivity(intent);
    				//When we come we come back to the activity we don't want the intent menu item to be selected.
    				updateSelectedItem = false; 
    			}
    		}
    		
    		if (fragment != null) {
    			FragmentManager fragmentManager = this.getSupportFragmentManager();
    			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    		}

    		if (updateSelectedItem) {
    			drawerList.setItemChecked(position, true);
    			setTitle(tab.title);
    		}
            
        }
		
        drawerLayout.closeDrawer(drawerList);
    }
	
	@Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getActionBar().setTitle(title);
    }
}
