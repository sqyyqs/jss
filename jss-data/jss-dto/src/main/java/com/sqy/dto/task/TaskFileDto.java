package com.sqy.dto.task;

import java.util.Arrays;

public record TaskFileDto(byte[] file, String fileContentType) {
    @Override
    public String toString() {
        return "TaskFileDto{" +
                "file=" + Arrays.toString(file).substring(0, 10) + "...]" +
                ", fileContentType='" + fileContentType + '\'' +
                '}';
    }
}
