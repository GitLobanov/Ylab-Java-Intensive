package com.backend.factory;

import com.backend.controller.*;

public class ControllerFactory {

    public static Controller createController(String controllerName) {

        switch (controllerName) {
            case "login":
                return new LoginRegisterController();
            case "admin":
                return new AdminController();
            case "client":
                return new ClientController();
            case "manager":
                return new ManagerController();
            default:
                throw new IllegalArgumentException("Unknown controller name: " + controllerName);
        }

    }

}
