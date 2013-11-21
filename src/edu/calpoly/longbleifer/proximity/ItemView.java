package edu.calpoly.longbleifer.proximity;

import com.squareup.picasso.Picasso;

import edu.calpoly.longbleifer.proximity.models.RestaurantItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {
	
	private RestaurantItem item;
	
	public ItemView(Context context, RestaurantItem item) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_view, this, true);
		
		this.item = item;
		this.setItem(item);
	}
	
	public void setItem(RestaurantItem item) {
		this.item = item;
		
		TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
		itemTitleView.setText(item.name);
		
		TextView itemPriceView = (TextView) findViewById(R.id.itemPrice);
		itemPriceView.setText("$" + Double.toString(item.price));
		
		if (item.description != null) {
			TextView itemDescriptionView = (TextView) findViewById(R.id.itemDescription);
			itemDescriptionView.setText(item.description);
		}
		
		if (item.image != null) {
			ImageView imageView = (ImageView) findViewById(R.id.image);
			Picasso.with(this.getContext()).load(item.image).into(imageView);
		}
	}
}
