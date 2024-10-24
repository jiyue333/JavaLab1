package io.jiyue333.StuManage.Controller;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.Sorted;
import io.jiyue333.StuManage.service.impl.GradeStrategy;
import io.jiyue333.StuManage.service.impl.LimitStrategy;
import io.jiyue333.StuManage.service.impl.NumberStrategy;
import io.jiyue333.StuManage.service.impl.StudentManagementService;
import io.jiyue333.StuManage.util.PrintFormatter;
import io.jiyue333.StuManage.util.SimpleContainer;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.InputMismatchException;
import java.util.List;

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
    private Sorted sorted;

    @SimpleInject
    private PrintFormatter printFormatter;

    /**
     * Displays information of a specific class.
     * @param classId The ID of the class to display.
     */
    public void displayClassInfo(String classId) {
        Class mclass = studentManagementService.getClassById(classId);
        if (mclass == null) {
            printFormatter.printError("班级不存在。");
            return;
        }
        printFormatter.printHeader("班级信息");
        String[] headers = {"班级编号", "班级名称", "课程编号", "课程学期", "教师编号", "学生人数"};
        String[] dataRow = {
            mclass.getClassId(),
            mclass.getClassName(),
            mclass.getCourseId(),
            mclass.getSemester(),
            mclass.getTeacherId(),
            String.valueOf(mclass.getTotalStudents())
        };
        printFormatter.printTable(headers, new String[][] {dataRow});
    }

    /**
     * Sorts and displays students in a class by grade.
     * @param classId The ID of the class to sort.
     */
    public void sortedByGrade(String classId) {
        Class mclass = studentManagementService.getClassById(classId);
        if (mclass == null) {
            printFormatter.printError("班级不存在。");
            return;
        }

        sorted.changeStrategy(SimpleContainer.getInstance(GradeStrategy.class));
        List<Student> students = sorted.sorted(mclass);
        if (students.isEmpty()) {
            printFormatter.printInfo("没有学生数据可显示。");
            return;
        }

        printFormatter.printHeader("按成绩排序的学生列表");
        String[] headers = {"学号", "姓名", "综合成绩"};
        String[][] data = new String[students.size()][3];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String studentId = student.getStudentId();
            String name = student.getName(); // 确保 Student 类有 getName() 方法
            double grade = student.getGrades().get(mclass.getCourseId()).getComprehensiveGrade();
            data[i][0] = studentId;
            data[i][1] = name;
            data[i][2] = String.format("%.2f", grade);
        }
        printFormatter.printTable(headers, data);
    }

    /**
     * Sorts and displays students in a class by student ID.
     * @param classId The ID of the class to sort.
     */
    public void sortedByStudentId(String classId) {
        Class mclass = studentManagementService.getClassById(classId);
        if (mclass == null) {
            printFormatter.printError("班级不存在。");
            return;
        }

        List<Student> students = studentManagementService.getStudentsByClassId(classId);
        if (students.isEmpty()) {
            printFormatter.printInfo("没有学生数据可显示。");
            return;
        }

        sorted.changeStrategy(SimpleContainer.getInstance(NumberStrategy.class));
        List<Student> sortedStudents = sorted.sorted(mclass);
        printFormatter.printHeader("按学号排序的学生列表");
        String[] headers = {"学号", "姓名", "综合成绩"};
        String[][] data = new String[sortedStudents.size()][3];
        for (int i = 0; i < sortedStudents.size(); i++) {
            Student student = sortedStudents.get(i);
            String studentId = student.getStudentId();
            String name = student.getName(); // 确保 Student 类有 getName() 方法
            double grade = student.getGrades().get(mclass.getCourseId()).getComprehensiveGrade();
            data[i][0] = studentId;
            data[i][1] = name;
            data[i][2] = String.format("%.2f", grade);
        }
        printFormatter.printTable(headers, data);
    }

    /**
     * Sorts and displays students in a class by score segments.
     * @param classId The ID of the class to sort.
     */
    public void sortedByScore(String classId) {
        Class mclass = studentManagementService.getClassById(classId);
        if (mclass == null) {
            printFormatter.printError("班级不存在。");
            return;
        }
        try {

            sorted.changeStrategy(SimpleContainer.getInstance(LimitStrategy.class));
            List<Student> students = sorted.sorted(mclass);
            if (students.isEmpty()) {
                printFormatter.printInfo("在该分数段内没有学生。");
                return;
            }

            String[] headers = {"学号", "姓名", "综合成绩"};
            String[][] data = new String[students.size()][3];
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                String studentId = student.getStudentId();
                String name = student.getName(); // 确保 Student 类有 getName() 方法
                double grade = student.getGrades().get(mclass.getCourseId()).getComprehensiveGrade();
                data[i][0] = studentId;
                data[i][1] = name;
                data[i][2] = String.format("%.2f", grade);
            }
            printFormatter.printTable(headers, data);
        } catch (InputMismatchException e) {
            printFormatter.printError("请输入有效的整数分数。");
        }
    }

    /**
     * Displays all classes with detailed information.
     */
    public void displayAllClass() {
        List<Class> classes = studentManagementService.getAllClasses();
        if (classes.isEmpty()) {
            printFormatter.printInfo("当前没有任何班级信息。");
            return;
        }

        printFormatter.printHeader("所有班级信息");
        String[] headers = {"班级编号", "班级名称", "课程编号", "课程学期", "教师编号", "学生人数"};
        String[][] data = new String[classes.size()][6];
        for (int i = 0; i < classes.size(); i++) {
            Class mclass = classes.get(i);
            data[i][0] = mclass.getClassId();
            data[i][1] = mclass.getClassName();
            data[i][2] = mclass.getCourseId();
            data[i][3] = mclass.getSemester();
            data[i][4] = mclass.getTeacherId();
            data[i][5] = String.valueOf(mclass.getTotalStudents());
        }
        printFormatter.printTable(headers, data);
    }
}
