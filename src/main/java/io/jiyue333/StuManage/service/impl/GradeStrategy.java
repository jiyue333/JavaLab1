package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.SortedStrategy;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

;

public class GradeStrategy implements SortedStrategy {
    @SimpleInject
    private StudentManagementService studentManagementService;

    @Override
    public List<Student> sorted(Class mclass) {
        // Retrieve grades associated with the class
        List<Student> students = studentManagementService.getStudentsByClassId(mclass.getClassId());
        String courseName = mclass.getCourseId();

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
}
