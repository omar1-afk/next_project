package org.noteam.nextclient.models;

import java.util.Date;

public class Driver {
    private int driverId;
    private String image;
    private String name;
    private int age;
    private String socialSecurityNumber;
    private String email;
    private String password;
    private boolean isBusy;

    public Driver(
int driverId, String name, String image, int age, String socialSecurityNumber, String email, String password, boolean isBusy

    ) {
        this.driverId = driverId;
        this.image = image;
        this.name = name;
        this.age = age;
        this.socialSecurityNumber = socialSecurityNumber;
        this.email = email;
        this.password = password;
        this.isBusy = isBusy;
    }


            public int getDriverId()
    {
        return driverId;
    }
    public void setDriverId(int driverId)
    {
        this.driverId = driverId;
    }
    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public String getSocialSecurityNumber()
    {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }



    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
