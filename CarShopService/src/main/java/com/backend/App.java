package com.backend;

import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import com.backend.util.MigrateLiquibase;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.database.jvm.JdbcConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App
{


    public static void main( String[] args )
    {

//        MigrateLiquibase migrateLiquibase = new MigrateLiquibase();
//        migrateLiquibase.migrate();

        UserRepository userRepository = new UserRepository();

        User user = userRepository.findById(1);
        System.out.println(user);

        userRepository.findAll().forEach(System.out::println);

//        LoginRegisterController loginRegisterController = new LoginRegisterController();
//        AdminController adminController = new AdminController();
//        ClientController clientController = new ClientController();
//        ManagerController managerController = new ManagerController();
//
//        PRE_INITIALISATION.loadData();
//        Session.getInstance().activate();
//
//        Session session = Session.getInstance();
//        session.activate();
//
//        while (session.isActive()) {
//
//            switch (Session.getInstance().getStage()) {
//                case HAVE_TO_LOGIN -> loginRegisterController.start();
//                case ADMIN -> adminController.start();
//                case CLIENT -> clientController.start();
//                case MANAGER -> managerController.start();
//                case EXIT -> {
//                    System.out.println("Exit from system... not, again?");
//                    return;
//                }
//            }
//
//        }
    }



}
