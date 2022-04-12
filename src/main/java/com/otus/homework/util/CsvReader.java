package com.otus.homework.util;

import java.util.List;

/**
 * Вспомогательный класс для чтения csv файла.
 */
public interface CsvReader {

    List<String> readCsvAndGetLines(String fileName);
}
