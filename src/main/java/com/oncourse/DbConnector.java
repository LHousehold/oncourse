package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnector {

    public void go() {
        String url = "jdbc:mariadb://localhost:3306/oncourse";
        String user = "oc";
        String password = "";
        Connection conn;

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            //Class.forName("com.mysql.jdbc.Driver");
            System.out.println("YOYOYO");
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/oncourse",
                    "oc",
                    "");
            //conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database test1");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }

    }

}
