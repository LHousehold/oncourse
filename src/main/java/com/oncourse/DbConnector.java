package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import javax.annotation.PreDestroy;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

//@ManagedBean(name = "coursePackageDisplay", eager = true)
@ManagedBean(name = "dbConnection", eager = true)
@ApplicationScoped
public class DbConnector {

    private Connection conn;

    public DbConnector() {
        open();
    }

    // not sure this actually works
    @PreDestroy
    public void closeConn() {
        try {
            conn.close();
            System.out.println("Disconnected from the database");
        }
        catch (SQLException ex) {
            System.out.println("An error occurred while disconnecting");
            ex.printStackTrace();
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    // entry should be a new instance of the type of table you are reading from
    public <T extends DbTable> DbTable readTable(DbTable entry, String where, Class<T> type) {
        // when this is created, the first entry will just act as a head and have no valuse by it self
        // call next to get the first entry with values
        //T entry;
        String query;
        System.out.println("begin read: " + type.toString());

        query  = entry.readQuery(where);

        System.out.println("start to read from table");
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                // create this to insert it in to the list of results from the query
                T newEntry;
                try {
                    newEntry = type.newInstance();
                    newEntry.fill(rs);
                    entry.push(newEntry);
                } catch (InstantiationException x) {
                    System.err.println("InstantiationException");
                    x.printStackTrace();
                    return null;
                } catch (IllegalAccessException x) {
                    System.err.println("IllegalAccessException");
                    x.printStackTrace();
                    return null;
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error reading the Database");
            System.err.println(e.getMessage());
        }

        System.out.println("finished read");
        return entry;

    }

    public void writeTable(DbTable entry) {

        String query;
        System.out.println("begin write: " + entry.toString());

        query  = entry.writeQuery();
        System.out.println(query);

        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, no return value
            st.executeUpdate(query);
        }
        catch (SQLException e)
        {
            System.err.println("Error reading the Database");
            System.err.println(e.getMessage());
        }

        System.out.println("finished write");
    }

    private void open() {

        try {
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Error: can not create instance of mariadb jdbc driver");
        }

        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/oncourse",
                    "oc",
                    "");
            if (this.conn != null) {
                System.out.println("Connected to the database oncourse");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }

    }

}
