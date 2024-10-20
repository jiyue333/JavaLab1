package io.jiyue333.StuManage.model;

import java.io.Serializable;
import java.util.List;

public class Class implements Serializable {
    private int classId;
    private String teacherId; // ID of the teacher assigned to the class
    private int totalStudents;
    private String semester;
    private List<String> studentIds; // List of student IDs in the class
    private String courseId; // ID of the course taught in the class

    // Constructor
    public Class(int classId, String teacherId, int totalStudents, String semester, List<String> studentIds, String courseId) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.totalStudents = totalStudents;
        this.semester = semester;
        this.studentIds = studentIds;
        this.courseId = courseId;
    }

    // Getters and Setters

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

