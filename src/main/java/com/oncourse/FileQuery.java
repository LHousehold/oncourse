package com.oncourse;

import java.sql.ResultSet;
import java.sql.SQLException;

// ##################################
// The comments marked this way must be addressed
// the commented code serves as a completely functional
// example and can just be deleted
//
// The rest of the code should
// NOT be modified
// MUST be included
// ##################################

// ##################################
// change the class name Course_package -> "what ever represents your query"
// ##################################
public class FileQuery implements DbTable {

    // for linked list
    DbTable next = null;

    // table columns
// ##################################
// add member variables to hold all the data you expect to return from the query
// ##################################
    int id;
    int cpid;
    String name;
    String media_type;
    String source;

    public void fill(ResultSet rs) {
        try {
// ##################################
// call rs.getInt/getString to put the data into the member variables
// ##################################
            this.id = rs.getInt("id");
            this.cpid = rs.getInt("cpid");
            this.media_type = rs.getString("media_type");
            this.name = rs.getString("name");
            this.source = rs.getString("source");
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
// ##################################
// write your query to read the database here
// ##################################
        //return "SELECT * FROM course_package WHERE cpid = " + this.cpid + " AND page = " + this.page;
        return "SELECT * FROM files WHERE " + where + ";";
    }

    // build query for writing the database
    public String writeQuery() {
// ##################################
// write your query to write the database here (some design decisions may still affect this functionality)
// ##################################
        return "INSERT INTO files (cpid, media_type, name, source)" +
<<<<<<< HEAD
<<<<<<< HEAD
                  "VALUES (" + cpid + ",'" + media_type + "','" + name + "','" + source + "')";
=======
                  "VALUES (" + cpid + ",'" + media_type + "'," + name + ",'" + source + "')";
>>>>>>> add FileQuery.java
=======
                  "VALUES (" + cpid + ",'" + media_type + "','" + name + "','" + source + "')";
>>>>>>> basic upload now working
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
