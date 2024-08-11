package com.backend.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

@Testcontainers
public class DatabaseTest {

    @Container
    public GenericContainer<?> postgresContainer = new GenericContainer<>("postgres:latest")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "car_shop_db")
            .withEnv("POSTGRES_USER", "postgres")
            .withEnv("POSTGRES_PASSWORD", "password");

    @Test
    @DisplayName("Тестирование подключения")
    void testCustomContainer() throws Exception {
        String address = postgresContainer.getHost();
        Integer port = postgresContainer.getFirstMappedPort();

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/car_shop_db", address, port);

        try (Connection conn = DriverManager.getConnection(jdbcUrl, "postgres", "password")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            if (rs.next()) {
                int result = rs.getInt(1);
                assert result == 1;
            }
        }
    }

    @Test
    @DisplayName("Тестирование миграции")
    void testMigration() throws Exception {

        String address = postgresContainer.getHost();
        Integer port = postgresContainer.getFirstMappedPort();

        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/car_shop_db", address, port);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, "postgres", "password")) {

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
            Liquibase liquibase = new Liquibase("/db/changelog/db.changelog-master.xml", resourceAccessor, database);


            liquibase.update("");

            System.out.println("Liquibase update executed successfully.");
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }

}
