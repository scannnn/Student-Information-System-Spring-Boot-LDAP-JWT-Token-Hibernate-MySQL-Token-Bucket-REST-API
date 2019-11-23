package com.araproje.OgrenciBilgiSistemi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="sections")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private int id;
	
	@Column(name = "sectionCode", nullable = false, updatable = true)
    private String sectionCode;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Course course;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Instructor instructor;
	
	@Column(name = "startDate", nullable = false, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "finishDate", nullable = false, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date finishDate;
	
	@OneToMany(mappedBy = "section")
	private Set<SectionClassroom> sectionClassrooms = new HashSet<SectionClassroom>();
	
	@OneToMany(mappedBy = "section")
	private Set<StudentSection> studentSections = new HashSet<StudentSection>();

	public Section() {}

	public Section(String sectionCode, Course course, Instructor instructor, Date startDate, Date finishDate) {
		super();
		this.sectionCode = sectionCode;
		this.course = course;
		this.instructor = instructor;
		this.startDate = startDate;
		this.finishDate = finishDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public List<Map<String, String>> getSectionClassrooms() {
		List<Map<String, String>> sectClassrooms = new ArrayList<>();
		
		for(SectionClassroom oneSecClass : sectionClassrooms) {
			Map<String, String> temp = new HashMap<>();
			temp.put("id", ""+oneSecClass.getId());
			temp.put("classroomCode", oneSecClass.getClassroom().getClassroomCode());
			temp.put("type", oneSecClass.getType());
			temp.put("startTime",oneSecClass.getStartTime());
			temp.put("finishTime", oneSecClass.getFinishTime());
			temp.put("day", oneSecClass.getDay());
			sectClassrooms.add(temp);
		}
		return sectClassrooms;
	}

	public void setSectionClassrooms(Set<SectionClassroom> sectionClassrooms) {
		this.sectionClassrooms = sectionClassrooms;
	}

	public Set<StudentSection> getStudentSections() {
		return studentSections;
	}

	public void setStudentSections(Set<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}
	
}
