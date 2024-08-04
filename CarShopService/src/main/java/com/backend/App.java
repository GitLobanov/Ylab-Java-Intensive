package com.backend;

import com.backend.controller.AdminController;
import com.backend.controller.ClientController;
import com.backend.controller.LoginRegisterController;
import com.backend.controller.ManagerController;
import com.backend.util.DemoData;
import com.backend.util.Session;

public class App
{

    private static DemoData demoData = new DemoData();


    public static void main( String[] args )
    {
        LoginRegisterController loginRegisterController = new LoginRegisterController();
        AdminController adminController = new AdminController();
        ClientController clientController = new ClientController();
        ManagerController managerController = new ManagerController();

        demoData.loadData();
        Session.getInstance().activate();

        while (Session.getInstance().isActive()){

            if (Session.getInstance().getStage() == Session.Stage.HAVE_TO_LOGIN) {
                loginRegisterController.start();

            } else if(Session.getInstance().getStage() == Session.Stage.ADMIN) {
                adminController.start();

            } else if(Session.getInstance().getStage() == Session.Stage.MANAGER) {
                managerController.start();

            } else if(Session.getInstance().getStage() == Session.Stage.CLIENT) {
                clientController.start();

            } else if (Session.getInstance().getStage() == Session.Stage.EXIT) {
                System.out.println("Exit from system... not, again?");
                return;
            }
        }

    }



}
