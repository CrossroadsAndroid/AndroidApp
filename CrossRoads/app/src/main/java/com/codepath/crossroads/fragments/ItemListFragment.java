package com.codepath.crossroads.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.adapters.ItemListArrayAdapter;
import com.codepath.crossroads.models.Item;

import java.util.ArrayList;

/**
 * Created by tonyleung on 10/12/14.
 */
public class ItemListFragment extends Fragment {

    protected ArrayAdapter<Item> aItems;
    protected ListView lvItems;
    protected ArrayList<Item> items;

    /**
     * defining an offer selected listener
     */
    private OnItemSelectedListener listener;

    // must handle on clickOffer
    public interface OnItemSelectedListener {
        public void didClickItem(Item item);
    }

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static ItemListFragment newInstance(ArrayList<Item> items) {
        ItemListFragment itemListFragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("items", items);
        itemListFragment.setArguments(args);
        return itemListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        items		= getArguments().getParcelableArrayList("items");
        aItems		= new ItemListArrayAdapter(getActivity(), items);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ReviewerOfferFragmentList.OnOfferSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout
        View view	= inflater.inflate(R.layout.fragment_item_list, container, false);
        // assign view references
        lvItems	= (ListView) view.findViewById(R.id.lvItems);

        lvItems.setAdapter(aItems);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.didClickItem(items.get(i));
            }
        });

        return view;
    }

    /**
     * add the given array list of tweets to the array adapter
     * @param items
     */
    public void addAll(ArrayList<Item> items) {
        aItems.addAll(items);
    }

    /**
     * remove all the tweets
     */
    public void removeAll() {
        aItems.clear();
        aItems.notifyDataSetChanged();
    }

}
