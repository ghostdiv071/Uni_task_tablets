package com.company;

public class FileParserException extends Exception {
    private final String fileName;
    private final String error;

    public FileParserException(String fileName, String error) {
        super();
        this.fileName = fileName;
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("При чтении файла %s произошла ошибка:\n%s",
                fileName, error);
    }
}
