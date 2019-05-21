package org.dreilaenderhack.model;

import java.util.ArrayList;
import java.util.List;

public class Person {

	
	private String name;
	private Double rank;
	private String image;
	private String education;
	private String company;
	private List<String> skills;
	private List<String> projects;
	private List<Document> documents;

	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	
	public void addSkillsItem(String skill) {
		if(skills == null)
			skills = new ArrayList<>();
		skills.add(skill);
	}
	
	public List<String> getProjects() {
		return projects;
	}
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}
	
	public void addProjectsItem(String project) {
		if(projects == null)
			projects = new ArrayList<>();
		projects.add(project);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRank() {
		return rank;
	}
	public void setRank(Double rank) {
		this.rank = rank;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public void addDocumentsItem(Document document) {
		if(documents == null)
			documents = new ArrayList<>();
		documents.add(document);		
	}

}
