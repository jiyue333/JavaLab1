package io.jiyue333.StuManage.Controller;

import io.jiyue333.StuManage.model.Student;
import java.util.*;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.service.impl.*;
import io.jiyue333.StuManage.model.Class;

/**
 * Controller for managing class-related operations.
 */
public class ClassController {
    // Reference to StudentController for accessing students
    @SimpleInject
    private StudentController studentController;
    @SimpleInject
    private StudentManagementService studentManagementService;
    @SimpleInject
    private SortedServiceImpl sortService;

    /**
     * Displays information of a specific class.
     * @param studentId The ID of the class to display.
     */
    public void displayClassInfo(String studentId) {
        List<Class> classes = studentManagementService.getClassByStudentId(studentId);
        if (classes == null) {
            System.out.println("班级不存在。");
            return;
        }
        System.out.println("班级信息：");
        for (Class mclass : classes) {
            System.out.println("班级编号: " + mclass.getClassId());
            System.out.println("班级名称: " + mclass.getClassName());
        }
    }

    /**
     * Sorts and displays students in a class by grade.
     * @param classId The ID of the class to sort.
     */
    public void sortedByGrade(String classId) {
        // Placeholder for sorting by grade
        Class mclass = studentManagementService.getClassById(classId);
        List<Student> students = sortService.sortedByGrade(mclass);
        for (Student student : students) {
            System.out.println(student.getStudentId() + " " + student.getGrades().get(mclass.getClassName()).getComprehensiveGrade());
        }
    }

    /**
     * Sorts and displays students in a class by student ID.
     * @param classId The ID of the class to sort.
     */
    public void sortedByStudentId(String classId) {
        // Placeholder for sorting by student ID
        Class mclass = studentManagementService.getClassById(classId);
        List<Student> students = studentManagementService.getStudentsByClassId(classId);
        List<Student> sortedStudents = sortService.sortedbyNumber(students);
        for (Student student : sortedStudents) {
            System.out.println(student.getStudentId() + " " + student.getGrades().get(mclass.getClassName()).getComprehensiveGrade());
        }
    }

    /**
     * Sorts and displays students in a class by score segments.
     * @param classId The ID of the class to sort.
     */
    public void sortedByScore(String classId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入分数段：");
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        Class mclass = studentManagementService.getClassById(classId);
        List<Student> students = sortService.sortedByLimit(mclass, start, end);
        for (Student student : students) {
            System.out.println(student.getStudentId() + " " + student.getGrades().get(mclass.getClassName()).getComprehensiveGrade());
        }
    }
}
