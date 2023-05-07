package com.sqy.utils;

import com.sqy.domain.Project;

import java.util.concurrent.ThreadLocalRandom;

public final class ProjectUtil {
    private ProjectUtil() {
        throw new RuntimeException();
    }

    public static String nameOfWithRandomNumber(Project project) {
        return String.format("%d: %s",
                ThreadLocalRandom.current().nextInt(15),
                project.getName());
    }
}
