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

    private String yay = "YAY";
    private Connection conn;

    public DbConnector() {
        open();
    }

    // not sure this actually works
    @PreDestroy
    public void closeConn() {
        try {
            conn.close();
            System.out.println("Connected to the database oncourse");
        }
        catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    // entry should be a new instance of the type of table you are reading from
    public <T extends DbTable> DbTable readTable(DbTable entry, Class<T> type) {
        //String query = "SELECT * FROM course_package WHERE cpid = " + cpid + " AND page = " + page;

        // when this is created, the first entry willl just act as a head and have no valuse by it self
        // call next to get the first entry with values
        //T entry;
        String query;
        System.out.println("begin");

        query  = entry.readQuery();

//        try {
//            entry = type.newInstance();
//            query = entry.readQuery();
//        } catch (InstantiationException x) {
//            x.printStackTrace();
//            return null;
//        } catch (IllegalAccessException x) {
//            x.printStackTrace();
//            return null;
//        }

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
                    x.printStackTrace();
                    return null;
                } catch (IllegalAccessException x) {
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

        System.out.println("finied");
        return entry;

    }

    public String tst() {
        return yay;
    }

    private void open() {

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/oncourse",
                    "oc",
                    "");
            //conn = DriverManager.getConnection(url, user, password);
            if (this.conn != null) {
                System.out.println("Connected to the database oncourse");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }

    }

}
