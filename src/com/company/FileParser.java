package com.company;


import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {

    public static List<TabletInfo> readFileAndValidateData(File file) throws FileParserException {
        String fileName = file.getName();
        if (fileName.length() < 4 || !fileName.substring(fileName.length() - 4).equals(".txt")) {
            throw new FileParserException(file.getAbsolutePath(),"файл не текстовый");
        }
        List<String> stringInfos;
        try (Stream<String> stream = Files.lines(file.toPath())) {
            stringInfos = stream.collect(Collectors.toList());
        } catch (IOException | UncheckedIOException e) {
            throw new FileParserException(file.getAbsolutePath(), e.getMessage());
        }
        List<TabletInfo> tabletInfos = new LinkedList<>();
        try {
            for (String stringInfo : stringInfos) {
                TabletInfo info = parseAndValidateTableInfo(stringInfo);
                tabletInfos.add(info);
            }
        } catch (IllegalArgumentException e) {
            throw new FileParserException(file.getAbsolutePath(), e.getMessage());
        }
        return tabletInfos;
    }

    private static TabletInfo parseAndValidateTableInfo(String tableInfo) throws IllegalArgumentException {
        String[] splitedInfo = tableInfo.split(",");
        if (splitedInfo.length != 4) {
            throw new IllegalArgumentException(String.format("В строке %s неверное количество данных. Правильный формат строки: название, количество памяти, рейтинг, стоимость", tableInfo));
        }
        String tabletName = splitedInfo[0].trim();
        int memorySize = Integer.parseInt(splitedInfo[1].trim());
        if (memorySize < 1) {
            throw new IllegalArgumentException(String.format("Количество памяти %s задано не верно. Нужно целое число > 0", splitedInfo[1]));
        }
        int rating = Integer.parseInt(splitedInfo[2].trim());
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(String.format("Рейтинг %s задан не верно. Нужно целое число r, 1 <= r <= 5", splitedInfo[2]));
        }
        float price = Float.parseFloat(splitedInfo[3].trim());
        if (price < 0) {
            throw new IllegalArgumentException(String.format("Стоимость %s задана не верно. Нужно вещественное число > 0", splitedInfo[3]));
        }
        return new TabletInfo(tabletName, memorySize, rating, price);
    }
}
