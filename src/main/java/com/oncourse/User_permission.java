package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User_permission implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    int uid;
    int cpid;
    String permission_level;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.uid = rs.getInt("uid");
            this.cpid = rs.getInt("cpid");
            this.permission_level = rs.getString("permission_level");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
        System.out.format("%s, %s, %s, %s\n", this.id, this.uid, this.cpid, this.permission_level);
    }

    // build query for reading the database
    public String readQuery(String where) {
        // still want to write this for how we actually want to query it
        //return "SELECT * FROM user_permission WHERE cpid = " + this.cpid + " AND uid = " + this.uid;
        return "SELECT * FROM user_permission WHERE " + where;
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
