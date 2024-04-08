package com.project.tailoredleisureappdevelopment.entities;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import java.io.Serializable;
import java.util.ArrayList;
/*
This is a entity class for Person table.
This is similar to an pojo class in java.
 */
public class Person implements Serializable {

    private int person_id;
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    private String role;

    private ArrayList<String> userNeeds = new ArrayList<>();

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

    public ArrayList<String> getUserNeeds() {
        return userNeeds;
    }

    public void setUserNeeds(ArrayList<String> userNeeds) {
        this.userNeeds = userNeeds;
    }
}
