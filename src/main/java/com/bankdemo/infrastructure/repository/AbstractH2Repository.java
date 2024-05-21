package com.bankdemo.infrastructure.repository;

import org.slf4j.*;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public abstract class AbstractH2Repository {
    protected Connection connection;
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractH2Repository.class);

    public AbstractH2Repository() {
        try {
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
                props.load(input);
            } catch (IOException e) {
                LOG.error("Error al cargar el archivo de propiedades de base de datos", e);
                throw new RuntimeException("Error al cargar el archivo de propiedades de base de datos", e);
            }

            String url = props.getProperty("database.url");
            String user = props.getProperty("database.user");
            String password = props.getProperty("database.password");

            connection = DriverManager.getConnection(url, user, password);
            LOG.info("Conexion exitosa a la BD");
            createTableIfNotExists();
        } catch (SQLException e) {
            LOG.error("Error de conexion a la BD", e);
            throw new RuntimeException("Error de conexion a la BD", e);
        }
    }

    protected abstract void createTableIfNotExists() throws SQLException;
}
