package io.jiyue333.StuManage.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jiyue333.StuManage.model.Grade;
import io.jiyue333.StuManage.util.SimpleInject;

public class GradeRepository {
    @SimpleInject
    private BasicDB db;

    public void saveGrade(Grade grade) {
        try {
            db.put("grade:" + grade.getGradeId(), grade);
            // Update grades.ids list
            @SuppressWarnings("unchecked")
            List<String> gradeIds = (List<String>) db.get("grades.ids");
            if (gradeIds == null) {
                gradeIds = new ArrayList<>();
            }
            if (!gradeIds.contains(grade.getGradeId())) {
                gradeIds.add(grade.getGradeId());
                db.put("grades.ids", gradeIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saving grade: " + e.getMessage());
        }
    }
}
