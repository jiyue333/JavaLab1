package io.jiyue333.StuManage.service;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Student;

import java.util.List;

public interface SortedService {
	List<Student> sortedbyNumber(List<Student> tlist);
	List<Student> sortedByGrade(Class mclass);
	List<Student> sortedByLimit(Class mclass, int start, int end);
}
