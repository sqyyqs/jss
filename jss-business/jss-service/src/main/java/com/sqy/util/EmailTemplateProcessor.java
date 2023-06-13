package com.sqy.util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.sqy.domain.email.EmailTemplate;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EmailTemplateProcessor {
    private static final DefaultMustacheFactory DEFAULT_MUSTACHE_FACTORY = new DefaultMustacheFactory();

    private static final String TEMPLATES = "templates/";
    private static final String START_MARK = "${";
    private static final String END_MARK = "}";

    @Nullable
    public static String prepareMessage(EmailTemplate template, Map<String, String> params) {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                try (InputStream is = contextClassLoader
                        .getResourceAsStream(TEMPLATES + template.getTemplateFileName())) {
                    if (is != null) {
                        return processParams(new InputStreamReader(is, StandardCharsets.UTF_8), params);
                    }
                }
            }
        } catch (IOException e) {
            log.info("Invoke prepareMessage({}, {}) with exception.", template, params, e);
        }
        return null;
    }


    private static String processParams(Reader reader, Map<String, String> params) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return fillTemplate(bufferedReader.lines().collect(Collectors.joining("\n")), params);
        }
    }

    private static String fillTemplate(String template, Map<String, String> params) {
        Mustache mustache = DEFAULT_MUSTACHE_FACTORY.compile(new StringReader(template), template, START_MARK, END_MARK);
        StringWriter writer = new StringWriter();
        mustache.execute(writer, params);
        return writer.toString();
    }

}
