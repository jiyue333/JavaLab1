package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.Repository.ClassRepository;
import io.jiyue333.StuManage.Repository.CourseRepository;
import io.jiyue333.StuManage.Repository.StudentRepository;
import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManagementService {

    @SimpleInject
    private StudentRepository studentRepository;
    @SimpleInject
    private CourseRepository courseRepository;
    @SimpleInject
    private ClassRepository classRepository;


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

    public List<Class> getClassByStudentId(String studentId) {
        Student student = getStudentById(studentId);
        List<String> classIds = student.getClassIds();
        return classIds.stream().map(this::getClassById).collect(Collectors.toList());
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public Course getCourseById(String courseId) {
        return courseRepository.getCourseById(courseId); }
    public Class getClassById(String classId) {
        return studentRepository.getClassById(classId);
    }

    public List<Student> getStudentsByClassId(String classId) {
        Class mclass = getClassById(classId);
        List<String> studentIds = mclass.getStudentIds();
        return studentIds.stream().map(studentRepository::getStudentById).collect(Collectors.toList());
    }

    public List<Class> getAllClasses() {
        return classRepository.getAllClasses();
    }

    /**
     * 根据姓名搜索学生
     * @param name 学生姓名
     * @return 匹配的学生列表
     */
    public List<Student> searchStudentsByName(String name) {
        if (name == null || name.isEmpty()) {
            return Collections.emptyList();
        }
        return studentRepository.getAllStudents().stream()
                .filter(student -> student.getName().contains(name))
                .collect(Collectors.toList());
    }

}
