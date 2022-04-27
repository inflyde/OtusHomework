package com.otus.homework.shell;

import com.otus.homework.service.StudentTestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.util.Pair;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Locale;

import static java.util.Locale.getDefault;
import static java.util.Objects.isNull;

@ShellComponent
@RequiredArgsConstructor
public class TestingManagementCommands {

    private Pair<String, String> studentData;

    private final MessageSource messageSource;
    private final StudentTestingService studentTestingService;

    @ShellMethod(value = "Greetings command", key = {"g", "greetings", "l", "login"})
    public String greetings() {
        final Locale locale = getDefault();
        studentData = studentTestingService.greetingsStudent();
        return messageSource.getMessage(
                "message.login", new String[] {studentData.getFirst(), studentData.getSecond()}, locale);
    }

    @ShellMethod(value = "Start testing command", key = {"s", "start"})
    @ShellMethodAvailability(value = "checkUserData")
    public void startStudentTesting() {
        studentTestingService.runStudentTesting(studentData);
        studentData = null;
    }

    private Availability checkUserData() {
        return isNull(studentData)
                ? Availability.unavailable("Перед началом тестирования укажите своё имя и фамилию.")
                : Availability.available();
    }
}
