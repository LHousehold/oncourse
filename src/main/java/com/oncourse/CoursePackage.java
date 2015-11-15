package com.oncourse;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "coursePackage", eager = true)
@RequestScoped
public class CoursePackage {

   public String go() {
      return "Test";
   }

}
