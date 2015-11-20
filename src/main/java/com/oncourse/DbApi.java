package com.oncourse;

import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;

@ManagedBean(name = "testapi", eager = true)
@RequestScoped
public class DbApi {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    // must provide the setter method
    // don't actually call this yourself
    @PostConstruct
    public void setdb(DbConnector db) {
        this.db = db;
    }

    public String pullData() {
        String query = "SELECT * FROM course_package";

        try {
            // create the java statement
            Statement st = db.getConn().createStatement();

            // execute the query, and get a java resultset

            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                int cpid = rs.getInt("cpid");
                String media_type = rs.getString("media_type");
                int page = rs.getInt("page");
                String source = rs.getString("source");
                String location = rs.getString("location");
                //String firstName = rs.getString("first_name");
                //String lastName = rs.getString("last_name");
                //Date dateCreated = rs.getDate("date_created");
                //boolean isAdmin = rs.getBoolean("is_admin");
                //int numPoints = rs.getInt("num_points");

                // print the results
                System.out.format("%s, %s, %s, %s, %s, %s\n", id, cpid, media_type, page, source, location);
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return "OOO " + db.tst();
    }
}
