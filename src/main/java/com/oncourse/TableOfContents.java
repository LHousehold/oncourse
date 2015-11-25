package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "tableOfContents", eager = true)
@RequestScoped
public class TableOfContents {

    private List<Section> sections = new ArrayList<Section>();

    public TableOfContents() {
        this.sections.add(new Section("Digital Sampling","1","1"));
        this.sections.add(new Section("Machine Learning","7","7"));
        this.sections.add(new Section("Capstone","9","9"));
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(){
        this.sections = sections;

    }

}
