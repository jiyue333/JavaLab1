package io.jiyue333.StuManage.Repository;

import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.util.SimpleInject;
import java.io.IOException;

public class GradeRepository {
    @SimpleInject
    private BasicDB db;

    public void saveGrade(Grade grade) {
        try {
            db.put("grade:" + grade.getGradeId(), grade);
        } catch (IOException e) {
            System.err.println("Error saving grade: " + e.getMessage());
        }
    }
}
