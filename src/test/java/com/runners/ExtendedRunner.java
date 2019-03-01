package com.runners;

import cucumber.api.junit.Cucumber;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

public class ExtendedRunner extends Runner {
    private Class clazz;
    private Cucumber cucumber;
    private static final Logger LOGGER = getLogger(ExtendedRunner.class);

    public ExtendedRunner(Class clazzValue) throws Exception {
        clazz = clazzValue;
        cucumber = new Cucumber(clazzValue);
    }

    @Override
    public Description getDescription() {
        return cucumber.getDescription();
    }

    private void runPredefinedMethods(Class annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();

        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    try {
                        method.invoke(null);
                    } catch (InvocationTargetException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public synchronized void run(RunNotifier notifier) {
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        cucumber.run(notifier);
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}

