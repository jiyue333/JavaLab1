package io.jiyue333.StuManage.Repository;

import io.jiyue333.StuManage.util.SimpleSingleton;
import io.jiyue333.StuManage.model.Class;
import io.jiyue333.StuManage.util.SimpleInject;
import java.io.IOException;


@SimpleSingleton
public class ClassRepository {
    @SimpleInject
    private BasicDB db;

    public void saveClass(Class mclass) {
        try {
            db.put("class:" + mclass.getClassId(), mclass);
        } catch (IOException e) {
            System.err.println("Error saving class: " + e.getMessage());
        }
    }
}
