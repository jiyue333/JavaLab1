package io.jiyue333.StuManage.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.util.SimpleSingleton;

@SimpleSingleton
public class StudentRepository {
    @SimpleInject
    private final BasicDB db;

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            @SuppressWarnings("unchecked")
            List<String> studentIds = (List<String>) db.get("students.ids");
            if (studentIds != null) {
                for (String studentId : studentIds) {
                    Student student = (Student) db.get("student:" + studentId);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    public Student getStudentById(String studentId) {
        try {
            return (Student) db.get("student:" + studentId);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
            return null;
        }
    }

    public void saveStudent(Student student) {
        try {
            db.put("student:" + student.getStudentId(), student);
            // Update students.ids list
            @SuppressWarnings("unchecked")
            List<String> studentIds = (List<String>) db.get("students.ids");
            if (studentIds == null) {
                studentIds = new ArrayList<>();
            }
            if (!studentIds.contains(student.getStudentId())) {
                studentIds.add(student.getStudentId());
                db.put("students.ids", studentIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saving student: " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        saveStudent(student);
    }

    public void deleteStudent(String studentId) {
        try {
            db.remove("student:" + studentId);
            @SuppressWarnings("unchecked")
            List<String> studentIds = (List<String>) db.get("students.ids");
            if (studentIds != null) {
                studentIds.remove(studentId);
                db.put("students.ids", studentIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}