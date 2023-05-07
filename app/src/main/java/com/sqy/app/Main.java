package com.sqy.app;

import com.sqy.controller.ProjectController;
import com.sqy.domain.Project;

public class Main {
    public static void main(String[] args) {
        // TODO: 06.05.2023 добавить спрингбут эплекейшон
        ProjectController projectController = new ProjectController();
        Project project = projectController.getProjectFromName("новый проектик");
        System.out.println(project);
    }
}