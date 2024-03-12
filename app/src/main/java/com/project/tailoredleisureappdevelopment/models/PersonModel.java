package com.project.tailoredleisureappdevelopment.models;

import android.util.Log;

import com.project.tailoredleisureappdevelopment.entities.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonModel {

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
        Boolean authencticationFlag = false;
        ArrayList<String> userEmails = new ArrayList<>();
        conn = db.getConnection();
        Log.d("DEBUG: PersonModel", "Inside authenticateUser method.");
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EMAIL FROM PERSON");
            while(rs.next()){
                userEmails.add(rs.getString(1));
            }
            for(int i=0; i<userEmails.size();i++){
                if(userEmails.get(i).equals(loginEmailTxt.trim())){
                    authencticationFlag = true;
                    Log.d("DEBUG: Database", "authenticateUser Successful.");
                }else{
                    Log.d("DEBUG: Database", "authenticateUser Not Successful.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        db.closeConnection(conn);
        return authencticationFlag;
    }
}
