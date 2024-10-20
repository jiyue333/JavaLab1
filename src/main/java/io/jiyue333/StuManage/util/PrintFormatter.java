package io.jiyue333.StuManage.util;
import java.util.*;
import io.jiyue333.StuManage.Controller.*;

public class PrintFormatter {
    @SimpleInject
    private final StudentController studentController;
    @SimpleInject   
    private final ClassController classController;
    @SimpleInject
    private final TeacherController teacherController;
    @SimpleInject
    private final CourseController courseController;
    @SimpleInject
    private final GradeController gradeController;

    // 存储学生学号
    private Map<String, Integer> info;


    
    public void printTable(String[] headers, String[][] data) {
        // 实现表格打印逻辑
    }

    public PrintFormatter(){
        this.info = new HashMap<>();
    }

    public void printMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────┐");
            System.out.println("│      学生管理系统菜单       │");
            System.out.println("├─────────────────────────────┤");
            System.out.println("1. 学生选课菜单");
            System.out.println("2. 学生信息菜单");
            System.out.println("3. 班级信息菜单");
            System.out.println("4. 教师信息菜单");
            System.out.println("0. 退出系统");
            System.out.println("=====================");
            System.out.print("请输入您的选择: ");

            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printStudentCourseMenu();
                    break;
                case 2:
                    printStudentInfoMenu();
                    break;
                case 3:
                    printClassInfoMenu();
                    break;
                case 4:
                    printTeacherInfoMenu();
                    break;
                case 0:
                    System.out.println("感谢使用，再见！");
                    System.exit(0);
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    public void printStudentCourseMenu() {
        Scanner scanner = new java.util.Scanner(System.in);
        if(!info.containsKey("studentId")){
            System.out.println("请输入学生学号：");
            int studentId = scanner.nextInt();
            info.put("studentId", studentId);
        }
        int studentId = info.get("studentId");
        System.out.println("\n===== 学生选课菜单 =====");
        System.out.println("1. 查看可选课程");
        System.out.println("2. 选择课程");
        System.out.println("3. 退选课程");
        System.out.println("4. 查看已选课程");
        System.out.println("0. 返回上级菜单");
        System.out.print("请输入您的选择: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                courseController.displayAllCourses(studentId);
                break;
            case 2:
                courseController.selectCourse(studentId);
                break;
            case 3:
                courseController.dropCourse(studentId);
                break;
            case 4:
                courseController.displaySelectedCourses(studentId);
                break;
            case 0:
                printMenu();
            default:
                break;
        }
    }

    private void printStudentInfoMenu() {
        Scanner scanner = new java.util.Scanner(System.in);
        if(!info.containsKey("studentId")){
            System.out.println("请输入学生学号：");
            int studentId = scanner.nextInt();
            info.put("studentId", studentId);
        }
        int studentId = info.get("studentId");
        System.out.println("\n===== 学生信息菜单 =====");
        System.out.println("1. 修改学生信息");
        System.out.println("2. 查询学生信息");
        System.out.println("3. 显示所有学生信息");
        System.out.println("0. 返回上级菜单");
        System.out.print("请输入您的选择: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                studentController.updateStudentInfo(studentId);
                break;
            case 2:
                studentController.queryStudentInfo(studentId);
                break;
            case 3:
                studentController.displayAllStudents();
                break;
            case 0:
                printMenu();
            default:
                break;
        }  
    }

    private void printClassInfoMenu() {
        Scanner scanner = new java.util.Scanner(System.in);
        if(!info.containsKey("classId")){
            System.out.println("请输入班级编号：");
            int classId = scanner.nextInt();
            info.put("classId", classId);
        }
        int classId = info.get("classId");
        System.out.println("\n===== 班级信息菜单 =====");
        System.out.println("1. 展示班级信息");
        System.out.println("2. 依据成绩排序");
        System.out.println("3. 依据学号排序");
        System.out.println("4. 根据分数段划分");
        System.out.println("0. 返回上级菜单");
        System.out.print("请输入您的选择: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                classController.displayClassInfo(classId);
                break;
            case 2:
                classController.sortedByGrade(classId);
                break;
            case 3:
                classController.sortedByStudentId(classId);
                break;
            case 4:
                classController.sortedByScore(classId);
                break;
            case 0:
                printMenu();
            default:
                break;
        }
    }

    private void printTeacherInfoMenu() {
        Scanner scanner = new java.util.Scanner(System.in);
        if(!info.containsKey("teacherId")){
            System.out.println("请输入教师编号：");
            int teacherId = scanner.nextInt();
            info.put("teacherId", teacherId);
        }
        int teacherId = info.get("teacherId");
        System.out.println("\n===== 教师信息菜单 =====");
        System.out.println("1. 修改教师信息");
        System.out.println("2. 查询教师信息");
        System.out.println("3. 显示所有教师信息");
        System.out.println("0. 返回上级菜单");
        System.out.print("请输入您的选择: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                teacherController.updateTeacherInfo(teacherId);
                break;
            case 2:
                teacherController.queryTeacherInfo(teacherId);
                break;
            case 3:
                teacherController.displayAllTeachers();
                break;
            case 0:
                printMenu();
            default:
                break;
        }
    }}