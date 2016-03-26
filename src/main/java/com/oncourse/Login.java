package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "Login")
@RequestScoped
public class Login {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    public int login(String username, String password) {
       Users users = new Users();
       users = (Users) db.readTable(users, "user_name='" + username + "' AND password='" + password + "'", Users.class);

       Users valid_user = (Users)users.next();

       if (valid_user != null)
           return valid_user.id;
       else
           return 0;
    }

}
