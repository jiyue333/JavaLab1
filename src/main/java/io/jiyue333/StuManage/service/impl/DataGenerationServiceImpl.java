package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.Repository.*;
import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.*;
import io.jiyue333.StuManage.service.DataGenerationService;
import io.jiyue333.StuManage.util.RandomDataGenerator;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.List;

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
        List<Teacher> teachers = generator.generateRandomTeachers(20);

        // Generate and save Courses
        List<Course> courses = generator.generateRandomCourses(20, teachers);

        // Generate and save Classes
        List<Class> classes = generator.generateRandomClasses(10, teachers, courses);
        

        // Generate and save Students
        List<Student> students = generator.generateRandomStudents(50, classes, courses);
        
        // Generate and save Grades
        List<Grade> grades = generator.generateRandomGrades(200, students, courses);

        teachers.forEach(teacherRepo::saveTeacher);
        courses.forEach(courseRepo::saveCourse);

        students.forEach(studentRepo::saveStudent);
        // Save Classes after students have been assigned
        classes.forEach(classRepo::saveClass);
        grades.forEach(gradeRepo::saveGrade);

        System.out.println("Random data generation completed successfully.");
    }
}