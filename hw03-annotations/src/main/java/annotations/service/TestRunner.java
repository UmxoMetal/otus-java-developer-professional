package annotations.service;

import annotations.annotations.After;
import annotations.annotations.Before;
import annotations.annotations.Test;
import annotations.exceptions.TestExecutionFailedException;
import annotations.model.TestResultDetails;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import static java.lang.Boolean.FALSE;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static annotations.enums.TestResult.FAILED;
import static annotations.enums.TestResult.PASSED;

@RequiredArgsConstructor(access = PRIVATE)
public class TestRunner {
    private static final String TEST_STARTED_MSG = "Test method [%s] started \n";
    private static final String TEST_FINISHED_MSG = """
    Test method [%s] finished \nresult = [%s], details = [%s] , test class name = [%s]\n
    """;
    private static final String TEST_FAILED_MSG = "TEST FAILED with cause = %s in method %s \n";
    private static final String UNABLE_TO_ACCESS_DEFAULT_CONSTRUCTOR_ERROR_MSG = "Default constructor is not accessible\n";
    private static final String TEST_EXECUTION_ERROR_MSG = "Exception while test execution. Failed with msg = %s \n";
    private static final String SETUP_EXECUTION_ERROR_MSG = "Exception while setup execution. Failed with msg = %s\n";
    private static final String SETUP_EXECUTION_EXCEPTION_MSG = "Exception while setup execution.";
    private static final String SUCCESS_MSG = "Success";

    public static List<TestResultDetails> launch(Class<?> clazz) {
        return getMethodsByAnnotationInClass(Test.class, clazz)
                .stream()
                .map(method -> performTest(method, clazz))
                .collect(toList());
    }

    private static TestResultDetails performTest(Method method, Class<?> clazz) {
        try {
            System.out.printf(TEST_STARTED_MSG, method.getName());
            var instance = clazz.getDeclaredConstructor().newInstance();

            var testDetails = setUp(instance, clazz).isPresent() ?
                    new TestResultDetails(method, FAILED, SETUP_EXECUTION_EXCEPTION_MSG) :
                    executeTest(method, instance);

            tearDown(instance, clazz);
            System.out.printf(TEST_FINISHED_MSG, testDetails.getMethod().getName(), testDetails.getResult(),
                    testDetails.getDetails(), clazz.getName());
            return testDetails;
        } catch (ReflectiveOperationException e) {
            System.err.printf(TEST_EXECUTION_ERROR_MSG, UNABLE_TO_ACCESS_DEFAULT_CONSTRUCTOR_ERROR_MSG);
            throw new TestExecutionFailedException(UNABLE_TO_ACCESS_DEFAULT_CONSTRUCTOR_ERROR_MSG);
        }
    }

    private static Optional<Boolean> setUp(Object instance, Class<?> clazz) {
        return getMethodsByAnnotationInClass(Before.class, clazz)
                .stream()
                .map(beforeMethod -> {
                    try {
                        beforeMethod.invoke(instance);
                        return true;
                    } catch (Exception e) {
                        System.err.printf(SETUP_EXECUTION_ERROR_MSG, e.getCause());
                        return false;
                    }
                })
                .filter(FALSE::equals)
                .findAny();
    }

    private static TestResultDetails executeTest(Method method, Object instance) {
        try {
            method.invoke(instance);
            return new TestResultDetails(method, PASSED, SUCCESS_MSG);
        } catch (Exception e) {
            System.err.printf(TEST_FAILED_MSG, e.getCause(), method.getName());
            return new TestResultDetails(method, FAILED, e.getCause().toString());
        }
    }

    private static void tearDown(Object instance, Class<?> clazz) {
        getMethodsByAnnotationInClass(After.class, clazz)
                .forEach(afterMethod -> {
                    try {
                        afterMethod.invoke(instance);
                    } catch (Exception ignored) {
                    }
                });
    }

    private static List<Method> getMethodsByAnnotationInClass(Class<? extends Annotation> annotation, Class<?> clazz) {
        return stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(toList());
    }
}