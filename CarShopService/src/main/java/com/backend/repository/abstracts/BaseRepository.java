package com.backend.repository.abstracts;

import com.backend.repository.interfaces.CrudRepository;
import com.backend.util.DatabaseManager;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository <T> implements CrudRepository<T> {

    private static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();
    protected abstract T mapRowToEntity(ResultSet rs) throws SQLException;


    protected boolean execute(String sql, Object... params) {
        try (Connection conn = DATABASE_MANAGER.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected T findById(String sql, int id) {
        try (Connection conn = DATABASE_MANAGER.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<T> findBySomething (String sql, Object... params) {
        List<T> entities = new ArrayList<>();
        try (Connection conn = DATABASE_MANAGER.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entities.add(mapRowToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    protected List<T> findAll(String sql) {
        List<T> entities = new ArrayList<>();
        try (Connection conn = DATABASE_MANAGER.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                entities.add(mapRowToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException{
        int i = 1;
        for (Object object : params) {
            stmt.setObject(i++, params[i]);
        }
    }
}
