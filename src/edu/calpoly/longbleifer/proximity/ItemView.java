package edu.calpoly.longbleifer.proximity;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {
	
	public ItemView(Context context, String itemTitle, double price, String itemDescription) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.item_view, this, true);
		
		TextView itemTitleView = (TextView) findViewById(R.id.itemTitle);
		itemTitleView.setText(itemTitle);
		
		TextView itemPriceView = (TextView) findViewById(R.id.itemPrice);
		itemPriceView.setText(Double.toString(price));
		
		TextView itemDescriptionView = (TextView) findViewById(R.id.itemDescription);
		itemDescriptionView.setText(itemDescription);
	}
}
