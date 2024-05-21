package com.bankdemo.infrastructure.repository;

import com.bankdemo.domain.model.Client;
import com.bankdemo.domain.repository.ClientRepository;

import java.sql.*;

public class H2ClientRepository extends AbstractH2Repository implements ClientRepository {

    protected void createTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS clients (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), cedula VARCHAR(255) UNIQUE)");
        }
    }

    @Override
    public int createClient(String name, String cedula) {
        String query = "INSERT INTO clients (name, cedula) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, cedula);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al crear el cliente en la BD", e);
            throw new RuntimeException("Error al crear el cliente en la BD", e);
        }
        return -1;
    }

    @Override
    public Client getClientByCedula(String cedula) {
        String query = "SELECT id, name, cedula FROM clients WHERE cedula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    return new Client(id, name, cedula);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener el cliente de la BD", e);
            throw new RuntimeException("Error al obtener el cliente de la BD", e);
        }
        return null;
    }

    @Override
    public Client getClientById(int clientId) {
        String query = "SELECT id, name, cedula FROM clients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String cedula = rs.getString("cedula");
                    return new Client(id, name, cedula);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener el cliente de la BD", e);
            throw new RuntimeException("Error al obtener el cliente de la BD", e);
        }
        return null;
    }
}
