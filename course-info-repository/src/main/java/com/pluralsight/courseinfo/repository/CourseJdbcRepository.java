package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CourseJdbcRepository implements CourseRepository{
    private static final String H2_DATABASE_URL = "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM '/Users/opatel/Library/CloudStorage/OneDrive-AmericanExpress/Desktop/Example_Projects/course-info/db_init.sql'";
    private static final String INSERT_COURSE = """
            MERGE INTO Courses (id, name, length, url)
            VALUES (?, ?, ?, ?)
        """;
    private final DataSource dataSource;

    public CourseJdbcRepository(String dataBaseFile) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(H2_DATABASE_URL.formatted((dataBaseFile)));
        this.dataSource = jdbcDataSource;
    }

    @Override
    public void saveCourse(Course course) {
        // Connection is opened at the start of the block, and automatically closes at the end of the block.
        // This is because JdbcConnection implements the Autocloseable interface.
        // It will also be closed if an exception occurs.
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, course.id());
            statement.setString(2, course.name());
            statement.setLong(3, course.length());
            statement.setString(4, course.url());
            statement.execute();
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save " + course, e); // This is to protect against SQL Injections
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4));
                courses.add(course);
            }
            return Collections.unmodifiableList(courses);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to retrieve courses", e);
        }
    }
}
