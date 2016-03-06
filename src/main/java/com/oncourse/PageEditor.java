package com.oncourse;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "PageEditor", eager = true)
@RequestScoped

public class PageEditor {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    public String getFiles(int cpid) {

        // this is where you actually start using it
        FileQuery files = new FileQuery();
        files = (FileQuery) db.readTable(files, "cpid=" + cpid, FileQuery.class);

        //String ret = "<div class='pos_all'>";

        String ret = "";

        int fileNum = 0;

        while (files.next() != null){

            files = (FileQuery) files.next();

            ret += "<div id='file" + fileNum + "' class='file_item' draggable='true'" +
                "media_type='" + files.media_type + "' media_source='" + files.source + "'>" + files.name + "</div>";

            fileNum += 1;

        }

        return ret;
    }

}
