package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.annotation.PreDestroy;
//import javax.faces.bean.ViewScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//@ManagedBean(name = "coursePackageDisplay", eager = true)
@ManagedBean(name = "dbConnection", eager = true)
@ApplicationScoped
public class DbConnector {

    private String yay = "YAY";
    private Connection conn;

    public DbConnector() {
        open();
    }

    @PreDestroy
    public void closeConn() {
        try {
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }

    public Connection getConn() {
        return this.conn;
    }

    public String tst() {
        return yay;
    }

    private void open() {
        String url = "jdbc:mariadb://localhost:3306/oncourse";
        String user = "oc";
        String password = "";

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
