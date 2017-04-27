package edu.salleurl.ls30394.foodfinderapp.model;




public class Restaurante {
    private String name;
    private String localization;
    private String image_restaurant; // he pensado que aquí podríamos guardar el directiorio de la
                                    // imagen, ya que en la base de datos no se pueden guardar imagenes
    private int rating;
    private String description;
    private String[] comments;

    public Restaurante(String name, String localization, String image_restaurant, int rating,
                       String description, String[] comments) {
        this.name = name;
        this.localization = localization;
        this.image_restaurant = image_restaurant;
        this.rating = rating;
        this.description = description;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getImage_restaurant() {
        return image_restaurant;
    }

    public void setImage_restaurant(String image_restaurant) {
        this.image_restaurant = image_restaurant;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }
}
