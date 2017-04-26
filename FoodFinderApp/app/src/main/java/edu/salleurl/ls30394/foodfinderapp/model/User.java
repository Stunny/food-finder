package edu.salleurl.ls30394.foodfinderapp.model;

import android.media.Image;



public class User {
    private String  userName;
    private String  userSurname;
    private String  userMail;
    private String  userPassword;
    private boolean male;
    private boolean female;
    private String userDescription;
    private Image userImage;


    public User(String userName, String userSurname, String userMail, String userPassword,
                boolean male, boolean female, String userDescription,Image userImage) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.male = male;
        this.female = female;
        this.userDescription = userDescription;
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

}
