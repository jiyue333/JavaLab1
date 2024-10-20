package io.jiyue333.StuManage.Controller;

import java.util.*;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.service.impl.TeacherManageService;
import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.model.Teacher;

/**
 * Controller for managing teacher-related operations.
 */
public class TeacherController {
    @SimpleInject
    private TeacherManageService teacherManagementService;

    /**
     * Updates the information of a teacher.
     * @param teacherId The ID of the teacher to update.
     */
    public void updateTeacherInfo(int teacherId) {
        Scanner scanner = new Scanner(System.in);
        Teacher teacher = teacherManagementService.getTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("教师不存在。");
            return;
        }
        System.out.print("请输入新的姓名: ");
        String name = scanner.nextLine();
        teacher.setName(name);
        teacherManagementService.updateTeacher(teacher);
        System.out.println("教师信息已更新。");
    }

    /**
     * Queries and displays information of a specific teacher.
     * @param teacherId The ID of the teacher to query.
     */
    public void queryTeacherInfo(int teacherId) {
        Teacher teacher = teacherManagementService.getTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("教师不存在。");
            return;
        }
        System.out.println("教师信息：");
        System.out.println("教师编号: " + teacher.getId());
        System.out.println("姓名: " + teacher.getName());
    }

    /**
     * Displays all teachers' information.
     */
    public void displayAllTeachers() {
        List<Teacher> teachers = teacherManagementService.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("没有教师信息。");
            return;
        }
        System.out.println("所有教师信息：");
        System.out.println("教师编号\t姓名");
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + "\t\t" + teacher.getName());
        }
    }
}
