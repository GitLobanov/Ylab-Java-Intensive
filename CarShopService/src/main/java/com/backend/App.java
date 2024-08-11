package com.backend;

import com.backend.controller.AdminController;
import com.backend.controller.ClientController;
import com.backend.controller.LoginRegisterController;
import com.backend.controller.ManagerController;
import com.backend.util.MigrateLiquibase;
import com.backend.util.Session;

public class App
{


    public static void main( String[] args )
    {

        MigrateLiquibase migrateLiquibase = new MigrateLiquibase();
        migrateLiquibase.migrate();

        LoginRegisterController loginRegisterController = new LoginRegisterController();
        AdminController adminController = new AdminController();
        ClientController clientController = new ClientController();
        ManagerController managerController = new ManagerController();

        Session session = Session.getInstance();
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



}
