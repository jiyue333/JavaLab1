package io.jiyue333.StuManage.Controller;

import java.util.List;
import java.util.Scanner;
import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.service.impl.TeacherManageService;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.util.PrintFormatter;

public class TeacherController {
    @SimpleInject
    private TeacherManageService teacherManagementService;
    
    @SimpleInject
    private PrintFormatter printFormatter;

    /**
     * Updates the information of a teacher.
     * @param teacherId The ID of the teacher to update.
     */
    public void updateTeacherInfo(String teacherId) {
        Scanner scanner = new Scanner(System.in);
        Teacher teacher = teacherManagementService.getTeacherById(teacherId);
        if (teacher == null) {
            printFormatter.printError("教师不存在。");
            return;
        }
        System.out.print("请输入新的姓名: ");
        String name = scanner.nextLine();
        teacher.setName(name);
        teacherManagementService.updateTeacher(teacher);
        printFormatter.printSuccess("教师信息已更新。");
    }

    /**
     * Queries and displays information of a specific teacher.
     * @param teacherId The ID of the teacher to query.
     */
    public void queryTeacherInfo(String teacherId) {
        Teacher teacher = teacherManagementService.getTeacherById(teacherId);
        if (teacher == null) {
            printFormatter.printError("教师不存在。");
            return;
        }
        printFormatter.printHeader("教师信息");
        String[] headers = {"教师编号", "姓名"};
        String[] dataRow = {
            teacher.getTeacherId(),
            teacher.getName()
        };
        printFormatter.printTable(headers, new String[][] {dataRow});
    }

    /**
     * Displays all teachers' information.
     */
    public void displayAllTeachers() {
        List<Teacher> teachers = teacherManagementService.getAllTeachers();
        if (teachers.isEmpty()) {
            printFormatter.printInfo("没有教师信息。");
            return;
        }
        printFormatter.printHeader("所有教师信息");
        String[] headers = {"教师编号", "姓名"};
        String[][] data = new String[teachers.size()][2];
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            data[i][0] = teacher.getTeacherId();
            data[i][1] = teacher.getName();
        }
        printFormatter.printTable(headers, data);
    }
}
