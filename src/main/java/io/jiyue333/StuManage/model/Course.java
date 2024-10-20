package io.jiyue333.StuManage.model;

import java.io.Serializable;
import java.util.List;

public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private String teacherId; // ID of the teacher teaching the course
    private List<Integer> classIds; // List of class IDs associated with the course
    private List<String> studentIds; // List of student IDs enrolled in the course

    // Constructor
    public Course(String courseId, String courseName, String teacherId, List<Integer> classIds, List<String> studentIds) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.classIds = classIds;
        this.studentIds = studentIds;
    }

    // Getters and Setters

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<Integer> classIds) {
        this.classIds = classIds;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
}
