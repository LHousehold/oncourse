package oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnector {

    public void go() {
        String url1 = "jdbc:mysql://localhost:3306/oncourse";
        String user = "oc";
        String password = "";
        Connection conn;

        try {

            conn = DriverManager.getConnection(url1, user, password);
            if (conn != null) {
                System.out.println("Connected to the database test1");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }

    }

}
