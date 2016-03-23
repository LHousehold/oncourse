package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Course_package_section implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
    int id;
    int cpid;
    double sectionIndex;
    String sectionType;
    String sectionName;
    int pageNumber;

    public void fill(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.cpid = rs.getInt("cpid");
            this.sectionIndex = rs.getDouble("section_index");
            this.sectionType = rs.getString("section_type");
            this.sectionName = rs.getString("section_name");
            this.pageNumber = rs.getInt("page_number");
        }
        catch (SQLException e) {
            System.out.println("error while filling entry");
            e.printStackTrace();
        }
// ##################################
// OPTIONAL: print statement that will print to the system log if you want to verify the query this way
// ##################################
        //System.out.format("%s, %s, %s, %s, %s, %s\n", this.id, this.cpid, this.media_type, this.page, this.source, this.location);
    }

    // build query for reading the database
    public String readQuery(String where) {
        return "SELECT * FROM course_package_section WHERE " + where + " ORDER BY section_index ASC";
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
