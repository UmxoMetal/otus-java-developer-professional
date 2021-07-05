package ru.otus;

import com.google.common.base.Optional;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Joiner.on;
import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.Lists.transform;
import static java.util.stream.IntStream.rangeClosed;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/hw01-gradle-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l ./hw01-gradle/build/libs/hw01-gradle-0.1.jar
 */
public class HelloOtus {
    public static void main(String[] args) {
        var listItems = of("Hello", "otus");
        var upperCaseStrings = transform(listItems, String::toUpperCase);
        System.out.println(on(" ").join(upperCaseStrings));
        System.out.println(playFizzBuzz(fromNullable(1), fromNullable(100)));
    }

    private static String playFizzBuzz(Optional<Integer> startIndexOpt, Optional<Integer> endIndexOpt) {
        if (!startIndexOpt.isPresent() || !endIndexOpt.isPresent()) {
            throw new RuntimeException("Start index value and end index value should be present");
        }
        var transformedList
                = transform(rangeClosed(startIndexOpt.get(), endIndexOpt.get())
                        .boxed()
                        .collect(toImmutableList()),
                number -> number % 5 == 0 ? (number % 7 == 0 ? "FizzBuzz" : "Fizz") : (number % 7 == 0 ? "Buzz" : number));
        return on(", ").join(transformedList);
    }
}