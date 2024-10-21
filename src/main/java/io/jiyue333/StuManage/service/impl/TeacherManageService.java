package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.Repository.TeacherRepository;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.List;

public class TeacherManageService {

	@SimpleInject
	private TeacherRepository teacherRepository;

	public Teacher getTeacherById(String teacherId) {
		return teacherRepository.getTeacherById(teacherId);
	}

	public void updateTeacher(Teacher teacher) {
		teacherRepository.saveTeacher(teacher);
	}

	public List<Teacher> getAllTeachers() {
		return teacherRepository.getAllTeachers();
	}
}
