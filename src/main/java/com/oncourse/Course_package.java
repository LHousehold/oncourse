package com.oncourse;

import java.sql.*;

public class Course_package implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    int cpid;
    String media_type;
    int page;
    String source;
    String location;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.cpid = rs.getInt("cpid");
            this.media_type = rs.getString("media_type");
            this.page = rs.getInt("page");
            this.source = rs.getString("source");
            this.location = rs.getString("location");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
        System.out.format("%s, %s, %s, %s, %s, %s\n", this.id, this.cpid, this.media_type, this.page, this.source, this.location);
    }

    // build query for reading the database
    public String readQuery() {
        return "SELECT * FROM course_package WHERE cpid = " + this.cpid + " AND page = " + this.page;
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

    public void push(DbTable entry) {
        Course_package curr = this;
        while (curr.next() != null) {
            curr = (Course_package) curr.next();
        }
        curr.next = entry;
    }
}
