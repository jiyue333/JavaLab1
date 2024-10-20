package io.jiyue333.StuManage.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.impl.StudentManagementService;
import io.jiyue333.StuManage.util.SimpleInject;
import java.util.List;

public class StudentController {
    @SimpleInject
    private StudentManagementService studentService;

    // In-memory storage for students
    private Map<Integer, Student> students = new HashMap<>();

    /**
     * Updates the information of a student.
     * @param studentId The ID of the student to update.
     */
    public void updateStudentInfo(String studentId) {
        Scanner scanner = new Scanner(System.in);
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("学生不存在。");
            return;
        }
        System.out.print("请输入新的姓名: ");
        String name = scanner.nextLine();
        student.setName(name);
        studentService.updateStudent(student);
        System.out.println("学生信息已更新。");
    }

    /**
     * Queries and displays information of a specific student.
     * @param studentId The ID of the student to query.
     */
    public void queryStudentInfo(String studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("学生不存在。");
            return;
        }
        System.out.println("学生信息：");
        System.out.println("学号: " + student.getStudentId());
        System.out.println("姓名: " + student.getName());
    }

    /**
     * Displays all students' information.
     */
    public void displayAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("没有学生信息。");
            return;
        }
        System.out.println("所有学生信息：");
        System.out.println("学号\t姓名");
        for (Student student : students) {
            System.out.println(student.getStudentId() + "\t" + student.getName());
        }
    }
    
}