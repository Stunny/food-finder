package edu.salleurl.ls30394.foodfinderapp.model;




public class Restaurante {
    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private String address;
    private String openingTime; //HH:mm
    private String closingTime; //HH:mm
    private float review;
    private String description;



    public Restaurante(String name,String type, double lat, double lng, String address,
                       String openingTime, String closingTime, float review, String description) {
        this.name = name;
        this.type = type;
        this.latitude = lat;
        this.longitude = lng;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.review = review;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
