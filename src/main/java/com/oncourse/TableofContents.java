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

    private void updatedb() {

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

        // populate two maps
        for (int i = 0; i < sectionarray_o.length(); i++) {
            JSONObject object = sectionarray_o.getJSONObject(i);
            Section new_section = new Section(object.getInt("id"), object.getString("name"), object.getInt("page"), object.getDouble("index"), object.getString("type"));
            sections_o.put(object.getInt("id"), new_section);
        }
        for (int j = 0; j < sectionarray_n.length(); j++) {
            JSONObject object = sectionarray_n.getJSONObject(j);
            Section new_section = new Section(object.getInt("id"), object.getString("name"), object.getInt("page"), object.getDouble("index"), object.getString("type"));
            sections_o.put(object.getInt("id"), new_section);
        }

        // now we traverse through the old map; if we find an element in both maps, we compare them and operate accordingly
        // and remove the element from the second map
        // if we find the element in just the first map, it means we have a removal; operate accordingly
        // when we're done with the old map, traverse the new map
        // any elements remaining in the new map are new sections and have to be inserted

        for (Map.Entry<Integer, Section> entry : sections_o.entrySet())
        {
            
        }

    }

}
