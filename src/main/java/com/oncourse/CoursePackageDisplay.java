package com.oncourse;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/*
    Position Names:
    FULL
    X|X
    X|X
    LEFT
    X|O
    X|O
    RIGHT
    O|X
    O|X
    TOP
    X|X
    O|O
    BOTTOM
    O|O
    X|X
    TL
    X|O
    O|O
    TR
    O|X
    O|O
    BL
    O|O
    X|O
    BR
    O|O
    O|X
*/

@ManagedBean(name = "coursePackageDisplay", eager = true)
@RequestScoped

public class CoursePackageDisplay {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    //public String pageFetch(int cpid, int page) {
    public String pageFetch() {

        // this is where you actually start using it
       Course_package cp = new Course_package();
       cp.cpid = 1;
       cp.page = 1;
       cp = (Course_package) db.readTable(cp, Course_package.class);

       String ret = "";

       while (cp.next() != null){
           // always start with setting to next because the first one is just a head
           // and has no useful values
           // you could use a tmp "pointer" instead
           cp = (Course_package) cp.next();
           ret += cp.source;
       }
       return ret;
    }

}
