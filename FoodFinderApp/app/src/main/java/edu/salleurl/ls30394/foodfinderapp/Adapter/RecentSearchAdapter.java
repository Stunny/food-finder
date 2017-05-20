package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;

/**
 * Created by avoge on 29/04/2017.
 */

public class RecentSearchAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> recent;

    public RecentSearchAdapter (Context context,List<String> recent){
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;
        this.recent = recent;
    }
    @Override
    public int getCount() {
        return recent.size();
    }

    @Override
    public String getItem(int position) {
        return recent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView  = inflater.inflate(R.layout.row_favorite,parent,false);

        String recent_search = recent.get(position);

        TextView textView = (TextView) itemView.findViewById(R.id.textViewFavorite);
        textView.setText(recent_search);


        return itemView;
    }
}
