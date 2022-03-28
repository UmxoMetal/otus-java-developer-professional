package annotations.service;

import annotations.enums.TestResult;
import annotations.model.TestResultDetails;
import java.util.Collection;
import java.util.function.Predicate;
import lombok.NoArgsConstructor;

import static java.util.stream.Collectors.partitioningBy;
import static lombok.AccessLevel.PRIVATE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@NoArgsConstructor(access = PRIVATE)
public class TestReporter {
    private static final Predicate<TestResultDetails> TEST_PASSED_PREDICATE = testResultDetails -> testResultDetails.getResult() == TestResult.PASSED;
    private static final String TEST_PASSED_MSG = "TEST %s PASSED \n";
    private static final String TEST_FAILED_MSG = "TEST %s FAILED , DETAILS = %s \n";
    private static final String TEST_SUMMARY_MSG = "TEST RUN STATISTICS FOR CLASS = [%s] :\nTOTAL TESTS: %d, PASSED: %d, FAILED: %d \n";

    public static void report(Class<?> clazz, Collection<TestResultDetails> testResults) {
        var partitionedResults = testResults
                .stream()
                .collect(partitioningBy(TEST_PASSED_PREDICATE));

        long passedTestsQty = partitionedResults.get(TRUE)
                .stream()
                .peek(testResult -> System.out.printf(TEST_PASSED_MSG, testResult.getMethod().getName()))
                .count();

        long failedTestsQty = partitionedResults.get(FALSE)
                .stream()
                .peek(testResult -> System.out.printf(TEST_FAILED_MSG, testResult.getMethod().getName(), testResult.getDetails()))
                .count();
        System.out.printf(TEST_SUMMARY_MSG, clazz.getSimpleName(), testResults.size(), passedTestsQty, failedTestsQty);
    }
}