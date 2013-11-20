package edu.calpoly.longbleifer.proximity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import edu.calpoly.longbleifer.proximity.models.RestaurantItem;

public class RestaurantItemArrayAdapter extends BaseAdapter {

	private Context context;

	/** The data set to which this JokeListAdapter is bound. */
	private ArrayList<RestaurantItem> items;

	public RestaurantItemArrayAdapter(Context context, ArrayList<RestaurantItem> items) {
		this.context = context;
        this.items = items;
	}

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemView itemView;
        RestaurantItem item = this.items.get(position);

        if (convertView == null) {
            itemView = new ItemView(this.context, item);
        } else {
            itemView = (ItemView) convertView;
        }

        itemView.setItem(item);

        return itemView;
	}
}