package ru.otus.log.service;

import ru.otus.log.annotation.Log;

public interface TestService {
    @Log
    void calculate(int a);

    @Log
    void calculate(int a, int b);

    @Log
    void calculate(int a, int b, String c);

    @Log
    void calculate(int a, int b, String c, double d);
}