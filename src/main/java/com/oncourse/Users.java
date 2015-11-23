package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    String user_name;
    String email;
    String type;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.user_name = rs.getString("user_name");
            this.email = rs.getString("email");
            this.type = rs.getString("type");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
        System.out.format("%s, %s, %s, %s\n", this.id, this.user_name, this.email, this.type);
    }

    // build query for reading the database
    public String readQuery() {
        // still want to write this for how we actually want to query it
        return "SELECT * FROM users WHERE user_name = " + this.user_name;
    }

    // build query for writing the database
    public String writeQuery() {
        // nothing yet, just testing
        return "";
    }

    // for simple linked list methods
    public DbTable next() {
        return this.next;
    }

    public void setNext(DbTable next) {
        this.next = next;
    }

    public void push(DbTable entry) {
        DbTable curr = (DbTable) this;
        while (curr.next() != null) {
            curr = curr.next();
        }
        curr.setNext(entry);
    }
}
