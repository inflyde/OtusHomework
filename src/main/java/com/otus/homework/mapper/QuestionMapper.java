package com.otus.homework.mapper;

import com.otus.homework.dao.QuestionDao;

import java.util.List;

/**
 * Маппер вопросов из csv файла в список QuestionDao.
 */
public interface QuestionMapper {

    List<QuestionDao> mapLinesToObjects(List<String> csvLines);
}
