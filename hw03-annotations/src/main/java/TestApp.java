import annotations.tests.*;

import static annotations.service.TestRunner.*;
import static annotations.service.TestReporter.report;

public class TestApp {
    public static void main(String[] args) {
        report(ServiceTest.class, launch(ServiceTest.class));
    }
}