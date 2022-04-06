package com.otus.homework.mapper;

import com.otus.homework.dao.QuestionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public class QuestionMapperTest {

    private final QuestionMapper questionMapper = new QuestionMapper();

    @Test
    public void mapFileToObjectsTest() {

        List<QuestionDao> expected = new ArrayList<>();
        expected.add(new QuestionDao("Вопрос1?", List.of("1", "2", "3", "4"), "2"));
        expected.add(new QuestionDao("Вопрос2?", List.of("А", "Б", "В", "Г"), "В"));
        List<String> expectedQuestionStringList = expected.stream().map(QuestionDao::toString).collect(toList());

        List<QuestionDao> actual = questionMapper.mapFileToObjects("test.csv");
        List<String> actualQuestionStringList = actual.stream().map(QuestionDao::toString).collect(toList());

        Assertions.assertIterableEquals(expectedQuestionStringList, actualQuestionStringList);
    }
}
