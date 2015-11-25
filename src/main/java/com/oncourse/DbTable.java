package com.oncourse;

import java.util.List;
import java.sql.*;

public interface DbTable {

    // fill the particular object with the table columns
    public void fill(ResultSet rs);

    // basicly you add a new query and fill in the members that are use for the where query
    // build query for reading the database
    public String readQuery();

    // build query for writing the database
    public String writeQuery();

    // for simple linked list methods
    public DbTable next();
    public void setNext(DbTable next);
    public void push(DbTable entry);
}
