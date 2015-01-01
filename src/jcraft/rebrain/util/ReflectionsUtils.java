package jcraft.rebrain.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionsUtils {

    public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
        final Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    public static void setPrivateField(String fieldName, Class<?> clazz, Object classObject, Object classValue) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            field.set(classObject, classValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object createPrivateInstance(String packagePath, Class<?>[] constructor, Object... args) {
        Object object = null;

        try {
            final Class<?> clazz = Class.forName(packagePath);
            final Constructor<?> cons = clazz.getConstructor(constructor);

            cons.setAccessible(true);

            object = cons.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static void invokePrivateMethod(String methodName, Class<?> clazz, Object object) {
        try {
            final Method method = clazz.getDeclaredMethod(methodName);

            method.setAccessible(true);

            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
