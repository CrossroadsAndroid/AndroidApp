package com.codepath.crossroads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.models.ReviewItem;
import com.codepath.crossroads.models.ReviewOffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyleung on 10/18/14.
 */
public class ReviewerOfferArrayAdapter  extends ArrayAdapter<ReviewOffer> {

    // view cache
    private static class ViewHolder {
        TextView    tvDonorName;
        TextView    tvDescription;
        ImageView   ivItem;
    }

    public ReviewerOfferArrayAdapter(Context context, List<ReviewOffer> offers) {
        super(context, 0, offers);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // take the data source
        // get data item
        ReviewOffer offer						= getItem(position);

        // use view holder pattern
        ViewHolder viewHolder;
        // Check if an existing view is being reused,
        // if it is new, inflate the view and set viewHolder as tag
        if (convertView == null) {
            viewHolder					= new ViewHolder();
            convertView 				= LayoutInflater.from(getContext()).inflate(R.layout.reviewer_offer_item, parent, false);
            viewHolder.tvDonorName		= (TextView) convertView.findViewById(R.id.tvDonorName);
            viewHolder.tvDescription    = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.ivItem           = (ImageView) convertView.findViewById(R.id.ivItem);

            convertView.setTag(viewHolder);
        }
        // retrieve view holder from tag
        else {
            viewHolder					= (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.tvDonorName.setText(offer.getDonor().getFirstName() + " " + offer.getDonor().getLastName());
        viewHolder.tvDescription.setText(Integer.valueOf(offer.getItems().size()) + " items submitted");
        viewHolder.ivItem.setImageResource(R.drawable.capture_pic);


        // set image as the first item
        ArrayList<ReviewItem> items = offer.getItems();
        if (null != items && items.size() > 0) {
            ReviewItem item = items.get(0);
            item.getImageView(viewHolder.ivItem);
            viewHolder.ivItem.setTag(item.getParseID());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}