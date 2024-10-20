package io.jiyue333.StuManage.service.impl;

import java.util.List;

import io.jiyue333.StuManage.Repository.ClassRepository;
import io.jiyue333.StuManage.Repository.CourseRepository;
import io.jiyue333.StuManage.Repository.GradeRepository;
import io.jiyue333.StuManage.Repository.StudentRepository;
import io.jiyue333.StuManage.Repository.TeacherRepository;
import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.service.DataGenerationService;
import io.jiyue333.StuManage.util.RandomDataGenerator;
import io.jiyue333.StuManage.util.SimpleInject;

public class DataGenerationServiceImpl implements DataGenerationService {
    @SimpleInject
    private StudentRepository studentRepo;
    @SimpleInject
    private TeacherRepository teacherRepo;
    @SimpleInject
    private CourseRepository courseRepo;
    @SimpleInject
    private ClassRepository classRepo;
    @SimpleInject
    private GradeRepository gradeRepo;
    @SimpleInject
    private RandomDataGenerator generator;

    @Override
    public void generateRandomData() {
        // Generate and save Teachers
        List<Teacher> teachers = generator.generateRandomTeachers(5);
        teachers.forEach(teacherRepo::saveTeacher);

        // Generate and save Courses
        List<Course> courses = generator.generateRandomCourses(10, teachers);
        courses.forEach(courseRepo::saveCourse);

        // Generate and save Classes
        List<Class> classes = generator.generateRandomClasses(3, teachers, courses);
        classes.forEach(classRepo::saveClass);

        // Generate and save Students
        List<Student> students = generator.generateRandomStudents(50, classes, courses);
        students.forEach(studentRepo::saveStudent);

        // Generate and save Grades
        List<Grade> grades = generator.generateRandomGrades(200, students, courses);
        grades.forEach(gradeRepo::saveGrade);

        System.out.println("Random data generation completed successfully.");
    }
}