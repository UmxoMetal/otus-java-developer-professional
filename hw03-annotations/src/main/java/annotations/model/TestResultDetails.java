package annotations.model;

import annotations.enums.TestResult;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResultDetails {
    private Method method;
    private TestResult result;
    private String details;
}