package io.jiyue333.StuManage.model;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable {
    private String teacherId;
    private String name;
    private List<Integer> classIds; // List of class IDs the teacher is responsible for

    // Constructor
    public Teacher(String teacherId, String name, List<Integer> classIds) {
        this.teacherId = teacherId;
        this.name = name;
        this.classIds = classIds;
    }

    // Getters and Setters

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<Integer> getClassIds() {
	    return classIds;
	}

	public void setClassIds(List<Integer> classIds) {
	    this.classIds = classIds;
	}
}