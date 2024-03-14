package com.project.tailoredleisureappdevelopment.models;

import android.util.Log;

import com.project.tailoredleisureappdevelopment.entities.Need;
import com.project.tailoredleisureappdevelopment.entities.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonModel {

    public static Person personDetails;

    public static ArrayList<Integer> needIds;
    private ArrayList<Integer> needIdsDup;

    private Person personObj;

    public Need need;

    Connection conn;

    public void addPerson(Database db, Person person) throws SQLException {
        int person_id = 0;
        conn = db.getConnection();
        Log.d("DEBUG: PersonModel", "Inside addPerson method.");
        String addPersonQuery = "INSERT INTO PERSON (first_name, last_name, email, number) VALUES ('"+ person.getFirstName()+"', '"+ person.getLastName()+"', '"+ person.getEmail()+"', '"+ person.getNumber()+"')";
        Log.d("DEBUG: PersonModel", "addPersonQuery: "+addPersonQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(addPersonQuery);
            ResultSet rs = stmt.executeQuery("SELECT PERSON_ID FROM PERSON WHERE FIRST_NAME = '"+ person.getFirstName().trim()+"' AND LAST_NAME = '"+person.getLastName().trim()+"'");
            while(rs.next()){
                person_id = rs.getInt(1);
            }
            for(int i=0; i<person.getUserNeeds().size();i++){
                String addPersonNeedQuery = "INSERT INTO PERSON_NEED (person_id, need_id) VALUES ('"+ person_id +"', '"+ person.getUserNeeds().get(i) +"')";
                Log.d("DEBUG: PersonModel", "addPersonNeedQuery: "+addPersonNeedQuery);
                stmt.executeUpdate(addPersonNeedQuery);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        Log.d("DEBUG: Database", "addPersonQuery Successful.");
        db.closeConnection(conn);
    }

    public Boolean authenticateUser(Database db, String loginEmailTxt) throws SQLException {
        personDetails = new Person();
        personObj = new Person();
        needIdsDup = new ArrayList<>();
        needIds = new ArrayList<>();
        Boolean authencticationFlag = false;
        ArrayList<String> userEmails = new ArrayList<>();
        conn = db.getConnection();
        Log.d("DEBUG: PersonModel", "Inside authenticateUser method.");
        Log.d("DEBUG: PersonModel", "loginEmailTxt: "+loginEmailTxt.trim());
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String Query1 = "SELECT * FROM PERSON";
            ResultSet rs = stmt.executeQuery(Query1);
            while(rs.next()){
                Log.d("DEBUG: PersonModel", "RESULT SET EMAIL: "+rs.getString("email").trim());
                if(loginEmailTxt.trim().equals(rs.getString("email").trim())){
                    authencticationFlag = true;
                    Log.d("DEBUG: Database", "authenticateUser Successful.");
                    personObj.setPerson_id(rs.getInt("person_id"));
                    personObj.setFirstName(rs.getString("first_name").trim());
                    personObj.setLastName(rs.getString("last_name").trim());
                    personObj.setEmail(rs.getString("email").trim());
                    personObj.setNumber(rs.getString("number").trim());
                    personObj.setRole(rs.getString("role").trim());
                    personDetails = personObj;
                    break;
                }
                else{
                    personDetails = null;
                    Log.d("DEBUG: Database", "authenticateUser Not Successful.");
                }
            }
            String Query2 = "SELECT NEED_ID FROM PERSON_NEED WHERE PERSON_ID = "+personObj.getPerson_id();
            Log.d("DEBUG: PersonModel", "Query2: "+Query2);
            rs = stmt.executeQuery(Query2);
            while (rs.next()){
                Log.d("DEBUG: PersonModel", "RESULT SET Need Id: "+rs.getInt(1));
                needIdsDup.add(rs.getInt(1));
            }
            Log.d("DEBUG: PersonModel", "needIdsDup: "+needIdsDup);
            needIds = needIdsDup;
            Log.d("DEBUG: PersonModel", "needIds: "+needIds);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        db.closeConnection(conn);
        return authencticationFlag;
    }

    public void updatePerson(Database db, Person person) throws SQLException {
        conn = db.getConnection();
        Log.d("DEBUG: PersonModel", "Inside updatePerson method.");
        String updatePersonQuery = "UPDATE PERSON SET first_name = '"+ person.getFirstName()+"', last_name = '"+ person.getLastName()+"', email = '"+ person.getEmail()+"', number = '"+ person.getNumber()+"' WHERE PERSON_ID = "+person.getPerson_id();
        Log.d("DEBUG: PersonModel", "updatePersonQuery: "+updatePersonQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(updatePersonQuery);
            stmt.executeUpdate("DELETE FROM PERSON_NEED WHERE PERSON_ID = "+person.getPerson_id());
            for(int i=0; i<person.getUserNeeds().size();i++){
                String addPersonNeedQuery = "INSERT INTO PERSON_NEED (person_id, need_id) VALUES ('"+ person.getPerson_id() +"', '"+ person.getUserNeeds().get(i) +"')";
                Log.d("DEBUG: PersonModel", "addPersonNeedQuery: "+addPersonNeedQuery);
                stmt.executeUpdate(addPersonNeedQuery);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        Log.d("DEBUG: Database", "updatePerson Successful.");
        personDetails = new Person();
        personObj = new Person();
        needIdsDup = new ArrayList<>();
        needIds = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            String Query1 = "SELECT * FROM PERSON WHERE PERSON_ID = "+person.getPerson_id();
            ResultSet rs = stmt.executeQuery(Query1);
            while(rs.next()){
                Log.d("DEBUG: PersonModel", "RESULT SET EMAIL: "+rs.getString("email").trim());
                    Log.d("DEBUG: Database", "authenticateUser Successful.");
                    personObj.setPerson_id(rs.getInt("person_id"));
                    personObj.setFirstName(rs.getString("first_name").trim());
                    personObj.setLastName(rs.getString("last_name").trim());
                    personObj.setEmail(rs.getString("email").trim());
                    personObj.setNumber(rs.getString("number").trim());
                    personObj.setRole(rs.getString("role").trim());
                    personDetails = personObj;
                    break;
            }
            String Query2 = "SELECT NEED_ID FROM PERSON_NEED WHERE PERSON_ID = "+person.getPerson_id();
            Log.d("DEBUG: PersonModel", "Query2: "+Query2);
            rs = stmt.executeQuery(Query2);
            while (rs.next()){
                Log.d("DEBUG: PersonModel", "RESULT SET Need Id: "+rs.getInt(1));
                needIdsDup.add(rs.getInt(1));
            }
            Log.d("DEBUG: PersonModel", "needIdsDup: "+needIdsDup);
            needIds = needIdsDup;
            Log.d("DEBUG: PersonModel", "needIds: "+needIds);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        db.closeConnection(conn);
    }
}
