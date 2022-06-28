package ru.otus.log.dto;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import static java.util.Arrays.stream;
import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MethodInfoDto {
    private final String name;
    private final String returnType;
    private final String parameterTypes;
    private final List<String> parameterNames;

    public MethodInfoDto(Method method) {
        this.name = method.getName();
        this.returnType = method.getReturnType().getName();
        this.parameterTypes = Arrays.toString(method.getParameterTypes());
        parameterNames = stream(method.getParameters())
                .map(Parameter::getName)
                .collect(toList());
    }
}