package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;

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

}
