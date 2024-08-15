package com.backend;

import com.backend.controller.*;
import com.backend.factory.ControllerFactory;
import com.backend.util.MigrateLiquibase;
import com.backend.util.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App
{
    private static MigrateLiquibase migrateLiquibase;
    private static Controller loginRegisterController;
    private static Controller adminController;
    private static Controller clientController;
    private static Controller managerController;

    private static Session session = Session.getInstance();

    public static void main( String[] args )
    {
        init();
        migrateLiquibase.migrate();
        session.activate();

        while (session.isActive()) {

            switch (Session.getInstance().getStage()) {
                case HAVE_TO_LOGIN -> loginRegisterController.start();
                case ADMIN -> adminController.start();
                case CLIENT -> clientController.start();
                case MANAGER -> managerController.start();
                case EXIT -> {
                    System.out.println("Exit from system... not, again?");
                    return;
                }
            }

        }
    }

    private static  void init(){
        migrateLiquibase = new MigrateLiquibase();

        loginRegisterController = ControllerFactory.createController("login");
        adminController = ControllerFactory.createController("admin");
        clientController = ControllerFactory.createController("client");
        managerController = ControllerFactory.createController("manager");
    }



}
