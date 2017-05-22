package edu.salleurl.ls30394.foodfinderapp.model;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class Restaurante implements Serializable{

    public static final String TYPE_GENERIC = "Restaurante";
    public static final String TYPE_BURGER = "Hamburgueseria";
    public static final String TYPE_ITALIAN = "Italiano";
    public static final String TYPE_ASIAN = "Oriental";
    public static final String TYPE_MEXICAN = "Mejicano";
    public static final String TYPE_TAKEAWAY = "Take Away";

    private String name;
    private String type;
    private double latitude;
    private double longitude;
    private String address;
    private String openingTime; //HH:mm
    private String closingTime; //HH:mm
    private float review;
    private String description;

    public Restaurante(String name, String type, double lat, double lng, String address,
                       String openingTime, String closingTime, float review, String description) {
        this.name = name;
        this.type = type;
        this.latitude = lat;
        this.longitude = lng;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.address = address;
        this.review = review;
        this.description = description;
    }

    public boolean isOpen(){
        if(!openingTime.equals("null") && !closingTime.equals("null")) {
            Calendar now = Calendar.getInstance();
            int nowHour = now.get(Calendar.HOUR_OF_DAY);
            int nowMinute = now.get(Calendar.MINUTE);

            String[] openingTimeParts = openingTime.split(":");
            String[] closingTimeParts = closingTime.split(":");

            int     openingHour = Integer.parseInt(openingTimeParts[0]),
                    openingMinute = Integer.parseInt(openingTimeParts[1]),

                    closingHour = Integer.parseInt(closingTimeParts[0]),
                    closingMinute = Integer.parseInt(closingTimeParts[1]);

            boolean afterOpening = nowHour == openingHour
                    ? nowMinute >= openingMinute
                    : nowHour > openingHour;

            boolean beforeClosing = nowHour == closingHour
                    ? nowMinute < closingMinute
                    : nowHour < closingHour;

            Log.i(getClass().getName(), afterOpening && beforeClosing?"OPEN":"CLOSED");
            return afterOpening && beforeClosing;
        }
        return true;
    }

    //************************STANDARD GETTERS AND SETTERS****************************************//

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
