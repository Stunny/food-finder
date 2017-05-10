package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.R;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;

/**
 * Created by avoge on 29/04/2017.
 */

public class RecentSearchAdapter extends BaseAdapter {
    private Context context;
    private List<Restaurante> recent;
    /*
        La lista tiene que ser la lista de busquedas recientes, la pregunta es si la de recent
        search, se guarda el Restaurante o el nombre nada mas.
        Si es la segunda se debe modificar poco.
     */
    public RecentSearchAdapter (Context context,List<Restaurante> recent){
        this.context = context;
        this.recent = recent;
    }
    @Override
    public int getCount() {
        return recent.size();
    }

    @Override
    public Object getItem(int position) {
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

        View itemView  = inflater.inflate(R.layout.layout_favorite_listview,parent,false);

        Restaurante restaurante = recent.get(position);

        TextView textView = (TextView) itemView.findViewById(R.id.textViewFavorite);
        textView.setText(restaurante.getName());


        return itemView;
    }
}
