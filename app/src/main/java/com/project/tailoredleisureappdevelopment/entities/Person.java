package com.project.tailoredleisureappdevelopment.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private int person_id;
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    private String role;

    private ArrayList<Integer> userNeeds = new ArrayList<>();

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", role='" + role + '\'' +
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

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
