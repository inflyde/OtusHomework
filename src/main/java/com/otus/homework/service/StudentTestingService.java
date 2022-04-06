package com.otus.homework.service;

import com.otus.homework.dao.QuestionDao;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Сервис управления тестированием студентов.
 */
public interface StudentTestingService {

    Pair<String, String> greetingsStudent();

    Map<Integer, Boolean> testing(List<QuestionDao> questions);

    void resultCheck(List<QuestionDao> questions, Map<Integer, Boolean> resultOfTesting);

    void runStudentTesting();
}
