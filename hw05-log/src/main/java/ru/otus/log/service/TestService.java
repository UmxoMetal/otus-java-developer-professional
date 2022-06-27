package ru.otus.log.service;

public interface TestService {
    void calculate(int a);

    void calculate(int a, int b);

    void calculate(int a, int b, String c);

    void calculate(int a, int b, String c, double d);
}