package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.service.SortedStrategy;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class NumberStrategy implements SortedStrategy {
    @SimpleInject
    private StudentManagementService studentManagementService;

    @Override
    public List<Student> sorted(Class mclass) {
        List<Student> students = studentManagementService.getStudentsByClassId(mclass.getClassId());
        return students.stream()
                .sorted(Comparator.comparing(Student::getStudentId))
                .collect(Collectors.toList());
    }
}
