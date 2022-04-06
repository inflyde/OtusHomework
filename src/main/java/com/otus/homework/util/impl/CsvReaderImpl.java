package com.otus.homework.util.impl;

import com.otus.homework.util.CsvReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CsvReaderImpl implements CsvReader {

    @Override
    public List<String> readCsvAndGetLines(String fileName) {
        final List<String> csvLines = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            csvLines.addAll(bufferedReader.lines().collect(toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvLines;
    }
}
