package ru.otus;

import ru.otus.myAnnotation.After;
import ru.otus.myAnnotation.Before;
import ru.otus.myAnnotation.Test;
import ru.otus.test.MyTest;
import ru.otus.test.TestRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TestRunner.run();

    }
}