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
     * @param classId The ID of the class to display.
     */
    public void displayClassInfo(int studentId) {
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
    public void sortedByGrade(int classId) {
        // Placeholder for sorting by grade
        System.out.println("按照成绩排序的功能尚未实现。");
    }

    /**
     * Sorts and displays students in a class by student ID.
     * @param classId The ID of the class to sort.
     */
    public void sortedByStudentId(int classId) {
        // Placeholder for sorting by student ID
        System.out.println("按照学号排序的功能尚未实现。");
    }

    /**
     * Sorts and displays students in a class by score segments.
     * @param classId The ID of the class to sort.
     */
    public void sortedByScore(int classId) {
        // Placeholder for sorting by score segments
        System.out.println("根据分数段划分的功能尚未实现。");
    }
}
