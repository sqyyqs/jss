package com.sqy.repository;

import com.sqy.configuration.DataStorageConfiguration;
import com.sqy.domain.Project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.NoSuchObjectException;
import java.util.List;

public class ProjectDataStorage implements ProjectRepository {
    Path filePath = DataStorageConfiguration.getPath();

    @Override
    public void create(Project project) throws IOException {
        Files.write(filePath, (project.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void update(Project project) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("id=" + project.getId())) {
                lines.set(i, project + "\n");
                Files.write(filePath, lines, StandardCharsets.UTF_8);
                return;
            }
        }
        throw new NoSuchObjectException("Project with id = " + project.getId() + " was not found.");
    }

    @Override
    public Project getById(Long id) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line.contains("id=" + id)) {
                return projectOf(line);
            }
        }
        throw new NoSuchObjectException("Project with id = " + id + " was not found.");
    }

    @Override
    public List<Project> getAll() throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        return lines.stream()
                .map(ProjectDataStorage::projectOf)
                .toList();
    }

    @Override
    public void deleteById(Long id) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("id=" + id)) {
                lines.remove(i);
                Files.write(filePath, lines, StandardCharsets.UTF_8);
                return;
            }
        }
        throw new NoSuchObjectException("Project with id = " + id + " was not found.");
    }

    private static Project projectOf(String projectStr) {
        Project project = new Project();
        String[] pairs = projectStr.substring(projectStr.indexOf("{") + 1, projectStr.indexOf("}")).split(", ");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id" -> project.setId(Long.parseLong(value));
                case "code" -> project.setCode(value);
                case "name" -> project.setName(value);
                case "description" -> {
                    if (!value.equals("'null'")) {
                        project.setDescription(value);
                    }
                }
                case "projectStatus" -> project.setProjectStatus(Project.ProjectStatus.valueOf(value));
                default -> throw new IllegalArgumentException("Invalid project string: " + projectStr);
            }
        }
        return project;
    }
}
