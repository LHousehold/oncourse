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
       //Course_package cp = new Course_package();
       //cp.cpid = cpid;
       //cp.page = page;
       //cp = (Course_package) db.readTable(cp, Course_package.class);

       //String ret = "<div class='pos_all'>";

       String ret = "HELLO cp# " + cpid;

       //while (cp.next() != null){

       //    cp = (Course_package) cp.next();

       //    if (cp.location.equals("TOP")){
       //        ret += "<div class='pos_top'>";
       //    }
       //    else if (cp.location.equals("BOTTOM")){
       //        ret += "<div class='pos_bottom'>";
       //    }
       //    else if (cp.location.equals("LEFT")){
       //        ret += "<div class='pos_left'>";
       //    }
       //    else if (cp.location.equals("RIGHT")){
       //        ret += "<div class='pos_right'>";
       //    }
       //    else if (cp.location.equals("TL")){
       //        ret += "<div class='pos_tl'>";
       //    }
       //    else if (cp.location.equals("TR")){
       //        ret += "<div class='pos_tr'>";
       //    }
       //    else if (cp.location.equals("BL")){
       //        ret += "<div class='pos_bl'>";
       //    }
       //    else if (cp.location.equals("BR")){
       //        ret += "<div class='pos_br'>";
       //    }
       //    else if (cp.location.equals("FULL")){
       //        ret += "<div class='pos_full'>";
       //    }

       //    if (cp.media_type.equals("YOUTUBE")){
       //        ret += "<iframe class = 'iframeStyle' src='https://www.youtube.com/embed/"
       //         + cp.source + "'frameborder='0' allowfullscreen></iframe>";


       //    }
       //    else if (cp.media_type.equals("PDF")){
       //        //ret += "<embed class = 'pos_full' src='" + cp.source + "'>";
       //        ret += "<iframe class = 'iframeStyle' src='" + cp.source + "' width='' height='' border=''></iframe>";
       //    }
       //    else if (cp.media_type.equals("MP3")){
       //        //ret += "<audio src=''" + cp.source + "' controls='controls'/>";
       //        //ret += "<source src='" + cp.source + "' type='audio/mpeg'>";
       //        //ret += "<embed src='" + cp.source + "' autostart = '0' type='audio/mp3' class = 'pos_full'/> TRACK ONE </embed>";
       //        ret += "<audio controls><source src='" + cp.source + "' type='audio/mpeg'>Your browser does not support the audio element.</audio>";
       //    }

       //    ret += "</div>";

       //}
       return ret;
    }

}
