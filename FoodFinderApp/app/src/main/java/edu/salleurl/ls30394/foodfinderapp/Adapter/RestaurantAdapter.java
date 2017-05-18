package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.R;


public class RestaurantAdapter extends ArrayAdapter<Restaurante>
        implements AdapterView.OnItemClickListener {

    private Context context;
    private List<Restaurante> restaurantes;

    public RestaurantAdapter(Context context, List<Restaurante> restaurantes){
        super(context, R.layout.row_restaurant);
        this.context = context;
        this.restaurantes = restaurantes;
    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Restaurante getItem(int position) {
        return restaurantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;

        if(row == null) {

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.row_restaurant, null);
        }

        Restaurante restaurante = restaurantes.get(position);

        TextView nameRestaurant = (TextView)row.findViewById(R.id.name_restaurant);
        nameRestaurant.setText(restaurante.getName());

        TextView localization = (TextView) row.findViewById(R.id.localization_restaurant);
        localization.setText(restaurante.getAddress());

        RatingBar ratingBar = (RatingBar) row.findViewById(R.id.ratingBar);
        ratingBar.setRating(restaurante.getReview());

        ImageView imageView = (ImageView) row.findViewById(R.id.list_restaurant_image);

        setImageRestaurant(restaurante.getType(),imageView);

        return row;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private void setImageRestaurant(String type, ImageView imageView){

        switch (type){
            case "Italiano":
                imageView.setImageResource(R.drawable.italian);
                break;
            case "Mejicano":
                imageView.setImageResource(R.drawable.italian);
                break;
            case "Oriental":
                imageView.setImageResource(R.drawable.japones);
                break;
           /* case "Restaurante":
                imageView.setImageResource(R.drawable.restaurante);
                break;*/
            case "Hamburgueseria" :
                imageView.setImageResource(R.drawable.hamburguesa);
                break;
            case "Take Away" :
                imageView.setImageResource(R.drawable.takeaway);
            default:
                imageView.setImageResource(R.drawable.restaurante);
                break;
        }

    }
}
