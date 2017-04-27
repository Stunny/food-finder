package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.R;


public class RestaurantListViewAdapter extends BaseAdapter
        implements AdapterView.OnItemClickListener {
    private Context context;
    private List<Restaurante> restaurantes;

    public RestaurantListViewAdapter(Context context,List<Restaurante> restaurantes){
        this.context = context;
        this.restaurantes = restaurantes;
    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_restaurant_listview,parent,false);

        Restaurante restaurante = restaurantes.get(position);

        TextView nameRestaurant = (TextView)itemView.findViewById(R.id.name_restaurant);
        nameRestaurant.setText(restaurante.getName());

        TextView localization = (TextView) itemView.findViewById(R.id.localization_restaurant);
        localization.setText(restaurante.getLocalization());

        RatingBar ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        ratingBar.setRating(restaurante.getRating());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.list_restaurant_image);
        //imageView.setImageResource(restaurante.getImage_restaurant());
        //TODO: poner la imagen en el adapter

        return itemView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
