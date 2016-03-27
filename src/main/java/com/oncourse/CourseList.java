package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "courseList")
@RequestScoped
public class CourseList {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    private List<CoursePackage> packages = new ArrayList<CoursePackage>();

    private Users valid_user;

    public void init(int uid){
        Users users = new Users();
        users = (Users) db.readTable(users, "id=" + uid, Users.class);

        this.valid_user = (Users)users.next();

        if (this.valid_user == null) {
            System.err.println("Invalid user id was entered");
            return;
        }

        String cpid_string = this.valid_user.cpids;

        System.out.println("cpids: " + cpid_string);

        String[] cpids = cpid_string.split(",");

        // assuming that all users are enrolled in at least one course package
        String query_condition = "id=0";

        for (String cpid: cpids) {
            query_condition += " OR id=" + cpid;
        }

        Course_package_name cpn = new Course_package_name();
        cpn = (Course_package_name) db.readTable(cpn, query_condition, Course_package_name.class);

        while(cpn.next != null) {
            cpn = (Course_package_name) cpn.next();
            this.packages.add(new CoursePackage(cpn.name,cpn.courseCode,cpn.cpid));
        }
    }

    public String getName(int cpid) {
        Course_package_name cpn = new Course_package_name();
        cpn = (Course_package_name) db.readTable(cpn, "cpid = " + cpid,Course_package_name.class);
        cpn = (Course_package_name) cpn.next();
        return cpn.name;
    }

    public List<CoursePackage> getCoursePackages(String context) {
        if (context.equals("VIEWER"))
            return packages;
        else { // only INSTRUCTOR should see
            if (valid_user.type.equals("INSTRUCTOR"))
                return packages;
            else
                return null;
        }
    }

}
