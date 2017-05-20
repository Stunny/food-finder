package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;

/**
 * Created by avoge on 29/04/2017.
 */

public class RecentSearchAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> list;

    public RecentSearchAdapter (Context context){
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;
        populateList();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;

        if(row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        String recent_search = list.get(position);

        TextView textView = (TextView) row.findViewById(R.id.textViewFavorite);
        textView.setText(recent_search);

        return row;
    }

    private void populateList() {

    }
}
