package com.project.tailoredleisureappdevelopment.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String number;

    private ArrayList<Integer> userNeeds = new ArrayList<>();

    public Person() {

    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", userNeeds=" + userNeeds +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Integer> getUserNeeds() {
        return userNeeds;
    }

    public void setUserNeeds(ArrayList<Integer> userNeeds) {
        this.userNeeds = userNeeds;
    }
}
