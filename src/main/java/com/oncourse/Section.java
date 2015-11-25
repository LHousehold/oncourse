package com.oncourse;


public class Section {
	private String sectionName;
	private String pageNumber;
	private String sectionNumber;

	public Section(String sectionName, String pageNumber, String sectionNumber){
		this.sectionName = sectionName;
		this.pageNumber = pageNumber;
		this.sectionNumber = sectionNumber;
	}

	public String getSectionName(){
		return sectionName;
	}

	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}

	public String getPageNumber(){
		return pageNumber;
	}

	public void setPageNumber(String pageNumber){
		this.pageNumber = pageNumber;
	}

	public String getSectionNumber(){
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber){
		this.sectionNumber = sectionNumber;
	}

}
