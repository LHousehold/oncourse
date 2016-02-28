package com.oncourse;

import java.util.List;
import java.sql.*;

// this is the interface that is expexted by the
// code under the hood that does the database
// heavy lifting

public interface DbTable {

    // ***********************
    // These functions are implemented by the user of the interface
    // ***********************

    // fill the particular object with the table columns
    public void fill(ResultSet rs);

    // basicly you add a new query and fill in the members that are use for the where query
    // build query for reading the database
    // where: the argument to the WHERE part of a query
    public String readQuery(String where);

    // build query for writing the database
    public String writeQuery();



    // ***********************
    // These functions are implemented by the template file that should be
    // used when creating implementers of this interface
    // ***********************

    // for simple linked list methods
    public DbTable next();
    public void setNext(DbTable next);
    public void push(DbTable entry);
}
