package com.otus.homework.mapper.impl;

import com.otus.homework.dao.QuestionDao;
import com.otus.homework.mapper.QuestionMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public List<QuestionDao> mapLinesToObjects(List<String> csvLines) {
        return csvLines.stream()
                .map(this::mapLineToObject)
                .collect(toList());

    }

    private QuestionDao mapLineToObject(String questionLine) {
        final List<String> lineValues = Arrays.asList(questionLine.split(";"));
        final String question = lineValues.get(0);
        final List<String> answers = lineValues.subList(1, lineValues.size() - 1);
        final String correctAnswer = lineValues.get(lineValues.size() - 1).replace("\r", "");
        return new QuestionDao(question, answers, correctAnswer);
    }
}
