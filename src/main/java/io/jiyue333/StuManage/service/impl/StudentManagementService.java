package io.jiyue333.StuManage.service.impl;

import java.util.List;

import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.Repository.StudentRepository;
import io.jiyue333.StuManage.util.SimpleInject;

public class StudentManagementService {

    @SimpleInject
    private final StudentRepository studentRepository;
    

    public Student getStudentById(String studentId) {
        return studentRepository.getStudentById(studentId);
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public void addStudent(Student student) {
        studentRepository.saveStudent(student);
    }

    public void updateStudent(Student student) {
        studentRepository.updateStudent(student);
    }

    public void deleteStudent(String studentId) {
        studentRepository.deleteStudent(studentId);
    }

    // Implement other service methods using respective repositories
}
