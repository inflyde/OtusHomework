package com.otus.homework.dao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

import java.util.List;

@AllArgsConstructor
@Getter
@Immutable
@ToString
public class QuestionDao {

    private String question;

    private List<String> answers;

    private String correctAnswer;
}
