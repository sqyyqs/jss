package com.sqy.repository;

import com.sqy.configuration.JdbcConfiguration;
import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProjectJdbcRepository implements ProjectRepository {
    JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    public ProjectJdbcRepository() throws IOException {
    }

    private static final String SQL_DELETE_BY_ID = "delete from project where project_id = ?";
    private static final String SQL_INSERT_PROJECT = "insert into project (project_id, code, name, description, status) values (default, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PROJECT = "update project set code = ?, name = ?, description = ?, status = ? where project_id = ?";
    private static final String SQL_SELECT_ALL = "select project_id, code, name, description, status from project";
    private static final String SQL_SELECT_BY_ID = "select project_id, code, name, description, status from project where project_id = ?";
    private static final String SQL_FIND_BASE_QUERY = """
            select project.project_id as project_id,
                   code,
                   name,
                   description        as description,
                   project.status     as status
            from project
                     join project_member pm on project.project_id = pm.project_id
                     join employee e on e.employee_id = pm.employee_id
                     """;

    @Override
    public void create(Project project) {
        try (Connection connection = jdbcConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PROJECT)) {
            statement.setString(1, project.getCode());
            statement.setString(2, project.getName());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getProjectStatus().getStatusString());
            statement.setLong(5, project.getId());
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void update(Project project) {
        try (Connection connection = jdbcConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PROJECT)) {
            statement.setString(1, project.getCode());
            statement.setString(2, project.getName());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getProjectStatus().getStatusString());
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public List<Project> searchByProps(Map<String, Object> props) {
        if (props.isEmpty()) {
            return getAll();
        }
        StringBuilder query = new StringBuilder(SQL_FIND_BASE_QUERY);
        props.forEach((name, value) -> {
            switch (name) {
                case "employeeId" -> query.append("e.employee_id = ").append(value).append(" and ");
                case "employeeFirstName" -> query.append("first_name = ").append(value).append(" and ");
                case "employeePosition" -> query.append("position = ").append(value).append(" and ");
                case "employeeMiddleName" -> query.append("middle_name = ").append(value).append(" and ");
                case "employeeAccount" -> query.append("account = ").append(value).append(" and ");
                case "employeeEmail" -> query.append("email = ").append(value).append(" and ");
                case "employeeStatus" -> query.append("e.status = ").append(value).append(" and ");
                case "projectCode" -> query.append("code = ").append(value).append(" and ");
                case "projectName" -> query.append("name = ").append(value).append(" and ");
                case "projectDescription" -> query.append("description = ").append(value).append(" and ");
                case "projectStatus" -> query.append("project.status = ").append(value).append(" and ");
            }
        });
        if (" and ".equals(query.substring(query.length() - 5))) {
            query.delete(query.length() - 5, query.length());
        }
        try (Connection connection = jdbcConfiguration.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());
            List<Project> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getProjectFrom(resultSet));
            }
            return result;
        } catch (SQLException ignored) {
        }
        return Collections.emptyList();
    }

    @Override
    /*todo @Nullable*/
    public Project getById(Long id) {
        try (Connection connection = jdbcConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProjectFrom(resultSet);
            }
        } catch (SQLException ignored) {
        }
        return null;
    }

    @Override
    public List<Project> getAll() {
        try (Connection connection = jdbcConfiguration.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            List<Project> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getProjectFrom(resultSet));
            }
            return result;
        } catch (SQLException ignored) {
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = jdbcConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    private static Project getProjectFrom(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                ProjectStatus.valueOf(resultSet.getString("status"))
        );
    }
}
