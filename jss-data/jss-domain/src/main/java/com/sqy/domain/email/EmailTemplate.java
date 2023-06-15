package com.sqy.domain.email;

public enum EmailTemplate {
    EMPLOYEE_EMAIL("employee.html");

    private final String templateFileName;

    EmailTemplate(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }
}
