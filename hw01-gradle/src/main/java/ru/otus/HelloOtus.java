package ru.otus;

import com.google.common.collect.ImmutableList;

import static com.google.common.collect.Lists.transform;
import static com.google.common.base.Joiner.on;

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
        var listItems = ImmutableList.of("Hello", "otus");
        var upperCaseStrings = transform(listItems, String::toUpperCase);
        System.out.println(on(" ").join(upperCaseStrings));
    }
}