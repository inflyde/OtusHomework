package com.otus.homework.service;

import com.otus.homework.dao.QuestionDao;
import com.otus.homework.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.System.*;
import static java.util.Collections.shuffle;
import static java.util.Locale.getDefault;

@Service
public class StudentTestingService {

    @Value("${homework.questions.filename}")
    private String FILENAME;

    private final QuestionMapper questionMapper;
    private final MessageSource messageSource;

    public StudentTestingService(QuestionMapper questionMapper, MessageSource messageSource) {
        this.questionMapper = questionMapper;
        this.messageSource = messageSource;
    }

    public void runStudentTesting() {
        final List<QuestionDao> questions = questionMapper.mapFileToObjects(FILENAME);
        shuffle(questions);
        Scanner consoleReader = new Scanner(in);
        final Locale locale = getDefault();

        out.println(messageSource.getMessage("message.enter-name", null, locale));
        final String userName = consoleReader.nextLine();

        out.println(messageSource.getMessage("message.enter-surname", null, locale));
        final String userSurname = consoleReader.nextLine();

        out.println(messageSource.getMessage("message.greetings", new String[] {userName, userSurname}, locale));

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
}
