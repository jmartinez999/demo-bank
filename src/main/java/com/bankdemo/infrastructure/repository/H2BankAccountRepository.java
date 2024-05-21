package com.bankdemo.infrastructure.repository;

import com.bankdemo.domain.event.DepositEvent;
import com.bankdemo.domain.event.WithdrawEvent;
import com.bankdemo.domain.event.DomainEvent;
import com.bankdemo.domain.repository.BankAccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class H2BankAccountRepository extends AbstractH2Repository implements BankAccountRepository {

    protected void createTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS events (accountId INT, eventType VARCHAR(255), amount DOUBLE,occurredOn TIMESTAMP)");
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (id INT AUTO_INCREMENT PRIMARY KEY, clientId INT)");
        }
    }

    @Override
    public void saveEvent(int accountId, DomainEvent event) {
        String query = "INSERT INTO events (accountId, eventType, amount, occurredOn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            if (event instanceof DepositEvent) {
                stmt.setString(2, "DepositEvent");
                stmt.setDouble(3, ((DepositEvent) event).getAmount());
            } else if (event instanceof WithdrawEvent) {
                stmt.setString(2, "WithdrawEvent");
                stmt.setDouble(3, ((WithdrawEvent) event).getAmount());
            }
            stmt.setTimestamp(4, new Timestamp(event.getOccurredOn().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error al guardar el evento en la BD", e);
            throw new RuntimeException("Error al guardar el evento en la BD", e);
        }
    }


    @Override
    public List<DomainEvent> getEvents(int accountId) {
        List<DomainEvent> events = new ArrayList<>();
        String query = "SELECT eventType, amount, occurredOn FROM events WHERE accountId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String eventType = rs.getString("eventType");
                    double amount = rs.getDouble("amount");
                    java.util.Date occurredOn = new Date(rs.getTimestamp("occurredOn").getTime());
                    if (eventType.equals("DepositEvent")) {
                        DepositEvent event = new DepositEvent(amount);
                        event.setOccurredOn(occurredOn);
                        events.add(event);
                    } else if (eventType.equals("WithdrawEvent")) {
                        WithdrawEvent event = new WithdrawEvent(amount);
                        event.setOccurredOn(occurredOn);
                        events.add(event);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener los eventos de la BD", e);
            throw new RuntimeException("Error al obtener los eventos de la BD", e);
        }
        return events;
    }

    @Override
    public double getBalance(int accountId) {
        double balance = 0.0;
        String query = "SELECT eventType, amount FROM events WHERE accountId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String eventType = rs.getString("eventType");
                    double amount = rs.getDouble("amount");
                    if (eventType.equals("DepositEvent")) {
                        balance += amount;
                    } else if (eventType.equals("WithdrawEvent")) {
                        balance -= amount;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener el saldo de la BD", e);
            throw new RuntimeException("Error al obtener el saldo de la BD", e);
        }
        return balance;
    }

    @Override
    public int createAccount(int clientId) {
        String query = "INSERT INTO accounts (clientId) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al crear la cuenta en la BD", e);
            throw new RuntimeException("Error al crear la cuenta en la BD", e);
        }
        return -1;
    }

    @Override
    public boolean accountBelongsToClient(int accountId, String cedula) {
        String query = "SELECT a.id FROM accounts a JOIN clients c ON a.clientId = c.id WHERE a.id = ? AND c.cedula = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            stmt.setString(2, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOG.error("Error al verificar si la cuenta pertenece al cliente en la BD", e);
            throw new RuntimeException("Error al verificar si la cuenta pertenece al cliente en la BD", e);
        }
    }
}