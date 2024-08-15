package com.backend.view.handler;

import com.backend.service.ActionLogService;
import com.backend.util.Session;

public class ActionLogMenuHandler extends BaseHandler{

    ActionLogService actionLogService = new ActionLogService();

    @Override
    public void handlerMenu() {

    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void search() {
        String queryAllUsers = actionLogService.formingQuerySearchActionLogs();
        actionLogService.getActionLogByUserSearch(queryAllUsers, null);
    }

    public void searchByUser (){
        String query = actionLogService.formingQuerySearchActionLogs();
        actionLogService.getActionLogByUserSearch(query, Session.getInstance().getUser());
    }

    public void getByUser (){
        printList(actionLogService.getUserActionLog(Session.getInstance().getUser()));
    }

    @Override
    public void viewAll() {
    }
}
