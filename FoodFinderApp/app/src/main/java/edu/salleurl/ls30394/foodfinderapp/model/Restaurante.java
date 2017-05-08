package edu.salleurl.ls30394.foodfinderapp.model;




public class Restaurante {
    private String name;
    private String imageURI; // he pensado que aquí podríamos guardar el directiorio de la
                                    // imagen, ya que en la base de datos no se pueden guardar imagenes

    private double latitude;
    private double longitude;

    private int rating;
    private String description;
    private String[] comments;

    private Restaurante(String name, double lat, double lng, String imageURI, int rating,
                       String description, String[] comments) {
        this.name = name;
        this.imageURI = imageURI;
        this.latitude = lat;
        this.longitude = lng;
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

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
