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
import edu.salleurl.ls30394.foodfinderapp.repositories.RecentSearchesRepo;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.RecentSearchesDB;
import edu.salleurl.ls30394.foodfinderapp.repositories.impl.UserDatabase;

/**
 * Created by avoge on 29/04/2017.
 */

public class RecentSearchAdapter extends ArrayAdapter<String> {

    private Context context;
    private int userId;

    private RecentSearchesRepo db;

    private List<String> list;

    public RecentSearchAdapter (Context context, String userName){
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;

        db = new RecentSearchesDB(context);

        getUserId(userName);
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

        TextView textView = (TextView) row.findViewById(android.R.id.text1);
        textView.setText(recent_search);

        return row;
    }

    /**
     *
     * @param userName
     */
    private void getUserId(String userName){
        UserDatabase udb = new UserDatabase(context);
        userId = udb.getUserId(userName);
    }

    /**
     *
     */
    private void populateList() {
        list = db.getRecentSearches(userId);
    }

    public void updateList(){
        populateList();
        notifyDataSetChanged();
    }

    /**
     * @param searchQuery
     */
    public void addRecentSearch(String searchQuery){
        if(db.exists(userId, searchQuery))
            db.removeRecentSearch(userId, searchQuery);

        db.addRecentSearch(userId, searchQuery);
        updateList();
    }
}
