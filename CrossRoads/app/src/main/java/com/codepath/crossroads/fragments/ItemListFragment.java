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


// --- 

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.crossroads.R;
import com.codepath.crossroads.activities.donors.AddItemActivity;
import com.codepath.crossroads.adapters.ItemListAdapter;
import com.codepath.crossroads.models.DonorItem;
import com.codepath.crossroads.models.DonorOffer;

import java.util.ArrayList;
import java.util.List;


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

public class ARItemListFragment extends Fragment {
    ArrayList<DonorItem> items;
    ItemListAdapter aItems;
    private DonorOffer offer;

    public ARItemListFragment() {
        // Required empty public constructor
    }

    public static ARItemListFragment newInstance(List<DonorItem> items) {
        ARItemListFragment fragment = new ARItemListFragment();
        Bundle args = new Bundle();
        args.putParcelableArray("items", items.toArray(new DonorItem[0]));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<DonorItem>();
        aItems = new ItemListAdapter(getActivity(), items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_item_list, container, false);
        ListView lvItems = (ListView) v.findViewById(R.id.lvItems);
        lvItems.setAdapter(aItems);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                DonorItem item = aItems.getItem(pos);
                Intent i = new Intent(getActivity(), AddItemActivity.class);
                i.putExtra("item", item);
                i.putExtra("pos", pos);
                getActivity().startActivityForResult(i, 1010);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void updateItem(DonorItem i, int pos) {
        if (pos < 0) {
            items.add(i);
        } else {
            items.set(pos, i);
        }
        aItems.notifyDataSetChanged();

        if (offer == null) {
            offer = new DonorOffer(items);
        }
    }

    public DonorOffer getOffer() {
        return offer;
    }

    public void setOffer(DonorOffer offer) {
        this.offer = offer;
        this.items = offer.getItems();
    }
}
