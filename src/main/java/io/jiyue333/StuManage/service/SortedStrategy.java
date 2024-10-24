
package io.jiyue333.StuManage.service;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Student;

import java.util.List;

public interface SortedStrategy {
    List<Student> sorted(Class mclass);
}