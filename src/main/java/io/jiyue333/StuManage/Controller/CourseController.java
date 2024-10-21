package io.jiyue333.StuManage.Controller;

import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.impl.StudentManagementService;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.util.PrintFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class CourseController {
    @SimpleInject
    private StudentManagementService studentManagementService;
    
    @SimpleInject
    private PrintFormatter printFormatter;

    /**
     * Displays all courses.
     * @param studentId The ID of the student (optional for personalized output).
     */
    public void displayAvailableCourses(String studentId) {
        // Retrieve all courses
        List<Course> courses = studentManagementService.getAllCourses();
        if (courses.isEmpty()) {
            printFormatter.printInfo("当前没有任何课程信息。");
            return;
        }

        // Retrieve the student
        Student student = studentManagementService.getStudentById(studentId);
        if (student == null) {
            printFormatter.printError("学生不存在。");
            return;
        }

        // Filter courses: not selected by the student and have at least one classId
        List<Course> filteredCourses = courses.stream()
            .filter(course -> !student.getCourseIds().contains(course.getCourseId()))
            .filter(course -> !course.getClassIds().isEmpty())
            .collect(Collectors.toList());

        if (filteredCourses.isEmpty()) {
            printFormatter.printInfo("没有符合条件的课程。");
            return;
        }

        // Display the filtered courses
        printFormatter.printHeader("可选课程");
        String[] headers = {"课程编号", "课程名称", "教师编号"};
        String[][] data = new String[filteredCourses.size()][3];
        for (int i = 0; i < filteredCourses.size(); i++) {
            Course course = filteredCourses.get(i);
            data[i][0] = course.getCourseId();
            data[i][1] = course.getCourseName();
            data[i][2] = course.getTeacherId();
        }
        printFormatter.printTable(headers, data);
    }

    /**
     * Allows a student to select a course.
     * @param studentId The ID of the student.
     */
    public void selectCourse(String studentId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要选择的课程ID: ");
        String courseId = scanner.nextLine();
        Course course = studentManagementService.getCourseById(courseId);
        if (course == null) {
            printFormatter.printError("课程不存在。");
            return;
        }
        Student student = studentManagementService.getStudentById(studentId);
        if (student == null) {
            printFormatter.printError("学生不存在。");
            return;
        }
        if (student.getCourseIds().contains(courseId)) {
            printFormatter.printInfo("学生已选此课程。");
            return;
        }
        student.getCourseIds().add(course.getCourseId());
        if(course.getClassIds().size() == 0){
            printFormatter.printError("课程尚未分配班级");
            return;
        }

        student.getClassIds().add(course.getClassIds().get(0));
        studentManagementService.updateStudent(student);
        printFormatter.printSuccess("课程选择成功。");
    }

    /**
     * Allows a student to drop a course.
     * @param studentId The ID of the student.
     */
    public void dropCourse(String studentId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要退选的课程ID: ");
        String courseId = scanner.nextLine();
        Course course = studentManagementService.getCourseById(courseId);
        if (course == null) {
            printFormatter.printError("课程不存在。");
            return;
        }
        Student student = studentManagementService.getStudentById(studentId);
        if (student == null) {
            printFormatter.printError("学生不存在。");
            return;
        }
        if (!student.getCourseIds().contains(courseId)) {
            printFormatter.printInfo("学生未选此课程。");
            return;
        }
        student.getCourseIds().remove(course.getCourseId());
        student.getClassIds().remove(course.getClassIds().get(0));
        studentManagementService.updateStudent(student);
        printFormatter.printSuccess("课程退选成功。");
    }

    /**
     * Displays all courses selected by a student.
     * @param studentId The ID of the student.
     */
    public void displaySelectedCourses(String studentId) {
        Student student = studentManagementService.getStudentById(studentId);
        if (student == null) {
            printFormatter.printError("学生不存在。");
            return;
        }
        List<Course> courses = student.getCourseIds().stream()
                .map(studentManagementService::getCourseById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (courses.isEmpty()) {
            printFormatter.printInfo("学生未选择任何课程。");
            return;
        }
        printFormatter.printHeader("已选课程");
        String[] headers = {"课程编号", "课程名称", "教师编号"};
        String[][] data = new String[courses.size()][3];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.getCourseId();
            data[i][1] = course.getCourseName();
            data[i][2] = course.getTeacherId();
        }
        printFormatter.printTable(headers, data);
    }

    public void displayAllCourses() {
        List<Course> courses = studentManagementService.getAllCourses();
        if (courses.isEmpty()) {
            printFormatter.printInfo("没有课程信息。");
            return;
        }
        printFormatter.printHeader("所有课程信息");
        String[] headers = {"课程编号", "课程名称", "教师编号", "班级IDs"};
        String[][] data = new String[courses.size()][4];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.getCourseId();
            data[i][1] = course.getCourseName();
            data[i][2] = course.getTeacherId();
            data[i][3] = String.join(", ", course.getClassIds());
        }
        printFormatter.printTable(headers, data);
    }
}
