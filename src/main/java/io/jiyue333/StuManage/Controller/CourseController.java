package io.jiyue333.StuManage.Controller;

import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.impl.StudentManagementService;
import io.jiyue333.StuManage.util.SimpleInject;
import java.util.*;

/**
 * Controller for managing course-related operations.
 */
public class CourseController {
    @SimpleInject
    private StudentManagementService studentManagementService;
    public void displayAllCourses(int studentId) {
        // 显示所有课程
        List<Course> courses = studentManagementService.getAllCourses();
        System.out.println("所有课程：");
        System.out.println("课程编号\t课程名称\t教师编号");
        for (Course course : courses) {
            System.out.println(course.getCourseId() + "\t\t" + course.getCourseName() + "\t\t" + course.getTeacherId());
        }
    }

    public void selectCourse(int studentId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要选择的课程名称: ");
        String courseName = scanner.nextLine();
        Course course = studentManagementService.getCourseByName(courseName);
        if (course == null) {
            System.out.println("课程不存在。");
            return;
        }
        Student student = studentManagementService.getStudentById(studentId);
        student.getCourseIds().add(course.getCourseId());
        student.getClassIds().add(course.getClassIds().get(0));
        studentManagementService.updateStudent(student);

        System.out.println("课程选择成功。");
    }

    // 退选课程
    public void dropCourse(int studentId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要退选的课程名称: ");
        String courseName = scanner.nextLine();
        Course course = studentManagementService.getCourseByName(courseName);
        if (course == null) {
            System.out.println("课程不存在。");
            return;
        }
        Student student = studentManagementService.getStudentById(studentId);
        student.getCourseIds().remove(course.getCourseId());
        student.getClassIds().remove(course.getClassIds().get(0));
        studentManagementService.updateStudent(student);
        System.out.println("课程退选成功。");
    }

    // 显示已选课程
    public void displaySelectedCourses(int studentId) {
        Student student = studentManagementService.getStudentById(studentId);
        List<Course> courses = student.getCourseIds().stream()
                .map(studentManagementService::getCourseById)
                .collect(Collectors.toList());
        System.out.println("已选课程：");
        System.out.println("课程编号\t课程名称\t教师编号");
        for (Course course : courses) {
            System.out.println(course.getCourseId() + "\t\t" + course.getCourseName() + "\t\t" + course.getTeacherId());
        }
    }
}
