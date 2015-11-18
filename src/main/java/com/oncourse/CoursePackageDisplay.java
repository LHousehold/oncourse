package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "coursePackageDisplay", eager = true)
@RequestScoped
public class CoursePackageDisplay {

    public String go() {
        return "<p>HEY HEY</p>";
    }

}
