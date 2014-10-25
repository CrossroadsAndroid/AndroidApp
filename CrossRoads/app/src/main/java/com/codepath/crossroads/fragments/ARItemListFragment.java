package com.codepath.crossroads.fragments;

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
import com.codepath.crossroads.models.DonorItem;
import com.codepath.crossroads.models.DonorOffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ar on 10/19/14.
 */
public class ARItemListFragment extends Fragment {
    ArrayList<DonorItem> items;
    // ItemListAdapterOld aItems;
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
        // aItems = new ItemListAdapterOld(getActivity(), items);
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
