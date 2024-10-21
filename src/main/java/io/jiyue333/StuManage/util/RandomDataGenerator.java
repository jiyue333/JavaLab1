package io.jiyue333.StuManage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.model.Student;
import io.jiyue333.StuManage.model.Teacher;

public class RandomDataGenerator {
    private Random random = new Random();
    private static final String[] FIRST_NAMES = {
        "James", "Mary", "John", "Patricia", "Robert", "Jennifer",
        "Michael", "Linda", "William", "Elizabeth", "David", "Barbara",
        "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah",
        "Charles", "Karen"
    };
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia",
        "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez",
        "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore",
        "Jackson", "Martin"
    };
    private static final String[] GENDERS = {"Male", "Female"};
    private static int gradeIdCounter = 1;

    /**
     * Generates a list of random teachers.
     * @param count Number of teachers to generate.
     * @return List of Teacher objects.
     */
    public List<Teacher> generateRandomTeachers(int count) {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String teacherId = "T" + String.format("%03d", i);
            String name = getRandomName();
            List<Integer> classIds = new ArrayList<>(); // Will be assigned later
            Teacher teacher = new Teacher(teacherId, name, classIds);
            teachers.add(teacher);
        }
        return teachers;
    }

    /**
     * Generates a list of random courses.
     * @param count Number of courses to generate.
     * @param teachers List of teachers to assign to courses.
     * @return List of Course objects.
     */
    public List<Course> generateRandomCourses(int count, List<Teacher> teachers) {
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String courseId = "C" + String.format("%03d", i);
            String courseName = "Course_" + i;
            // Assign a random teacher to the course
            Teacher teacher = teachers.get(random.nextInt(teachers.size()));
            String teacherId = teacher.getTeacherId();
            List<String> classIds = new ArrayList<>(); // Will be assigned later
            List<String> studentIds = new ArrayList<>(); // Will be assigned later
            Course course = new Course(courseId, courseName, teacherId, classIds, studentIds);
            courses.add(course);
        }
        return courses;
    }

    /**
     * Generates a list of random classes.
     * @param count Number of classes to generate.
     * @param teachers List of teachers to assign to classes.
     * @param courses List of courses to assign to classes.
     * @return List of Class objects.
     */
    public List<Class> generateRandomClasses(int count, List<Teacher> teachers, List<Course> courses) {
        List<Class> classes = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String classId = "CL" + String.format("%03d", i);
            String className = "Class_" + i;
            // Assign a random teacher to the class
            Teacher teacher = teachers.get(random.nextInt(teachers.size()));
            String teacherId = teacher.getTeacherId();
            int totalStudents = random.nextInt(40) + 10; // Between 10 and 50 students
            String semester = getRandomSemester();
            List<String> studentIds = new ArrayList<>(); // Will be assigned later
            // Assign a random course to the class
            Course course = courses.get(random.nextInt(courses.size()));
            String courseId = course.getCourseId();

            Class mclass = new Class(classId, className, teacherId, totalStudents, semester, studentIds, courseId);
            classes.add(mclass);

            // Update teacher's class list
            teacher.getClassIds().add(Integer.parseInt(classId.substring(2)));
            // Update course's class list
            course.getClassIds().add(classId);
        }
        return classes;
    }

    /**
     * Generates a list of random students.
     * @param count Number of students to generate.
     * @param classes List of classes to assign students to.
     * @param courses List of courses to assign students to.
     * @return List of Student objects.
     */
    public List<Student> generateRandomStudents(int count, List<Class> classes, List<Course> courses) {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            String studentId = "S" + String.format("%04d", i);
            String name = getRandomName();
            String gender = GENDERS[random.nextInt(GENDERS.length)];
            
            // 分配1-2个班级给学生
            int numClasses = random.nextInt(2) + 1;
            List<String> classIds = new ArrayList<>();
            Set<String> mandatoryCourseIds = new HashSet<>(); // 班级对应的课程
            for (int j = 0; j < numClasses; j++) {
                Class mclass = classes.get(random.nextInt(classes.size()));
                classIds.add(mclass.getClassId());
                mandatoryCourseIds.add(mclass.getCourseId()); // 确保班级的课程被包含
                // 将学生添加到班级的学生列表
                mclass.getStudentIds().add(studentId);
            }
            
            // 分配3-5门课程给学生，确保包含必修课程
            int numCourses = random.nextInt(3) + 3;
            List<String> courseIds = new ArrayList<>(mandatoryCourseIds);
            while (courseIds.size() < numCourses) {
                Course course = courses.get(random.nextInt(courses.size()));
                if (!courseIds.contains(course.getCourseId())) {
                    courseIds.add(course.getCourseId());
                    // 将学生添加到课程的学生列表
                    course.getStudentIds().add(studentId);
                }
            }
            
            // 初始化成绩映射
            Map<String, Grade> grades = new HashMap<>();
            Student student = new Student(studentId, name, gender, classIds, courseIds, grades);
            students.add(student);
        }
        return students;
    }

    /**
     * Generates a list of random grades.
     * @param count Number of grades to generate.
     * @param students List of students to assign grades to.
     * @param courses List of courses to associate with grades.
     * @return List of Grade objects.
     */
    public List<Grade> generateRandomGrades(int count, List<Student> students, List<Course> courses) {
        List<Grade> grades = new ArrayList<>();
        for (Student student : students) {
            for (String courseId : student.getCourseIds()) {
                Grade grade = student.getGrades().get(courseId);
                if (grade == null) {
                    // If no grade has been assigned, set default grade values to -1
                    grade = new Grade(
                        String.valueOf(gradeIdCounter++),
                        student.getStudentId(),
                        courseId,
                        -1, // regularGrade
                        -1, // midtermGrade
                        -1, // experimentalGrade
                        -1, // finalGrade
                        -1, // comprehensiveGrade
                        System.currentTimeMillis()
                    );
                    student.getGrades().put(courseId, grade);
                    grades.add(grade);
                }
            }
        }

        // Optionally, assign random grades up to the specified count
        for (int i = 0; i < count; i++) {
            Student student = students.get(random.nextInt(students.size()));
            if (student.getCourseIds().isEmpty()) continue;
            String courseId = student.getCourseIds().get(random.nextInt(student.getCourseIds().size()));

            int regularGrade = random.nextInt(101); // 0-100
            int midtermGrade = random.nextInt(101);
            int experimentalGrade = random.nextInt(101);
            int finalGrade = random.nextInt(101);
            int comprehensiveGrade = (regularGrade + midtermGrade + experimentalGrade + finalGrade) / 4;
            long gradeTimestamp = System.currentTimeMillis();

            Grade grade = new Grade(
                String.valueOf(gradeIdCounter++),
                student.getStudentId(),
                courseId,
                regularGrade,
                midtermGrade,
                experimentalGrade,
                finalGrade,
                comprehensiveGrade,
                gradeTimestamp
            );
            grades.add(grade);

            // Assign grade to student
            student.getGrades().put(courseId, grade);
        }

        return grades;
    }

    // Helper Methods

    private String getRandomName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    private String getRandomSemester() {
        String[] semesters = {"Spring 2023", "Fall 2023", "Spring 2024", "Fall 2024"};
        return semesters[random.nextInt(semesters.length)];
    }
}
