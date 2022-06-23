package ru.otus.log.service;

import ru.otus.log.annotation.Log;

public class TestServiceImpl implements TestService {
    @Log
    @Override
    public void calculate(int a) {
    }

    @Log
    @Override
    public void calculate(int a, int b) {
    }

    @Log
    @Override
    public void calculate(int a, int b, String c) {
    }

    @Log
    @Override
    public void calculate(int a, int b, String c, double d) {
    }
}