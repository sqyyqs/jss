package com.sqy.dto.project;

import java.util.Arrays;

public record ProjectFileDto(byte[] file, String fileContentType) {
    @Override
    public String toString() {
        return "ProjectFileDto{" +
                "file=" + Arrays.toString(file).substring(0, 10) + "...]" +
                ", fileContentType='" + fileContentType + '\'' +
                '}';
    }
}
