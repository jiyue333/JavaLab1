package io.jiyue333.StuManage.util;

import java.util.*;
import io.jiyue333.StuManage.Controller.*;


@SimpleSingleton
public class PrintFormatter {
    @SimpleInject
    private StudentController studentController;
    @SimpleInject   
    private ClassController classController;
    @SimpleInject
    private TeacherController teacherController;
    @SimpleInject
    private CourseController courseController;
    
    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    
    // 存储输入信息
    private final Map<String, String> info;

    public PrintFormatter(){
        this.info = new HashMap<>();
    }

    /**
     * Prints a table with headers and data, aligning the text considering character widths.
     * @param headers Array of header titles.
     * @param data 2D array containing table data.
     */
    public void printTable(String[] headers, String[][] data) {
        // 计算列数
        int numColumns = headers.length;

        // 确定每列的最大显示宽度
        int[] maxWidths = new int[numColumns];
        for (int i = 0; i < numColumns; i++) {
            maxWidths[i] = getDisplayWidth(headers[i]);
            for (String[] row : data) {
                if (i < row.length && row[i] != null) {
                    maxWidths[i] = Math.max(maxWidths[i], getDisplayWidth(row[i]));
                }
            }
            // 添加填充
            maxWidths[i] += 2; // 每侧各一个空格
        }

        // 打印表头
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(GREEN);
        headerBuilder.append("|");
        for (int i = 0; i < numColumns; i++) {
            headerBuilder.append(" ");
            headerBuilder.append(padString(headers[i], maxWidths[i]));
            headerBuilder.append(" |");
        }
        headerBuilder.append(RESET);
        System.out.println(headerBuilder.toString());

        // 打印分隔线
        StringBuilder separatorBuilder = new StringBuilder();
        separatorBuilder.append(YELLOW);
        separatorBuilder.append("|");
        for (int i = 0; i < numColumns; i++) {
            separatorBuilder.append(repeatChar('-', maxWidths[i] + 2));
            separatorBuilder.append("|");
        }
        separatorBuilder.append(RESET);
        System.out.println(separatorBuilder.toString());

        // 打印数据行
        for (String[] row : data) {
            StringBuilder rowBuilder = new StringBuilder();
            rowBuilder.append("|");
            for (int i = 0; i < numColumns; i++) {
                rowBuilder.append(" ");
                String cell = (i < row.length && row[i] != null) ? row[i] : "";
                rowBuilder.append(padString(cell, maxWidths[i]));
                rowBuilder.append(" |");
            }
            System.out.println(rowBuilder.toString());
        }
    }

    /**
     * 计算字符串的显示宽度，中文字符算作2，英文字符算作1。
     * @param str 输入字符串
     * @return 显示宽度
     */
    private int getDisplayWidth(String str) {
        int width = 0;
        for (char c : str.toCharArray()) {
            if (isFullWidth(c)) {
                width += 2;
            } else {
                width += 1;
            }
        }
        return width;
    }

    /**
     * 判断字符是否是全宽字符（如中文）。
     * @param c 输入字符
     * @return 是否为全宽字符
     */
    private boolean isFullWidth(char c) {
        // 简单判断中文字符范围
        return (c >= 0x4E00 && c <= 0x9FFF) || (c >= 0x3400 && c <= 0x4DBF);
    }

    /**
     * 根据显示宽度填充字符串，使其达到指定宽度。
     * @param str 原始字符串
     * @param width 目标显示宽度
     * @return 填充后的字符串
     */
    private String padString(String str, int width) {
        int currentWidth = getDisplayWidth(str);
        StringBuilder sb = new StringBuilder(str);
        while (currentWidth < width) {
            sb.append(' ');
            currentWidth++;
        }
        return sb.toString();
    }

