package io.jiyue333.StuManage.Repository;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.util.SimpleSingleton;

@SimpleSingleton
public class CourseRepository {
    @SimpleInject
    private BasicDB db;

    public Course getCourseById(String courseId) {
        try {
            return (Course) db.get("course:" + courseId);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving course: " + e.getMessage());
            return null;
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        List<String> courseIds = new ArrayList<>();
        try {
            courseIds = (List<String>) db.get("courses.ids");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving courses: " + e.getMessage());
        }
        for (String courseId : courseIds) {
            Course course = getCourseById(courseId);
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }

    public void saveCourse(Course course) {
        try {
            db.put("course:" + course.getCourseId(), course);
        } catch (IOException e) {
            System.err.println("Error saving course: " + e.getMessage());
        }
    }
    
}
