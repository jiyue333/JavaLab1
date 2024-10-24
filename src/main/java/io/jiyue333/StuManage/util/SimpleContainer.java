package io.jiyue333.StuManage.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleContainer {
	private static Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

	private static <T> T createInstance(Class<T> cls, boolean singleton) throws Exception {
		T obj = cls.newInstance();
		if (singleton) {
			instances.put(cls, obj);
		}
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(SimpleInject.class)) {
				if (!f.isAccessible()) {
					f.setAccessible(true);
				}
				Class<?> fieldCls = f.getType();
				f.set(obj, getInstance(fieldCls));
			}
		}
		return obj;
	}

	public static <T> T getInstance(Class<T> cls) {
		try {
			boolean singleton = cls.isAnnotationPresent(SimpleSingleton.class);
			if (!singleton) {
				return createInstance(cls, false);
			}
			Object obj = instances.get(cls);
			if (obj != null) {
				return (T) obj;
			}
			synchronized (cls) {
				obj = instances.get(cls);
				if (obj == null) {
					obj = createInstance(cls, true);
				}
			}
			return (T) obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
