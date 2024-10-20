package io.jiyue333.StuManage.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.model.Class;

public class RandomDataGenerator {
    private final Random random = new Random();

    // Sample data for names and courses
    private final String[] sampleNames = {
        "Alice", "Bob", "Charlie", "David", "Eva",
        "Frank", "Grace", "Helen", "Ian", "Jane",
        "Karl", "Laura", "Mike", "Nina", "Oscar",
        "Paula", "Quentin", "Rachel", "Steve", "Tina"
    };

    private final String[] sampleCourseNames = {
        "Mathematics", "Physics", "Chemistry", "Biology",
        "History", "Geography", "English", "Computer Science",
        "Art", "Music"
    };

    // Generate random Teachers
    public List<Teacher> generateRandomTeachers(int count) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String teacherId = "T" + String.format("%03d", i);
            String name = sampleNames[random.nextInt(sampleNames.length)] + " Smith";
            teachers.add(new Teacher(teacherId, name, new ArrayList<>()));
        }
        return teachers;
    }

    // Generate random Courses
    public List<Course> generateRandomCourses(int count, List<Teacher> teachers) {
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String courseId = "C" + String.format("%03d", i);
            String courseName = sampleCourseNames[random.nextInt(sampleCourseNames.length)];
            String teacherId = teachers.get(random.nextInt(teachers.size())).getTeacherId();
            courses.add(new Course(courseId, courseName, teacherId, new ArrayList<>(), new ArrayList<>()));
        }
        return courses;
    }

    // Generate random Classes
    public List<Class> generateRandomClasses(int count, List<Teacher> teachers, List<Course> courses) {
        List<Class> classes = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            int classId = 100 + i;
            String teacherId = teachers.get(random.nextInt(teachers.size())).getTeacherId();
            int totalStudents = 0; // Will be updated when students are assigned
            String semester = "Semester " + ((i % 2) + 1);
            List<String> studentIds = new ArrayList<>();
            String courseId = courses.get(random.nextInt(courses.size())).getCourseId();
            classes.add(new Class(classId, teacherId, totalStudents, semester, studentIds, courseId));
        }
        return classes;
    }

    // Generate random Students
    public List<Student> generateRandomStudents(int count, List<Class> classes, List<Course> courses) {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String studentId = "S" + String.format("%03d", i);
            String name = sampleNames[random.nextInt(sampleNames.length)] + " Johnson";
            String gender = random.nextBoolean() ? "Male" : "Female";
            Class assignedClass = classes.get(random.nextInt(classes.size()));
            // Assign courses randomly
            int enrolledCourses = 3 + random.nextInt(3); // Enroll in 3 to 5 courses
            List<String> courseIds = new ArrayList<>();
            while (courseIds.size() < enrolledCourses) {
                String courseId = courses.get(random.nextInt(courses.size())).getCourseId();
                if (!courseIds.contains(courseId)) {
                    courseIds.add(courseId);
                }
            }
            // Create Student with empty grades initially
            Student student = new Student(studentId, name, gender, assignedClass.getClassId(),
                                         courseIds, new java.util.HashMap<>());
            students.add(student);
            // Update Class
            assignedClass.getStudentIds().add(studentId);
            assignedClass.setTotalStudents(assignedClass.getStudentIds().size());
            // Update Courses
            for (String courseId : courseIds) {
                Course course = courses.stream()
                                       .filter(c -> c.getCourseId().equals(courseId))
                                       .findFirst()
                                       .orElse(null);
                if (course != null) {
                    course.getStudentIds().add(studentId);
                }
            }
        }
        return students;
    }

    // Generate random Grades
    public List<Grade> generateRandomGrades(int count, List<Student> students, List<Course> courses) {
        List<Grade> grades = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            int gradeId = i;
            Student student = students.get(random.nextInt(students.size()));
            List<String> enrolledCourses = student.getCourseIds();
            if (enrolledCourses.isEmpty()) continue; // Skip if student is not enrolled in any course
            String courseId = enrolledCourses.get(random.nextInt(enrolledCourses.size()));
            String studentId = student.getStudentId();
            int regularGrade = 50 + random.nextInt(51);       // 50-100
            int midtermGrade = 50 + random.nextInt(51);
            int experimentalGrade = 50 + random.nextInt(51);
            int finalGrade = 50 + random.nextInt(51);
            int comprehensiveGrade = (regularGrade + midtermGrade + experimentalGrade + finalGrade) / 4;
            long timestamp = System.currentTimeMillis() - random.nextInt(1_000_000);
            grades.add(new Grade(gradeId, studentId, courseId, regularGrade, midtermGrade,
                                 experimentalGrade, finalGrade, comprehensiveGrade, timestamp));
        }
        return grades;
    }
}