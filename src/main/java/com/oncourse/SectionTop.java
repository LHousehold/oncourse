package com.oncourse;

import java.util.ArrayList;


public class SectionTop {

	private Section head;
	private ArrayList<Section> subsections = new ArrayList<Section>();

	public SectionTop(Section head){
		this.head = head;
	}

	public Section getHead() {
		return head;
	}

	public ArrayList<Section> getSubsections() {
		return subsections;
	}

	public void addSubsection(Section section) {
		this.subsections.add(section);
	}
}
