package io.jiyue333.StuManage.Controller;

import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.impl.StudentManagementService;
import io.jiyue333.StuManage.util.PrintFormatter;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentController {
    @SimpleInject
    private StudentManagementService studentService;
    
    @SimpleInject
    private PrintFormatter printFormatter;

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
            printFormatter.printError("学生不存在。");
            return;
        }
        System.out.print("请输入新的姓名: ");
        String name = scanner.nextLine();
        student.setName(name);
        studentService.updateStudent(student);
        printFormatter.printSuccess("学生信息已更新。");
    }

    /**
     * Queries and displays information of a specific student.
     * @param studentId The ID of the student to query.
     */
    public void queryStudentInfo(String studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            printFormatter.printError("学生不存在。");
            return;
        }
        printFormatter.printHeader("学生信息");
        String[] headers = {"学号", "姓名", "课程IDs"};
        String[] dataRow = {
            student.getStudentId(),
            student.getName(),
            String.join(", ", student.getCourseIds())
        };
        printFormatter.printTable(headers, new String[][] {dataRow});
    }

    /**
     * Displays all students' information.
     */
    public void displayAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            printFormatter.printInfo("没有学生信息。");
            return;
        }
        printFormatter.printHeader("所有学生信息");
        String[] headers = {"学号", "姓名", "课程IDs"};
        String[][] data = new String[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            data[i][0] = student.getStudentId();
            data[i][1] = student.getName();
            data[i][2] = String.join(", ", student.getCourseIds());
        }
        printFormatter.printTable(headers, data);
    }
    
    public void searchStudentsByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要搜索的学生姓名: ");
        String name = scanner.nextLine();   
        List<Student> students = studentService.searchStudentsByName(name);
        if (students.isEmpty()) {
            printFormatter.printInfo("没有学生信息。");
            return;
        }
        printFormatter.printHeader("搜索结果");
        String[] headers = {"学号", "姓名", "课程IDs"};
        String[][] data = new String[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            data[i][0] = student.getStudentId();
            data[i][1] = student.getName();
            data[i][2] = String.join(", ", student.getCourseIds());
        }
        printFormatter.printTable(headers, data);
    }
}
