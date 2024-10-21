package io.jiyue333.StuManage.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.model.Course;
import io.jiyue333.StuManage.util.SimpleInject;
import io.jiyue333.StuManage.util.SimpleSingleton;


@SimpleSingleton
public class ClassRepository {
    @SimpleInject
    private BasicDB db;

    public void saveClass(Class mclass) {
        try {
            db.put("class:" + mclass.getClassId(), mclass);
            // Update classes.ids list
            @SuppressWarnings("unchecked")
            List<String> classIds = (List<String>) db.get("classes.ids");
            if (classIds == null) {
                classIds = new ArrayList<>();
            }
            if (!classIds.contains(mclass.getClassId())) {
                classIds.add(mclass.getClassId());
                db.put("classes.ids", classIds);
            }
            db.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error saving class: " + e.getMessage());
        }
    }

    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        List<String> classIds = new ArrayList<>();
        try {
            classIds = (List<String>) db.get("classes.ids");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving classes: " + e.getMessage());
        }
        for (String classId : classIds) {
            Class mclass = getClassById(classId);
            if (mclass != null) {
                classes.add(mclass);
            }
        }
        return classes;
    }

    public Class getClassById(String classId) {
        try {
            return (Class) db.get("class:" + classId);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving class: " + e.getMessage());
            return null;
        }
    }
}
