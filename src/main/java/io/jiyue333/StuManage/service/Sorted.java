package io.jiyue333.StuManage.service;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Student;

import java.util.List;

public class Sorted {

    private SortedStrategy sortedStrategy;

    public Sorted() {
    }

    public Sorted(SortedStrategy sortedStrategy) {
        this.sortedStrategy = sortedStrategy;
    }

    public void changeStrategy(SortedStrategy sortedStrategy) {
        this.sortedStrategy = sortedStrategy;
    }

    public List<Student> sorted(Class mclass) {
        return sortedStrategy.sorted(mclass);
    }
}