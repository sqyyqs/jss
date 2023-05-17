package com.sqy.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConfiguration {
    public JdbcConfiguration() throws IOException {
        InputStream input = JdbcConfiguration.class.getClassLoader().getResourceAsStream("database.properties");
        Properties prop = new Properties();
        prop.load(input);
        login = prop.getProperty("login");
        password = prop.getProperty("password");
        url = prop.getProperty("url");
    }

    private final String login;
    private final String password;
    private final String url;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }
}
