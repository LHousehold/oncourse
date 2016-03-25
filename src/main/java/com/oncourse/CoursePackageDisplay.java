package com.oncourse;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/*
    Position Names:
    FULL
    X|X
    X|X
    LEFT
    X|O
    X|O
    RIGHT
    O|X
    O|X
    TOP
    X|X
    O|O
    BOTTOM
    O|O
    X|X
    TL
    X|O
    O|O
    TR
    O|X
    O|O
    BL
    O|O
    X|O
    BR
    O|O
    O|X
*/

@ManagedBean(name = "coursePackageDisplay", eager = true)
@RequestScoped

public class CoursePackageDisplay {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    public String pageFetch(int cpid, int page) {


        // this is where you actually start using it
       Course_package cp = new Course_package();
       cp = (Course_package) db.readTable(cp, "cpid = " + cpid + " AND page = " + page, Course_package.class);

       //String ret = "<div class='pos_all'>";

       String ret = "";

       while (cp.next() != null){
           // always start with setting to next because the first one is just a head
           // and has no useful values
           // you could use a tmp "pointer" instead
           cp = (Course_package) cp.next();

           if (cp.location.equals("TOP")){
               ret += "<div class='pos_top cp_grid'>";
           }
           else if (cp.location.equals("BOTTOM")){
               ret += "<div class='pos_bottom cp_grid'>";
           }
           else if (cp.location.equals("LEFT")){
               ret += "<div class='pos_left cp_grid'>";
           }
           else if (cp.location.equals("RIGHT")){
               ret += "<div class='pos_right cp_grid'>";
           }
           else if (cp.location.equals("TL")){
               ret += "<div class='pos_tl cp_grid'>";
           }
           else if (cp.location.equals("TR")){
               ret += "<div class='pos_tr cp_grid'>";
           }
           else if (cp.location.equals("BL")){
               ret += "<div class='pos_bl cp_grid'>";
           }
           else if (cp.location.equals("BR")){
               ret += "<div class='pos_br cp_grid'>";
           }
           else if (cp.location.equals("FULL")){
               ret += "<div class='pos_full cp_grid'>";
           }

           if (cp.media_type.equals("YOUTUBE")){
               ret += "<iframe class = 'iframeStyle' src='https://www.youtube.com/embed/"
                + cp.source + "'frameborder='0' allowfullscreen></iframe>";
           }
           else if (cp.media_type.equals("PDF")){
               ret += "<iframe class = 'iframeStyle' src='" + cp.source + "' width='' height='' border=''></iframe>";
           }
           else if (cp.media_type.equals("MP3")){
               ret += "<span class='center_span'></span><audio class='cp_audio' controls><source src='" + cp.source +
                   "' type='audio/mpeg'>Your browser does not support the audio element.</audio>";
           }
           else if (cp.media_type.equals("IMAGE")){
               ret += "<span class='center_span'></span><img width=100% class='cp_img' src='" + cp.source +
                   "' alt='Problem loading Image'>";
           }
           else if (cp.media_type.equals("MP4")){
               ret += "<span class='center_span'></span><video width=100% class='cp_video' controls><source src='" + cp.source +
                   "' type='video/mp4'>Your browser does not support the video element with mp4.</video>";
           }

           ret += "</div>";

           //ret += cp.source+"<br>";
       }
       //ret += "</div>";
       return ret;
    }

}
