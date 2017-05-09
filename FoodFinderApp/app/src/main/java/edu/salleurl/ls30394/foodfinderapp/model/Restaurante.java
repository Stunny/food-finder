package edu.salleurl.ls30394.foodfinderapp.model;




public class Restaurante {
    private String name;
    private String imageURI; // he pensado que aquí podríamos guardar el directiorio de la
                                    // imagen, ya que en la base de datos no se pueden guardar imagenes
    private String address;
    private double latitude;
    private double longitude;

    private float rating;
    private String description;
    private String[] comments;

    private String openingTime; //HH:mm
    private String closingTime; //HH:mm

    private Restaurante(String name, double lat, double lng, String imageURI, float rating,
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
