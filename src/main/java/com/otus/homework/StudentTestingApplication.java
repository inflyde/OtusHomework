package com.otus.homework;

import com.otus.homework.service.StudentTestingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StudentTestingApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(StudentTestingApplication.class, args);

		StudentTestingService studentTestingService = ctx.getBean(StudentTestingService.class);
		studentTestingService.runStudentTesting();
	}
}
