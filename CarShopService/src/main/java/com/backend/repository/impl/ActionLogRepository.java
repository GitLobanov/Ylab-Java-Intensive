package com.backend.repository.impl;

import com.backend.model.ActionLog;
import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.abstracts.BaseRepository;
import com.backend.repository.interfaces.CrudRepository;
import com.backend.util.db.SQLRequest;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ActionLogRepository extends BaseRepository<ActionLog> {

    @Override
    public boolean save(ActionLog actionLog) {
        String sql = SQLRequest.INSERT_ALL_INTO_ACTION;
        return execute(sql, actionLog.getUser().getId(), actionLog.getActionType().toString(), actionLog.getActionDateTime(),
                actionLog.getMessage());
    }

    @Override
    public boolean update(ActionLog actionLog) {
        String sql = "UPDATE main.action SET user = ?, type = ?, date = ?, message = ? WHERE id = ?";
        return execute(sql, actionLog.getUser().getId(), actionLog.getActionType().toString(), actionLog.getActionDateTime(),
                actionLog.getMessage(), actionLog.getId());
    }

    @Override
    public boolean delete(ActionLog actionLog) {
        String sql = "DELETE FROM main.car WHERE id = ?";
        return execute(sql, actionLog.getId());
    }

    @Override
    public ActionLog findById(int id) {
        String sql = "SELECT * FROM main.action WHERE id = ?";
        return findById(sql, id);
    }

    @Override
    public List<ActionLog> findAll() {
        String sql = SQLRequest.SELECT_ALL_FROM_ACTION;
        return findAll(sql);
    }

    public List<ActionLog> findByUser(User user) {
        String sql = "SELECT * FROM main.action WHERE user = ?";
        findBy(sql, user.getId());
        return null;
    }

    @Override
    protected ActionLog mapRowToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idUser = rs.getInt("user");
        UserRepository userRepository = new UserRepository();
        User user = userRepository.findById(idUser);
        ActionLog.ActionType type = ActionLog.ActionType.valueOf(rs.getString("type").toUpperCase());
        Date date = rs.getDate("date");
        String message = rs.getString("message");
        return new ActionLog(id, user, type, date ,message);
    }
}
