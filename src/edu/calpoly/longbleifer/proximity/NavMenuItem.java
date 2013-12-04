package edu.calpoly.longbleifer.proximity;

import android.content.Context;

public class NavMenuItem implements NavDrawerItem {

    public static final int ITEM_TYPE = 1 ;
    public static final int NO_ICON = -1 ;

    private int id ;
    private String label ;  
    private int icon ;
    private boolean updateActionBarTitle ;
    
    public NavMenuItem( int id, String label, boolean updateActionBarTitle, Context context ) {
        this.setId(id);
        this.setLabel(label);
        this.setIcon(NO_ICON);
        this.setUpdateActionBarTitle(updateActionBarTitle);
    }

    public NavMenuItem ( int id, String label, String icon, boolean updateActionBarTitle, Context context ) {
        this.setId(id);
        this.setLabel(label);
        this.setIcon(context.getResources().getIdentifier( icon, "drawable", context.getPackageName()));
        this.setUpdateActionBarTitle(updateActionBarTitle);
    }
    
    @Override
    public int getType() {
        return ITEM_TYPE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean updateActionBarTitle() {
        return this.updateActionBarTitle;
    }

    public void setUpdateActionBarTitle(boolean updateActionBarTitle) {
        this.updateActionBarTitle = updateActionBarTitle;
    }
}