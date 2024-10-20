package io.jiyue333.StuManage.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Student implements Serializable {
    private String studentId;
    private String name;
    private String gender;
    private int assignedClassId; // Changed from Class object to classId
    private List<String> courseIds; // Changed from List<Course> to List<String>
    private Map<String, Grade> grades; // Changed from Map<Course, Grade> to Map<String, Grade>

    // Constructor
    public Student(String studentId, String name, String gender, int assignedClassId, List<String> courseIds, Map<String, Grade> grades) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.assignedClassId = assignedClassId;
        this.courseIds = courseIds;
        this.grades = grades;
    }

    // Getters and Setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAssignedClassId() {
        return assignedClassId;
    }

    public void setAssignedClassId(int assignedClassId) {
        this.assignedClassId = assignedClassId;
    }

    public List<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }

    public Map<String, Grade> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Grade> grades) {
        this.grades = grades;
    }

}