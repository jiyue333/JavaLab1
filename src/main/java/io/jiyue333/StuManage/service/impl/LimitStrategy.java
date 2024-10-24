package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.SortedStrategy;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LimitStrategy implements SortedStrategy {
    @SimpleInject
    private StudentManagementService studentManagementService;

    @Override
    public List<Student> sorted(Class mclass) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入分数段 (开始-结束): ");
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        if (start > end) {
            System.out.println("开始分数不能大于结束分数。");
            return null;
        }
        List<Student> students = studentManagementService.getStudentsByClassId(mclass.getClassId());
        String courseName = mclass.getCourseId();

        return mclass.getStudentIds().stream()
                .map(studentManagementService::getStudentById)
                .filter(student -> {
                    Grade grade = student.getGrades().get(courseName);
                    return grade != null && grade.getComprehensiveGrade() >= start && grade.getComprehensiveGrade() <= end;
                })
                // Sort students by comprehensive grade in descending order
                .sorted(Comparator.comparing(
                        (Student student) -> student.getGrades().get(courseName).getComprehensiveGrade(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).reversed())
                .collect(Collectors.toList());
    }
}
