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

    public int savePage(int cpid, int page, String media_type, String source, String location) {
        Course_package cp = new Course_package();

        cp.cpid = cpid;
        cp.page = page;
        cp.media_type = media_type;
        cp.source = source;
        cp.location = location;

        return db.getIdWrite(cp);
    }

    public int removePage(int id) {
        db.genericQuery("DELETE FROM course_package WHERE id=" + id);
        return 0;
    }

    public String getFiles(int cpid) {

        // this is where you actually start using it
        FileQuery files = new FileQuery();
        files = (FileQuery) db.readTable(files, "cpid=" + cpid, FileQuery.class);

        //String ret = "<div class='pos_all'>";

        String ret = "";

        int fileNum = 0;

        // the YOUTUBE adder
        ret += "<div id='youtube_add' class='file_item list-group-item' draggable='true'>" +
            "<span class='glyphicon glyphicon-play' aria-hidden='true'></span>" +
            "YouTube Video</div>";

        while (files.next() != null){

            files = (FileQuery) files.next();

            ret += "<div id='file" + fileNum + "' class='file_item list-group-item' draggable='true'" +
                "data_media_type='" + files.media_type + "' data_media_source='" + files.source + "'>";

            if (files.media_type.equals("PDF")) {
                ret += "<span class='glyphicon glyphicon-file' aria-hidden='true'></span>";
            }
            else if (files.media_type.equals("MP3")) {
                ret += "<span class='glyphicon glyphicon-headphones' aria-hidden='true'></span>";
            }
            else if (files.media_type.equals("IMAGE")) {
                ret += "<span class='glyphicon glyphicon-picture' aria-hidden='true'></span>";
            }
            else if (files.media_type.equals("MP4")) {
                ret += "<span class='glyphicon glyphicon-film' aria-hidden='true'></span>";
            }

            ret += files.name + "</div>";

            fileNum += 1;

        }

        return ret;
    }

    public String getTiles(int cpid, int pagenumber) {

       Course_package cp = new Course_package();
       cp = (Course_package) db.readTable(cp, "cpid = " + cpid + " AND page = " + pagenumber, Course_package.class);

       String ret = "<script type='text/javascript'>";

       while (cp.next() != null){

           cp = (Course_package) cp.next();

           ret += "initial_tile_add('" + cp.location + "', " + cp.id +", '" + cp.media_type + "', '" + cp.source + "');";

       }

       ret += "</script>";

       return ret;

    }

}