    /**
     * 辅助方法：重复指定字符多次。
     * @param ch 字符
     * @param count 重复次数
     * @return 由重复字符组成的字符串
     */
    private String repeatChar(char ch, int count) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < count; i++) {
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * Prints a styled header.
     * @param header The header text to print.
     */
    public void printHeader(String header) {
        System.out.println(BLUE + "=== " + header + " ===" + RESET);
    }

    /**
     * Prints an error message.
     * @param message The error message to print.
     */
    public void printError(String message) {
        System.out.println(RED + "Error: " + message + RESET);
    }

    /**
     * Prints a success message.
     * @param message The success message to print.
     */
    public void printSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }

    /**
     * Prints an informational message.
     * @param message The information message to print.
     */
    public void printInfo(String message) {
        System.out.println(YELLOW + message + RESET);
    }

    /**
     * Prints the main menu.
     */
    public void printMenu() {
        while (true) {
            printHeader("学生管理系统菜单");
            System.out.println("1. 学生选课菜单");
            System.out.println("2. 学生信息菜单");
            System.out.println("3. 班级信息菜单");
            System.out.println("4. 教师信息菜单");
            System.out.println("0. 退出系统");
            System.out.println("=====================");
            System.out.print("请输入您的选择: ");

            Scanner scanner = new Scanner(System.in);
            int choice = getIntInput(scanner);
            switch (choice) {
                case 1:
                    printStudentCourseMenu(scanner);
                    break;
                case 2:
                    printStudentInfoMenu(scanner);
                    break;
                case 3:
                    printClassInfoMenu(scanner);
                    break;
                case 4:
                    printTeacherInfoMenu(scanner);
                    break;
                case 0:
                    printSuccess("感谢使用，再见！");
                    System.exit(0);
                default:
                    printError("无效选择，请重新输入。");
            }
        }
    }

    private void printStudentCourseMenu(Scanner scanner) {
        printInfo("所有课程信息如下：");
        courseController.displayAllCourses();
        if(!info.containsKey("studentId")){
            System.out.print("请输入学生学号：");
            String studentId = scanner.next();
            info.put("studentId", studentId);
        }
        boolean flag = true;
        while (flag) {
            String studentId = info.get("studentId");
            printHeader("学生选课菜单");
            System.out.println("1. 查看可选课程");
            System.out.println("2. 选择课程");
            System.out.println("3. 退选课程");
            System.out.println("4. 查看已选课程");
            System.out.println("0. 返回上级菜单");
            System.out.print("请输入您的选择: ");
            int choice = getIntInput(scanner);
            switch (choice) {
                case 1:
                    courseController.displayAvailableCourses(studentId);
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
                    flag = false;
                    info.remove("studentId");
                    break;
                default:
                    printError("无效选择，请重新输入。");
            }
        }
    }

    private void printStudentInfoMenu(Scanner scanner) {
        printInfo("所有学生信息如下：");
        studentController.displayAllStudents();
        if(!info.containsKey("studentId")){
            System.out.print("请输入学生学号：");
            String studentId = scanner.next();
            info.put("studentId", studentId);
        }
        boolean flag = true;
        while (flag) {
            String studentId = info.get("studentId");
            printHeader("学生信息菜单");
            System.out.println("1. 修改学生信息");
            System.out.println("2. 查询学生信息");
            System.out.println("3. 显示所有学生信息");
            System.out.println("0. 返回上级菜单");
            System.out.print("请输入您的选择: ");
            int choice = getIntInput(scanner);
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
                    flag = false;
                    info.remove("studentId");
                    break;
                default:
                    printError("无效选择，请重新输入。");
            }  
        }
    }

    private void printClassInfoMenu(Scanner scanner) {
        printInfo("所有班级信息如下：");
        classController.displayAllClass();
        if(!info.containsKey("classId")){
            System.out.print("请输入班级编号：");
            String classId = scanner.next();
            info.put("classId", classId);
        }
        boolean flag = true;
        while (flag) {
            String classId = info.get("classId");
            printHeader("班级信息菜单");
            System.out.println("1. 展示班级信息");
            System.out.println("2. 依据成绩排序");
            System.out.println("3. 依据学号排序");
            System.out.println("4. 根据分数段划分");
            System.out.println("0. 返回上级菜单");
            System.out.print("请输入您的选择: ");
            int choice = getIntInput(scanner);
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
                    flag = false;
                    info.remove("classId");
                    break;
                default:
                    printError("无效选择，请重新输入。");
            }
        }
    }

    private void printTeacherInfoMenu(Scanner scanner) {
        printInfo("所有教师信息如下：");
        teacherController.displayAllTeachers();
        if(!info.containsKey("teacherId")){
            System.out.print("请输入教师编号：");
            String teacherId = scanner.next();
            info.put("teacherId", teacherId);
        }
        boolean flag = true;
        while (flag) {
            String teacherId = info.get("teacherId");
            printHeader("教师信息菜单");
            System.out.println("1. 修改教师信息");
            System.out.println("2. 查询教师信息");
            System.out.println("3. 显示所有教师信息");
            System.out.println("0. 返回上级菜单");
            System.out.print("请输入您的选择: ");
            int choice = getIntInput(scanner);
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
                    flag = false;
                    info.remove("teacherId");
                    break;
                default:
                    printError("无效选择，请重新输入。");
            }
        }
    }

    /**
     * Helper method to safely get integer input from the user.
     * @param scanner The Scanner instance to read input.
     * @return The integer input by the user.
     */
    private int getIntInput(Scanner scanner) {
        int input = -1;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            printError("请输入有效的整数。");
            scanner.next(); // Clear invalid input
        }
        return input;
    }
}
