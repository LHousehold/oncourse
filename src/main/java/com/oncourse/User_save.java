package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User_save implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    int uid;
    int cpid;
    int page;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.uid = rs.getInt("uid");
            this.cpid = rs.getInt("cpid");
            this.page = rs.getInt("page");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
        System.out.format("%s, %s, %s, %s\n", this.id, this.uid, this.cpid, this.page);
    }

    // build query for reading the database
    public String readQuery() {
        // still want to write this for how we actually want to query it
        return "SELECT * FROM user_save WHERE cpid = " + this.cpid + " AND uid = " + this.uid;
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
