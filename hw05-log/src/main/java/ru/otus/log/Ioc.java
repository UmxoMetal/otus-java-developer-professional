package ru.otus.log;

import java.lang.reflect.Proxy;
import ru.otus.log.annotation.Log;
import ru.otus.log.dto.MethodInfoDto;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

class Ioc<I> {
    private static final String METHOD_EXECUTION_MSG = "EXECUTED METHOD: %s%s\n";

    @SuppressWarnings("unchecked")
    <C> C createProxyInstanceAngLogArguments(Class<C> clazz, I instance) {
        var methodInfoDtos = stream(instance.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .map(MethodInfoDto::new)
                .collect(toSet());

        return (C) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{clazz}, (proxy, method, methodArgs) -> {
                    var methodDetails = new MethodInfoDto(method);
                    if (methodInfoDtos.contains(methodDetails)) {
                        var parameterNames = methodDetails.getParameterNames();
                        var params = new StringBuilder();
                        for (int i = 0; i < methodArgs.length; i++) {
                            params.append(", ").append(parameterNames.get(i)).append(": ").append(methodArgs[i]);
                        }
                        System.out.printf(METHOD_EXECUTION_MSG, method.getName(), params.toString());
                    }
                    return method.invoke(instance, methodArgs);
                });
    }
}