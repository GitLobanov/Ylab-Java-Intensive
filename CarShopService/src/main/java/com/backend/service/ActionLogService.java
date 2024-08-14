package com.backend.service;

import com.backend.model.ActionLog;
import com.backend.repository.impl.ActionLogRepository;
import com.backend.util.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;

public class ActionLogService {

    private ActionLogRepository actionLogRepository;
    private static final Logger logger = LoggerFactory.getLogger("UserActions");

    public ActionLogService() {
        actionLogRepository = new ActionLogRepository();
    }

    public void logAction(ActionLog.ActionType type, String message) {
        Session session = Session.getInstance();
        ActionLog log = new ActionLog(0, session.getUser(),type, Date.valueOf(LocalDate.now().toString()), message);
        logger.info("User: {}, Action: {}, Details: {}", log.getUser().getUsername(), log.getActionType(), log.getMessage());
        actionLogRepository.save(log);
    }

}
