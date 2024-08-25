package com.backend.repository.impl;

import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;
import com.backend.util.db.SQLRequest;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
    }

    @Override
    public boolean save(User user) {
        String sql = SQLRequest.INSERT_ALL_INTO_USER;
        return execute(sql, user.getUsername(), user.getPassword(),
                user.getRole().name(), user.getName(), user.getEmail(), user.getPhone());
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE main.user SET password = ?, role = ?, name = ?, email = ?, phone = ? WHERE username = ?";
        return execute(sql, user.getPassword(),
                user.getRole().name(), user.getName(), user.getEmail(), user.getPhone(), user.getUsername());
    }

    @Override
    public boolean delete(User user) {
        String sql = "DELETE FROM main.user WHERE username = ?";
        return execute(sql, user.getUsername());
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM main.user WHERE id = ?";
        return findById(sql, id);
    }

    @Override
    public List<User> findAll() {
        String sql = SQLRequest.SELECT_ALL_FROM_USER;
        return findAll(sql);
    }

    public List<User> findClientsByManager(String manager) {
        String sql = SQLRequest.SELECT_CLIENTS_BY_MANAGER;
        return findBy(sql, manager);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        String sql = "select * from main.user where username = ? and password = ?";
        return findBy(sql, userName, password).get(0);
    }


    public User findByUserName(String userName) {
        String sql = "SELECT * FROM main.user WHERE username = ?";
        return findBy(sql, userName).get(0);
    }

    public List<User> findUsersByRole(User.Role role) {
        String sql = "SELECT * FROM main.user WHERE role = ?";
        return findBy(sql, role.name());
    }

    public List<User> findEmployees() {
        String sql = "SELECT * FROM main.user WHERE role = 'MANAGER' or role = 'ADMIN'";
        return findBy(sql);
    }

    public List<User> search(User user) {
        StringBuilder sql = new StringBuilder("SELECT * FROM main.\"user\" u WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (user.getId() > 0) {
            sql.append(" AND u.id = ?");
            parameters.add(user.getId());
        }

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            sql.append(" AND u.username LIKE ?");
            parameters.add("%" + user.getUsername() + "%");
        }

        if (user.getRole() != null) {
            sql.append(" AND u.role = ?");
            parameters.add(user.getRole().toString()); // Преобразование Enum в строку
        }

        if (user.getName() != null && !user.getName().isEmpty()) {
            sql.append(" AND u.name LIKE ?");
            parameters.add("%" + user.getName() + "%"); // Используем LIKE для частичного совпадения
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            sql.append(" AND u.email = ?");
            parameters.add(user.getEmail());
        }

        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            sql.append(" AND u.phone LIKE ?");
            parameters.add("%" + user.getPhone() + "%");
        }

        return findBy(sql.toString(), parameters.toArray());
    }


    @Override
    protected User mapRowToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String userName = rs.getString("userName");
        String password = rs.getString("password");
        User.Role role = User.Role.valueOf(rs.getString("role").toUpperCase());
        String name = rs.getString("name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        return new User(id, userName, password, role, name, email, phone);
    }
}
