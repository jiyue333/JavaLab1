package io.jiyue333.StuManage.service.impl;

import io.jiyue333.StuManage.Repository.TeacherRepository;
import io.jiyue333.StuManage.model.Teacher;
import io.jiyue333.StuManage.util.SimpleInject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * 根据姓名搜索教师
	 * @param name 教师姓名
	 * @return 匹配的教师列表
	 */
	public List<Teacher> searchTeachersByName(String name) {
		if (name == null || name.isEmpty()) {
			return Collections.emptyList();
		}
		return teacherRepository.getAllTeachers().stream()
				.filter(teacher -> teacher.getName().contains(name))
				.collect(Collectors.toList());
	}

}
