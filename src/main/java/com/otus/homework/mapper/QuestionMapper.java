package com.otus.homework.mapper;

import com.otus.homework.dao.QuestionDao;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class QuestionMapper {

    public List<QuestionDao> mapFileToObjects(String filename) {
        final List<QuestionDao> questions = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            Scanner scanner = new Scanner(resource.getInputStream()).useDelimiter("\n");
            while (scanner.hasNext()) {
                questions.add(mapLineToObject(scanner.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private QuestionDao mapLineToObject(String questionLine) {
        final List<String> lineValues = Arrays.asList(questionLine.split(";"));
        final String question = lineValues.get(0);
        final List<String> answers = lineValues.subList(1, lineValues.size() - 1);
        final String correctAnswer = lineValues.get(lineValues.size() - 1).replace("\r", "");
        return new QuestionDao(question, answers, correctAnswer);
    }
}
