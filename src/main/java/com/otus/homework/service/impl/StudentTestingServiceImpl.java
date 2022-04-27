package com.otus.homework.service.impl;

import com.otus.homework.dao.QuestionDao;
import com.otus.homework.mapper.QuestionMapper;
import com.otus.homework.service.StudentTestingService;
import com.otus.homework.util.CsvReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.System.*;
import static java.util.Collections.shuffle;
import static java.util.Locale.getDefault;

@Service
public class StudentTestingServiceImpl implements StudentTestingService {

    @Value("${homework.questions.filename}")
    private String FILENAME;

    private final CsvReader csvReader;
    private final QuestionMapper questionMapper;
    private final MessageSource messageSource;

    private static final Locale locale = getDefault();
    private static final Scanner consoleReader = new Scanner(in);

    public StudentTestingServiceImpl(CsvReader csvReader,
                                     QuestionMapper questionMapper,
                                     MessageSource messageSource) {
        this.csvReader = csvReader;
        this.questionMapper = questionMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Pair<String, String> greetingsStudent() {
        out.println(messageSource.getMessage("message.enter-name", null, locale));
        final String studentName = consoleReader.nextLine();

        out.println(messageSource.getMessage("message.enter-surname", null, locale));
        final String studentSurname = consoleReader.nextLine();

        return Pair.of(studentName, studentSurname);
    }

    @Override
    public Map<Integer, Boolean> testing(List<QuestionDao> questions) {
        final Map<Integer, Boolean> resultOfTesting = new HashMap<>();
        for (int questionOrder = 1; questionOrder < questions.size() + 1; questionOrder++) {
            QuestionDao question = questions.get(questionOrder - 1);
            out.println(messageSource.getMessage("message.question",
                    new Object[] {questionOrder, question.getQuestion()}, locale));
            out.println(messageSource.getMessage("message.answers", null, locale));

            List<String> answers = question.getAnswers();
            for(int answerOrder = 1; answerOrder < answers.size() + 1; answerOrder++) {
                out.println(messageSource.getMessage("message.enumerate-answers",
                        new Object[] {answerOrder, answers.get(answerOrder - 1)}, locale));
            }
            out.println(messageSource.getMessage("message.enter-answer-number", null, locale));
            int userAnswer = parseInt(consoleReader.nextLine());
            resultOfTesting.put(questionOrder, answers.get(userAnswer - 1).equals(question.getCorrectAnswer()));
        }
        return resultOfTesting;
    }

    @Override
    public void resultCheck(List<QuestionDao> questions, Map<Integer, Boolean> resultOfTesting) {
        out.println(messageSource.getMessage("message.test-result", null, locale));
        resultOfTesting.forEach((questionOrder, isCorrectAnswer) -> out.println(
                messageSource.getMessage("message.question", new Object[] {questionOrder,
                        isCorrectAnswer
                                ? messageSource.getMessage("message.correct-answer", null, locale)
                                : messageSource.getMessage("message.wrong-answer", null, locale)
                                + questions.get(questionOrder - 1).getCorrectAnswer()
                }, locale))
        );
    }

    @Override
    public void runStudentTesting(Pair<String, String> studentData) {
        final List<String> csvLines = csvReader.readCsvAndGetLines(FILENAME);
        final List<QuestionDao> questions = questionMapper.mapLinesToObjects(csvLines);
        shuffle(questions);

        final String studentName = studentData.getFirst();
        final String studentSurname = studentData.getSecond();
        out.println(messageSource.getMessage("message.greetings", new String[] {studentName, studentSurname}, locale));

        final Map<Integer, Boolean> resultOfTesting = testing(questions);
        resultCheck(questions, resultOfTesting);
    }
}
