package com.project.tailoredleisureappdevelopment.models;

import android.util.Log;

import com.project.tailoredleisureappdevelopment.entities.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connection;

    private final String host = "roundhouse.proxy.rlwy.net";

    private final String database = "railway";
    private final int port = 46971;
    private final String user = "postgres";
    private final String pass = "SFCJQISLsAKvmhkoUSnnzsCXsdaUqIET";
    private String url = "jdbc:postgresql://"+host+":"+String.valueOf(port)+"/"+database;
    private boolean status;

    public Database()
    {
    }

    public Connection getConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
            status = true;
            System.out.println("connected:" + status);
        }
        catch(Exception e)
        {
            status = false;
            System.out.println("connected:" + status);
            System.out.print(e.getMessage());
            e.printStackTrace();
        }

        return c;
    }

    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
}
