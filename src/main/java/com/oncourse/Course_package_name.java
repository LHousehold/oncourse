package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Course_package_name implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    String name;
    String courseCode;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
            this.courseCode = rs.getString("course_code");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
        //System.out.format("%s, %s, %s\n", this.id, this.name, this.course_code);
    }

    // build query for reading the database
    public String readQuery(String where) {
        //return "SELECT * FROM course_package_name WHERE id = " + this.id;
        return "SELECT * FROM course_package_name WHERE " + where;
    }

    // build query for writing the database
    public String writeQuery() {
        // nothing yet, just testing
        return "INSERT INTO course_package_name (name, course_code)" +
                   "VALUES ('" + this.name + "','" + this.courseCode + "')";
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
