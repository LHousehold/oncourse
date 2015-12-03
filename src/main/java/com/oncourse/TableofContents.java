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
        this.sections.add(new Section("Digital Sampling","1","1","section"));
        this.sections.add(new Section("Machine Learning","7","7","subsection"));
        this.sections.add(new Section("Capstone","9","9","section"));
    }

    public List<Section> getCPSections() {
        return sections;
    }

    public void setSections(){
        this.sections = sections;

    }

}
