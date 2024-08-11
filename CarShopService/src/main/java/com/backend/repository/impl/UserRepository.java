package com.backend.repository.impl;

import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserRepository extends BaseRepository<User> {


    public UserRepository (){
    }


    @Override
    public boolean save(User user) {
        String sql = "insert into main.user values(DEFAULT,?,?,?,?,?,?)";
        return execute(sql, user.getId(), user.getUsername(), user.getPassword(),
                user.getRole(), user.getName(), user.getEmail(), user.getPhone());
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE main.user SET username = ?, password = ?, role = ?, name = ?, email = ?, phone = ? WHERE id = ?";
        return execute(sql, user.getUsername(), user.getPassword(),
                user.getRole(), user.getName(), user.getEmail(), user.getPhone(), user.getId());
    }

    @Override
    public boolean delete(User user) {
        String sql = "DELETE FROM main.user WHERE id = ?";
        return execute(sql, user.getId());
    }

    @Override
    public User findById (int id) {
        String sql = "SELECT * FROM main.user WHERE id = ?";
        return findById(sql, id);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM main.user";
        return findAll(sql);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        String sql = "select * from main.user where username = ? and password = ?";
        return findBy(sql, userName, password).get(0);
    }


    public User findByUserName(String userName) {
        String sql = "SELECT * FROM main.user WHERE username = ?";
        return findBy(sql, userName).get(0);
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
