package com.oncourse;


public class Section {
	private String sectionName;
	private int pageNumber;
	private double sectionIndex;
	private String sectionType;

	public Section(String sectionName, int pageNumber, double sectionIndex, String sectionType){
		this.sectionName = sectionName;
		this.pageNumber = pageNumber;
		this.sectionIndex = sectionIndex;
		this.sectionType = sectionType;
	}

	public String getSectionName(){
		return sectionName;
	}

	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}

	public double getSectionIndex(){
		return sectionIndex;
	}

	public void setSectionIndex(double sectionIndex){
		this.sectionIndex = sectionIndex;
	}

	public String getSectionType(){
		return sectionType;
	}

	public void setSectionType(String sectionType){
		this.sectionType = sectionType;
	}

}
