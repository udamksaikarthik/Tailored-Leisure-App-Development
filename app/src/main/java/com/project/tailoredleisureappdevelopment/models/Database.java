package com.project.tailoredleisureappdevelopment.models;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.util.Log;

import com.project.tailoredleisureappdevelopment.entities.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*
This is Database class.
This class helps to establish database connection.
 */
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

    /*
    This getConnection method establishs the db connection
     */
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

    /*
    This closeConnection method closes the db connection

     * @param conn the Connection instance
     */
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
}
