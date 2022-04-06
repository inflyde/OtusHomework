package com.otus.homework.mapper;

import com.otus.homework.dao.QuestionDao;
import com.otus.homework.mapper.impl.QuestionMapperImpl;
import com.otus.homework.util.CsvReader;
import com.otus.homework.util.impl.CsvReaderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public class QuestionMapperTest {

    private final QuestionMapper questionMapper = new QuestionMapperImpl();
    private final CsvReader csvReader = new CsvReaderImpl();

    @Test
    public void readCsvAndGetLinesTest() {
        List<String> expected = new ArrayList<>();
        expected.add(String.join(";", "Вопрос1?", "1", "2", "3", "4", "2"));
        expected.add(String.join(";","Вопрос2?", "А", "Б", "В", "Г", "В"));

        List<String> actual = csvReader.readCsvAndGetLines("test.csv");
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void mapLinesToObjectsTest() {
        List<QuestionDao> expected = new ArrayList<>();
        expected.add(new QuestionDao("Вопрос1?", List.of("1", "2", "3", "4"), "2"));
        expected.add(new QuestionDao("Вопрос2?", List.of("А", "Б", "В", "Г"), "В"));
        List<String> expectedQuestionStringList = expected.stream().map(QuestionDao::toString).collect(toList());

        List<String> testLinesForActual = new ArrayList<>();
        testLinesForActual.add(String.join(";", "Вопрос1?", "1", "2", "3", "4", "2"));
        testLinesForActual.add(String.join(";","Вопрос2?", "А", "Б", "В", "Г", "В"));
        List<QuestionDao> actual = questionMapper.mapLinesToObjects(testLinesForActual);
        List<String> actualQuestionStringList = actual.stream().map(QuestionDao::toString).collect(toList());

        Assertions.assertIterableEquals(expectedQuestionStringList, actualQuestionStringList);
    }
}
