package io.jiyue333.StuManage.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.util.SimpleSingleton;
import io.jiyue333.StuManage.util.SimpleInject;

@SimpleSingleton
public class TeacherRepository {
    @SimpleInject
    private BasicDB db;

    private final static String TEACHERPREFIX = "teacher:";

     
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            @SuppressWarnings("unchecked")
            List<String> teacherIds = (List<String>) db.get("teachers.ids");
            if (teacherIds != null) {
                for (String teacherId : teacherIds) {
                    Teacher teacher = (Teacher) db.get(TEACHERPREFIX + teacherId);
                    if (teacher != null) {
                        teachers.add(teacher);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading teachers: " + e.getMessage());
        }
        return teachers;
    }

     
    public Teacher getTeacherById(String teacherId) {
        try {
            return (Teacher) db.get(TEACHERPREFIX + teacherId);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving teacher: " + e.getMessage());
            return null;
        }
    }

     
    public void saveTeacher(Teacher teacher) {
        try {
            db.put("teacher:" + teacher.getTeacherId(), teacher);
            // Update teachers.ids list
            @SuppressWarnings("unchecked")
            List<String> teacherIds = (List<String>) db.get("teachers.ids");
            if (teacherIds == null) {
                teacherIds = new ArrayList<>();
            }
            if (!teacherIds.contains(teacher.getTeacherId())) {
                teacherIds.add(teacher.getTeacherId());
                db.put("teachers.ids", teacherIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saving teacher: " + e.getMessage());
        }
    }

     
    public void updateTeacher(Teacher teacher) {
        saveTeacher(teacher);
    }

     
    public void deleteTeacher(String teacherId) {
        try {
            db.remove(TEACHERPREFIX + teacherId);
            @SuppressWarnings("unchecked")
            List<String> teacherIds = (List<String>) db.get("teachers.ids");
            if (teacherIds != null) {
                teacherIds.remove(teacherId);
                db.put("teachers.ids", teacherIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deleting teacher: " + e.getMessage());
        }
    }
}