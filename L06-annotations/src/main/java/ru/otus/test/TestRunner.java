package ru.otus.test;

import ru.otus.myAnnotation.After;
import ru.otus.myAnnotation.Before;
import ru.otus.myAnnotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void run() {

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

                Object testingClassObject;
                testingClassObject = clazz.getDeclaredConstructor().newInstance();

                for (var beforeMethod : beforeMethods) {
                    try {
                        beforeMethod.setAccessible(true);
                        beforeMethod.invoke(testingClassObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println("=> Before method is failed");
                        throw new RuntimeException(e);
                    }
                }

                try {
                    try {
                        testMethod.setAccessible(true);
                        testMethod.invoke(testingClassObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } catch (RuntimeException ignore) {
                    failedTestCount++;
                }

                for (var afterMethod : afterMethods) {
                    try {
                        afterMethod.setAccessible(true);
                        afterMethod.invoke(testingClassObject);
                        succeedTestCount++;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println("=> After method is failed");
                        throw new RuntimeException(e);
                    }
                }

            }
            catch (Exception exception) {
                failedTestCount = testMethods.size();
                break;
            }
        }

        System.out.println("Tests failed: " + failedTestCount);
        System.out.println("Tests succeed: " + succeedTestCount);

    }

}
