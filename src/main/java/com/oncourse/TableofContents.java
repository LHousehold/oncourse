package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import org.json.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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
            sections.add(new Section(cps.id,cps.sectionName,cps.pageNumber,cps.sectionIndex,cps.sectionType));
        }

        return sections;
    }

    public ArrayList<SectionID> getIDs(int cpid) {
        ArrayList<SectionID> sections = new ArrayList<SectionID>();

        Course_package_section cps = new Course_package_section();
        cps = (Course_package_section) db.readTable(cps, "cpid = " + cpid, Course_package_section.class);

        while(cps.next != null) {
            cps = (Course_package_section) cps.next();
            sections.add(new SectionID(cps.id,cps.sectionIndex));
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

        JSONObject ndata = new JSONObject(new_data);
        JSONObject odata = new JSONObject(old_data);

        // compare old data to new data
        /*{'cpid':'2', 'course_name':'Introduction to JSP','sections':[
        {'id':'1', 'index':'1.0', 'name':'Title', 'type':'section', 'page':'5'},
        {'id':'2', 'index':'2.0', 'name':'JSP Overview', 'type':'section', 'page':'5'},
        {'id':'3', 'index':'2.1', 'name':'Why use JSP?', 'type':'subsection', 'page':'5'},
        {'id':'4', 'index':'2.2', 'name':'Advantages of JSP', 'type':'subsection', 'page':'5'},
        {'id':'5', 'index':'3.0', 'name':'JSP Environment Setup', 'type':'section', 'page':'5'},
        {'id':'6', 'index':'3.1', 'name':'Setting up Java Development Kit', 'type':'subsection', 'page':'5'},
        {'id':'7', 'index':'3.2', 'name':'Setting up Web Server: Tomcat', 'type':'subsection', 'page':'5'}]}*/


        assert( ndata.getString("cpid").equals(odata.getString("cpid")) ) ; // cpid should not have changed

        int cpid = Integer.parseInt(ndata.getString("cpid"));

        // compare course package name
        if (! ndata.getString("course_name").equals(odata.getString("course_name")) ) {
            db.genericQuery("UPDATE course_package_name SET name='" + ndata.getString("course_name") + "' WHERE id='" + cpid + "'");
        }

        // traverse through array of sections and detect changes
        JSONArray sectionarray_o = odata.getJSONArray("sections");
        JSONArray sectionarray_n = ndata.getJSONArray("sections");

        HashMap<Integer, Section> sections_o = new HashMap<Integer, Section>();
        HashMap<Integer, Section> sections_n = new HashMap<Integer, Section>();
        List<Section> new_sections = new ArrayList<Section>();

        // populate two maps
        for (int i = 0; i < sectionarray_o.length(); i++) {
            JSONObject object1 = sectionarray_o.getJSONObject(i);
            Section new_section = new Section(object1.getInt("id"), object1.getString("name"), object1.getInt("page"), object1.getDouble("index"), object1.getString("type"));
            sections_o.put(object1.getInt("id"), new_section);
            System.out.println("Old ID: " + object1.getInt("id"));
        }
        for (int j = 0; j < sectionarray_n.length(); j++) {
            JSONObject object2 = sectionarray_n.getJSONObject(j);
            Section new_section = new Section(object2.getInt("id"), object2.getString("name"), object2.getInt("page"), object2.getDouble("index"), object2.getString("type"));

            if (object2.getInt("id") == -1) {
                // this is a new section; add to new sections array
                new_sections.add(new_section);
                System.out.println("New ID: " + object2.getInt("id"));
            }
            else {
                sections_n.put(object2.getInt("id"), new_section);
                System.out.println("New ID: " + object2.getInt("id"));
            }
        }

        // now we traverse through the old map; if we find an element in both maps, we compare them and operate accordingly
        // and remove the element from the second map
        // if we find the element in just the first map, it means we have a removal; operate accordingly
        // when we're done with the old map, traverse the new map
        // any elements remaining in the new map are new sections and have to be inserted

        for (Map.Entry<Integer, Section> entry : sections_o.entrySet())
        {
            int key = (int)entry.getKey();
            Section old_section = entry.getValue();
            Section new_section = sections_n.get(key);
            if (new_section != null) {
                if (new_section.equals(old_section)) {
                    System.out.println("Section " + key + " has not changed");
                }
                else {
                    // find out if names have been changed
                    if (! old_section.getSectionName().equals(new_section.getSectionName()) ) {
                        db.genericQuery("UPDATE course_package_section SET section_name=\"" + new_section.getSectionName() + "\" WHERE id=" + key);
                    }

                    // find out if index has been changed
                    if (old_section.getSectionIndex() != new_section.getSectionIndex()) {
                        db.genericQuery("UPDATE course_package_section SET section_index=\"" + new_section.getSectionIndex() + "\" WHERE id=" + key);
                    }

                    if (old_section.getPageNumber() != new_section.getPageNumber()) {
                        db.genericQuery("UPDATE course_package_section SET page_number=\"" + new_section.getPageNumber() + "\" WHERE id=" + key);
                    }
                }
            }
            else { // this section has been removed
                // delete section from database
                db.genericQuery("DELETE FROM course_package_section WHERE id=" + key);
                // REMEMBER TO REMOVE RELEVANT PAGES
            }
        }

        // now add any new sections from new_sections array
        for (Section sect : new_sections) {
            System.out.println("Made it here");
            db.genericQuery("INSERT INTO course_package_section (cpid,section_index,section_name,section_type,page_number) VALUES (" + cpid + "," + sect.getSectionIndex() + ",\"" + sect.getSectionName() + "\",\"" + sect.getSectionType() + "\"," + sect.getPageNumber() + ");");
        }

    }

}
