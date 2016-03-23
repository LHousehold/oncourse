package com.oncourse;


public class Section {
	private int sectionID;
	private String sectionName;
	private int pageNumber;
	private double sectionIndex;
	private String sectionType;

	public Section(int sectionID, String sectionName, int pageNumber, double sectionIndex, String sectionType){
		this.sectionID = sectionID;
		this.sectionName = sectionName;
		this.pageNumber = pageNumber;
		this.sectionIndex = sectionIndex;
		this.sectionType = sectionType;
	}

	public int getSectionID(){
		return sectionID;
	}

	public void setSectionID(int sectionID){
		this.sectionID = sectionID;
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

	public int getSectionIndexInt(){
		return (int)Math.floor(sectionIndex);
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

	@Override
	public boolean equals(Section that) {
		if (!(that instanceof Section)) return false;

		if (this.sectionID.equals(that.getSectionID()) && this.sectionName.equals(that.getSectionName()) &&
		this.pageNumber.equals(that.getPageNumber()) && this.sectionIndex.equals(that.getSectionIndex()) &&
		this.sectionType.equals(that.getSectionType())) return true;

		else return false;
	}


}
