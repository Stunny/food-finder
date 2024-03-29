package edu.salleurl.ls30394.foodfinderapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.salleurl.ls30394.foodfinderapp.activities.DescriptionActivity;
import edu.salleurl.ls30394.foodfinderapp.model.Restaurante;
import edu.salleurl.ls30394.foodfinderapp.R;


public class RestaurantAdapter extends ArrayAdapter<Restaurante>
        implements AdapterView.OnItemClickListener {

    private Context context;
    private String userName;

    private List<Restaurante> allResultRestaurants;
    private List<Restaurante> activeList;
    private String[] restaurantTypes;

    private boolean showOnlyOpen;

    /**
     *
     * @param context
     * @param allResultRestaurants
     */
    public RestaurantAdapter(Context context, List<Restaurante> allResultRestaurants,
                             boolean showOnlyOpen, String userName){
        super(context, R.layout.row_restaurant);
        this.context = context;
        this.showOnlyOpen = showOnlyOpen;
        this.userName = userName;
        this.allResultRestaurants = new ArrayList<>(allResultRestaurants);
        this.restaurantTypes = setRestaurantTypes();

        if(this.showOnlyOpen){
            removeClosedRestaurants();
        }
        this.activeList = new ArrayList<>(allResultRestaurants);
    }

    @Override
    public int getCount() {
        return activeList.size();
    }

    @Override
    public Restaurante getItem(int position) {
        return activeList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;

        if(row == null) {

            LayoutInflater inflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.row_restaurant, parent, false);
        }

        Restaurante restaurante = activeList.get(position);

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
        Intent intent ;
        intent = new Intent(this.context, DescriptionActivity.class);

        Restaurante aux = activeList.get(position);

        intent.putExtra("restaurant", aux);
        intent.putExtra("username", userName);

        this.context.startActivity(intent);

    }

    /**
     * Asocia un icono de tipo de restaurante a la vista del mismo
     * @param type Tipo de restaurante
     * @param imageView View del icono de restaurante
     */
    private void setImageRestaurant(String type, ImageView imageView){

        switch (type){
            case Restaurante.TYPE_ITALIAN:
                imageView.setImageResource(R.drawable.italian);
                break;
            case Restaurante.TYPE_MEXICAN:
                imageView.setImageResource(R.drawable.mexicano);
                break;
            case Restaurante.TYPE_ASIAN:
                imageView.setImageResource(R.drawable.japones);
                break;
            case Restaurante.TYPE_BURGER :
                imageView.setImageResource(R.drawable.hamburguesa);
                break;
            case Restaurante.TYPE_TAKEAWAY :
                imageView.setImageResource(R.drawable.takeaway);
            default:
                imageView.setImageResource(R.drawable.restaurante);
                break;
        }

    }

    /**
     * Filtra los restaurants mostrados en la lista segun el tipo
     * @param type Tipo de restaurante
     * @param all True si se desean todos los restaurantes resultantes
     */
    public void filterRestaurants(boolean all, String type){
        if(all){
            activeList = new ArrayList<>(allResultRestaurants);
        }else{
            changeSet(type);
        }
        notifyDataSetChanged();
    }

    /**
     * Cambia el conjunto de restaurantes mostrado segun su tipo
     * @param type Tipo de restaurante por el que filtrar
     */
    private void changeSet(String type){
        activeList = new ArrayList<>();
        int size = allResultRestaurants.size();

        Restaurante aux;
        for(int i = 0; i < size; i++){
            aux = allResultRestaurants.get(i);

            if(type.contains(aux.getType())){
                activeList.add(aux);
            }
        }

        clear();
        addAll(activeList);
    }

    /**
     *
     */
    private void removeClosedRestaurants(){
        int size = allResultRestaurants.size();
        ArrayList<Restaurante> toBeHidden = new ArrayList<>();
        Restaurante aux;

        for(int i = 0; i < size; i++){
            aux = allResultRestaurants.get(i);
            if(aux != null && !aux.isOpen()){
                toBeHidden.add(aux);
            }
        }

        for(Restaurante r : toBeHidden){
            if(allResultRestaurants.remove(r))
                Log.i("DELETED", r.getName());
        }
    }

    private String[] setRestaurantTypes(){
        int size = allResultRestaurants.size();

        List<String> auxTypes = new ArrayList<>();
        auxTypes.add(0, context.getString(R.string.all));

        int typesSize;

        Restaurante auxRest;
        boolean typeFound = false;

        for(int i = 0; i < size; i++){

            auxRest = allResultRestaurants.get(i);

            if (auxTypes.size() == 0){
                auxTypes.add(auxRest.getType());
            } else {
                typesSize = auxTypes.size();

                for(int j = 0; j < typesSize; j++){
                    if(auxRest.getType().equals(auxTypes.get(j))){
                        typeFound = true;
                    }
                }

                if(!typeFound){
                    auxTypes.add(auxRest.getType());
                }
            }
        }
        return auxTypes.toArray(new String[0]);
    }

    public String[] getRestaurantTypes(){return restaurantTypes;}
}
