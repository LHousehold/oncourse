package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "tableofContents", eager = true)
@RequestScoped
public class TableofContents {

    @ManagedProperty(value="#{dbConnection}")
    private DbConnector db;

    public void setdb(DbConnector db){
        this.db = db;
    }

    private List<Section> sections = new ArrayList<Section>();

    // public TableofContents() {
    // }

    public List<Section> getCPSections(int cpid) {
        Course_package_section cps = new Course_package_section();
        cps = (Course_package_section) db.readTable(cps, "cpid = " + cpid, Course_package_section.class);

        while(cps.next != null) {
            cps = (Course_package_section) cps.next();
            System.out.println("\n\ncps.sectionName\n\n");
            this.sections.add(new Section(cps.sectionName,cps.pageNumber,cps.sectionIndex,cps.sectionType));
        }

        return sections;
    }

    public void setSections(){
        this.sections = sections;

    }

}
