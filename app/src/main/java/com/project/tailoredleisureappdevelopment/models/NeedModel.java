package com.project.tailoredleisureappdevelopment.models;

import android.util.Log;
import android.widget.Toast;

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

    public void deleteNeed(Database db, int need_id) throws SQLException{

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


}
