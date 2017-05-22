package edu.salleurl.ls30394.foodfinderapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.Adapter.RestaurantAdapter;
import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RestaurantsWebService;

/**
 * Created by avoge on 20/05/2017.
 */

public class RestaurantListFragment extends Fragment {

    private ListView listView;
    private List<Restaurante> list;
    private RestaurantAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        listView = (ListView) view.findViewById(R.id.result_restaurants_list);

        list = RestaurantsWebService.getInstance(getActivity()).getResult();

        adapter = new RestaurantAdapter(getActivity(), list, getArguments().getBoolean("onlyOpen"),
                getArguments().getString("username"));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);

        return view;
    }

    public RestaurantAdapter getListAdapter(){
        return adapter;
    }
}
