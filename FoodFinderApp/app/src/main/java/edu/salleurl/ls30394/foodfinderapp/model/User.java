package edu.salleurl.ls30394.foodfinderapp.model;

import android.graphics.Bitmap;
import android.media.Image;



public class User {
    private String  userName;
    private String  userSurname;
    private String  userMail;
    private String  userPassword;
    private int genderIndex;
    private String userDescription;
    private Bitmap userImage;


    public User(String userName, String userSurname, String userMail, String userPassword,
                int genderIndex, String userDescription, Bitmap userImage) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.genderIndex = genderIndex;
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

    public int getGenderIndex() {
        return genderIndex;
    }

    public void setGenderIndex(int genderIndex) {
        this.genderIndex = genderIndex;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

}
