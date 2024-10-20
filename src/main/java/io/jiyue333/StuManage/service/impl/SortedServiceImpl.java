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
    private StudentManagementService studentManagementService;


    @Override
    public List<Student> sortedbyNumber(List<Student> tlist) {
        return tlist.stream()
                .sorted(Comparator.comparing(Student::getStudentId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortedByGrade(Class mclass) {
        // Retrieve grades associated with the class
        List<Student> students = studentManagementService.getStudentsByClassId(mclass.getClassId());
        String courseName = mclass.getClassName();

        return students.stream()
                // Filter out students without a grade for the course
                .filter(student -> {
                    Grade grade = student.getGrades().get(courseName);
                    return grade != null ;
                })
                // Sort students by comprehensive grade in descending order
                .sorted(Comparator.comparing(
                        (Student student) -> student.getGrades().get(courseName).getComprehensiveGrade(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortedByLimit(Class mclass, int start, int end) {
        // Retrieve grades associated with the class
        List<Student> students = studentManagementService.getStudentsByClassId(mclass.getClassId());
        String courseName = mclass.getClassName();

        return mclass.getStudentIds().stream()
                .map(studentManagementService::getStudentById)
                .filter(student -> {
                    Grade grade = student.getGrades().get(courseName);
                    return grade != null && grade.getComprehensiveGrade() >= start && grade.getComprehensiveGrade() <= end;
                })
                .sorted(Comparator.comparing(Student::getStudentId))
                .collect(Collectors.toList());
    }
}
