package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import org.json.*;
import java.util.Map;

@ManagedBean(name = "tableofContents", eager = true)
@RequestScoped
public class TableofContents {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    // private ArrayList<Section> sections = new ArrayList<Section>();
    // private ArrayList<SectionTop> sectionTops = new ArrayList<SectionTop>();

    // public TableofContents() {
    // }

    public ArrayList<Section> getCPSections(int cpid) {
        ArrayList<Section> sections = new ArrayList<Section>();

        Course_package_section cps = new Course_package_section();
        cps = (Course_package_section) db.readTable(cps, "cpid = " + cpid, Course_package_section.class);

        while(cps.next != null) {
            cps = (Course_package_section) cps.next();
            sections.add(new Section(cps.sectionName,cps.pageNumber,cps.sectionIndex,cps.sectionType));
        }

        return sections;
    }

    public ArrayList<SectionTop> buildEditor(int cpid) {
        ArrayList<Section> sections = getCPSections(cpid);
        ArrayList<SectionTop> sectionTops = new ArrayList<SectionTop>();

        int topIndex = 0;

        for (Section section : sections) {
            if (section.getSectionType().equals("section")) {
                sectionTops.add(new SectionTop(section));
            }
            else if (section.getSectionType().equals("subsection")) {
                topIndex = (int)Math.floor(section.getSectionIndex());
                // minus 1 because section indexes start at 1 in the database for aesthetic purposes
                sectionTops.get(topIndex - 1).addSubsection(section);
            }
        }

        return sectionTops;
    }

    public void save_all() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String new_data = ec.getRequestParameterMap().get("save_cp_form:save_cp_data");
        String old_data = ec.getRequestParameterMap().get("save_cp_form:original_cp_data");

        System.out.println("Data: " + old_data);
        System.out.println("Updated to: " + new_data);

        // JSONObject json_data = new JSONObject(data);

        // String course_package_name = json_data.getString("course_name");
        // db.genericQuery("UPDATE course_package_name SET page_number=5 WHERE TRUE;");
    }

}
