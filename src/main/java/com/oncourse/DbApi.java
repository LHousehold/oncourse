package com.oncourse;

import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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

    //@ManagedProperty(value="#{navigation}")
    //private Navigation navigation;
    //@PostConstruct
    public String pullData() {
        return "OOO " + db.tst();
        //return "MAAAAN!";
    }
}
