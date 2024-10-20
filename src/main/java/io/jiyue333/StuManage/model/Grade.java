package io.jiyue333.StuManage.model;

import java.io.Serializable;

public class Grade implements Serializable {
    private int gradeId;
    private String studentId; // ID of the student
    private String courseId; // ID of the course
    private int regularGrade;
    private int midtermGrade;
    private int experimentalGrade;
    private int finalGrade;
    private int comprehensiveGrade;
    private long gradeTimestamp;

    // Constructor
    public Grade(int gradeId, String studentId, String courseId, int regularGrade, int midtermGrade, int experimentalGrade, int finalGrade, int comprehensiveGrade, long gradeTimestamp) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.regularGrade = regularGrade;
        this.midtermGrade = midtermGrade;
        this.experimentalGrade = experimentalGrade;
        this.finalGrade = finalGrade;
        this.comprehensiveGrade = comprehensiveGrade;
        this.gradeTimestamp = gradeTimestamp;
    }

    // Getters and Setters

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

	public String getCourseId() {
	    return courseId;
	}

	public void setCourseId(String courseId) {
	    this.courseId = courseId;
	}

    public int getRegularGrade() {
        return regularGrade;
    }

    public void setRegularGrade(int regularGrade) {
        this.regularGrade = regularGrade;
    }

    public int getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(int midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public int getExperimentalGrade() {
        return experimentalGrade;
    }

    public void setExperimentalGrade(int experimentalGrade) {
        this.experimentalGrade = experimentalGrade;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

    public int getComprehensiveGrade() {
        return comprehensiveGrade;
    }

    public void setComprehensiveGrade(int comprehensiveGrade) {
        this.comprehensiveGrade = comprehensiveGrade;
    }

    public long getGradeTimestamp() {
        return gradeTimestamp;
    }

    public void setGradeTimestamp(long gradeTimestamp) {
        this.gradeTimestamp = gradeTimestamp;
    }
}