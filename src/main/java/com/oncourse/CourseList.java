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

    public void populate() {
        Course_package_name cpn = new Course_package_name();
        cpn = (Course_package_name) db.readTable(cpn, "TRUE",Course_package_name.class);

        while(cpn.next != null) {
            cpn = (Course_package_name) cpn.next();
            this.packages.add(new CoursePackage(cpn.name,cpn.courseCode,cpn.cpid));
        }
    }

    public List<CoursePackage> getCoursePackages() {

        return packages;
    }

}
