package ru.otus.log;

import ru.otus.log.service.TestService;
import ru.otus.log.service.TestServiceImpl;

public class TestApp {
    public static void main(String[] args) {
        var testService = new Ioc<TestServiceImpl>().createProxyInstanceAngLogArguments(TestService.class, new TestServiceImpl());
        testService.calculate(1);
        testService.calculate(1, 2);
        testService.calculate(1, 2, "Hello, Otus");
        testService.calculate(1, 2, "Hello, World", 3.0);
    }
}