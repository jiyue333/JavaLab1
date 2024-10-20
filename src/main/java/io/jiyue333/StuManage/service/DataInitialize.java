package io.jiyue333.StuManage.service;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.model.Teacher;

import java.util.List;

public interface DataInitialize {
	List<Student> loadStudents();
	List<Grade> loadGrades();
	List<Teacher> loadTeachers();
	List<Course> loadCourses();
	List<Class> loadClasses();
}
