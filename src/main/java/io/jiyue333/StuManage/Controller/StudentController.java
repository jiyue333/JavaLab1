package io.jiyue333.StuManage.controller;

import java.util.List;

import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.impl.StudentManagementService;

public class StudentController {
    @SimpleInject
    private final StudentManagementService studentService;

    public void displayAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("List of Students:");
        System.out.println("-----------------");
        for (Student student : students) {
            System.out.println("ID: " + student.getStudentId() +
                               ", Name: " + student.getName() +
                               ", Gender: " + student.getGender());
        }
    }

    public void addStudent(Student student) {
        studentService.addStudent(student);
        System.out.println("Student added successfully.");
    }

    public void updateStudent(Student student) {
        studentService.updateStudent(student);
        System.out.println("Student updated successfully.");
    }

    public void deleteStudent(String studentId) {
        studentService.deleteStudent(studentId);
        System.out.println("Student deleted successfully.");
    }

    public void viewStudentDetails(String studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Student Details:");
        System.out.println("-----------------");
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getName());
        System.out.println("Gender: " + student.getGender());
        System.out.println("Class ID: " + student.getAssignedClassId());
        System.out.println("Courses: " + String.join(", ", student.getCourseIds()));
        // Display grades if available
        if (student.getGrades() != null && !student.getGrades().isEmpty()) {
            System.out.println("Grades:");
            student.getGrades().forEach((courseId, grade) -> {
                System.out.println("  Course ID: " + courseId +
                                   ", Comprehensive Grade: " + grade.getComprehensiveGrade());
            });
        } else {
            System.out.println("No grades available.");
        }
    }
}