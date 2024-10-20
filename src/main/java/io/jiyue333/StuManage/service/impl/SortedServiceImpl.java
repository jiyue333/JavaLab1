package io.jiyue333.StuManage.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.SortedService;
import io.jiyue333.StuManage.util.SimpleInject;

public class SortedServiceImpl implements SortedService {
    @SimpleInject
    private final StudentManagementService studentManagementService;


    @Override
    public List<Student> sortedbyNumber(List<Student> tlist) {
        return tlist.stream()
                .sorted(Comparator.comparing(Student::getStudentId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortedByGrade(Class mclass) {
        // Retrieve grades associated with the class
        Map<String, Grade> gradeMap = studentManagementService.getGradesByClassId(mclass.getClassId());

        return mclass.getStudentIds().stream()
                .map(studentManagementService::getStudentById)
                .filter(student -> gradeMap.containsKey(student.getStudentId()))
                .sorted(Comparator.comparing((Student student) -> gradeMap.get(student.getStudentId()).getComprehensiveGrade()).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortedByLimit(Class mclass, int start, int end) {
        // Retrieve grades associated with the class
        Map<String, Grade> gradeMap = studentManagementService.getGradesByClassId(mclass.getClassId());

        return mclass.getStudentIds().stream()
                .map(studentManagementService::getStudentById)
                .filter(student -> {
                    Grade grade = gradeMap.get(student.getStudentId());
                    return grade != null && grade.getComprehensiveGrade() >= start && grade.getComprehensiveGrade() <= end;
                })
                .sorted(Comparator.comparing(Student::getStudentId))
                .collect(Collectors.toList());
    }
}