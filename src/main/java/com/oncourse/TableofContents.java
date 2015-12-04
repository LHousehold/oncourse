package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "tableofContents", eager = true)
@RequestScoped
public class TableofContents {

    private List<Section> sections = new ArrayList<Section>();

    public TableofContents() {
        this.sections.add(new Section("Title","1","1","section"));
        this.sections.add(new Section("1. JSP Overview","2","2","section"));
        this.sections.add(new Section("Why use JSP?","2","3","subsection"));
        this.sections.add(new Section("Advantages of JSP","2","4","subsection"));
        this.sections.add(new Section("2. JSP Environment Setup","3","5","section"));
        this.sections.add(new Section("Setting up Java Development Kit","3","6","subsection"));
        this.sections.add(new Section("Setting up Web Server: Tomcat","3","7","subsection"));
        this.sections.add(new Section("Tutorial Videos","4","8","subsection"));
        this.sections.add(new Section("3. JSP Architecture","5","9","section"));
        this.sections.add(new Section("JSP Processing","5","10","subsection"));
    }

    public List<Section> getCPSections() {
        return sections;
    }

    public void setSections(){
        this.sections = sections;

    }

}
