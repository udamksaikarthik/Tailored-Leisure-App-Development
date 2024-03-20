package com.project.tailoredleisureappdevelopment.models;

import android.util.Log;

import com.project.tailoredleisureappdevelopment.entities.Need;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NeedModel {
    public Need need;

    Connection conn;

    public void addNeed(Database db, Need n) throws SQLException {
        conn = db.getConnection();
        Log.d("DEBUG: NeedModel", "Inside addNeed method.");
        String addNeedQuery = "INSERT INTO NEED (need_short_desc, need_long_desc) VALUES ('"+ n.getNeed_short_desc()+"', '"+ n.getNeed_long_desc()+"')";
        Log.d("DEBUG: NeedModel", "addNeedQuery: "+addNeedQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(addNeedQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        Log.d("DEBUG: Database", "addPersonQuery Successful.");
        db.closeConnection(conn);
    }

    public void updateNeed(Database db, Need n) throws SQLException{

    }

    public ArrayList<Need> getNeeds(Database db) throws SQLException{
        ArrayList<Need> needsList = new ArrayList<>();
        conn = db.getConnection();
        Log.d("DEBUG: NeedModel", "Inside showNeeds method.");
        String showNeedsQuery = "SELECT * FROM NEED";
        Log.d("DEBUG: NeedModel", "showNeedsQuery: "+showNeedsQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(showNeedsQuery);
            while(rs.next()){
                Need n = new Need();
                n.setNeed_id(rs.getInt("need_id"));
                n.setNeed_short_desc(rs.getString("need_short_desc"));
                n.setNeed_long_desc(rs.getString("need_long_desc"));
                needsList.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        Log.d("DEBUG: NeedModel", "needsList:"+needsList);
        db.closeConnection(conn);

        return needsList;
    }


    public void deleteNeeds(Database db, ArrayList<String> userNeeds) throws SQLException {
        conn = db.getConnection();
        Log.d("DEBUG: NeedModel", "Inside deleteNeeds method.");
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        try {
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            stmt2 = conn.createStatement();
        for(int i=0; i<userNeeds.size(); i++){
            String getNeedIdQuery = "SELECT NEED_ID FROM NEED WHERE NEED_SHORT_DESC = '"+userNeeds.get(i).trim()+"'";
            Log.d("DEBUG: NeedModel", "getNeedIdQuery: "+getNeedIdQuery);
            ResultSet rs = stmt.executeQuery(getNeedIdQuery);
            while (rs.next()){
                String deletQueryPersonNeed = "DELETE FROM PERSON_NEED WHERE NEED_ID = '"+rs.getInt("NEED_ID")+"'";
                Log.d("DEBUG: NeedModel", "deletQueryPersonNeed: "+deletQueryPersonNeed);
                stmt1.executeUpdate(deletQueryPersonNeed);
                String deleteNeedQuery = "DELETE FROM NEED WHERE NEED_ID = '"+rs.getInt("NEED_ID")+"'";
                Log.d("DEBUG: NeedModel", "deleteNeedQuery: "+deleteNeedQuery);
                stmt2.executeUpdate(deleteNeedQuery);
            }
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
    }
}
