package ru.otus;

import ru.otus.myAnnotation.After;
import ru.otus.myAnnotation.Before;
import ru.otus.myAnnotation.Test;
import ru.otus.test.MyTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //var test = new MyTest();
        Class<MyTest> clazz = MyTest.class;

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

        int failedTestCount = 0;
        int succeedTestCount = 0;

        for (Method method : clazz.getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Before) {
                    beforeMethods.add(method);
                }
                if (annotation instanceof Test) {
                    testMethods.add(method);
                }
                if (annotation instanceof After) {
                    afterMethods.add(method);
                }
            }
        }

        for (var testMethod : testMethods) {
            try {
                for (var beforeMethod : beforeMethods) {
                    try {
                        beforeMethod.setAccessible(true);
                        beforeMethod.invoke(clazz);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    testMethod.setAccessible(true);
                    testMethod.invoke(clazz);
                    succeedTestCount++;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

                for (var afterMethod : afterMethods) {
                    try {
                        afterMethod.setAccessible(true);
                        afterMethod.invoke(clazz);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            catch (Exception exception) {
                failedTestCount++;
            }
        }

        System.out.println("Tests failed: " + failedTestCount);
        System.out.println("Tests succeed: " + succeedTestCount);

    }
}