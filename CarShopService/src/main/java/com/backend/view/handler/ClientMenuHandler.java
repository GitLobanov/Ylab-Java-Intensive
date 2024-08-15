package com.backend.view.handler;

import com.backend.model.User;
import com.backend.service.impl.ClientService;
import com.backend.service.impl.ManagerService;
import com.backend.util.ErrorResponses;
import com.backend.util.Session;
import com.backend.util.SuccessResponses;

import java.util.List;

public class ClientMenuHandler extends BaseHandler{

    ClientService clientService = new ClientService();
    ManagerService managerService = new ManagerService();

    @Override
    public void handlerMenu() {

    }

    @Override
    public void create() {

        String userName = getField("username");

        while (clientService.getClientByUsername(userName) != null) {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST);
            userName = getField("username");
        }

        String password = getField("password");
        User.Role role = User.Role.CLIENT;
        String name = getField("name");
        String email = getField("email");
        String phone = getField("phone");

        User client = new User(0, userName, password, role, name, email, phone);
        if (client!=null) clientService.addClient(client); {
            SuccessResponses.printCustomMessage("Client added successfully. \n" + client);
        }
    }

    @Override
    public void update() {
        System.out.println("Enter username of the client to update: ");
        String userName = scanner.nextLine();

        if (clientService.getClientByUsername(userName)==null) {
            ErrorResponses.printCustomMessage("Client not found.");
            return;
        }

        String password = getNewField("password");
        String name = getNewField("name");
        String email = getNewField("email");
        String phone = getNewField("phone");

        User existingClient = clientService.getClientByUsername(userName);
        User updatedEmployee = new User(
                existingClient.getId(),
                userName,
                password.isEmpty() ? existingClient.getPassword() : password,
                existingClient.getRole(),
                name.isEmpty() ? existingClient.getName() : name,
                email.isEmpty() ? existingClient.getEmail() : email,
                phone.isEmpty() ? existingClient.getPhone() : phone
        );

        if (clientService.updateClient(userName, updatedEmployee)) {
            SuccessResponses.printCustomMessage("Client updated successfully.");
        }
    }

    @Override
    public void delete() {
        System.out.println("Enter username of the client to remove: ");
        String userName = scanner.nextLine();

        if (clientService.removeClient(userName)) {
            SuccessResponses.printCustomMessage("Employee removed successfully.");
        }
    }

    @Override
    public void search() {
        String query = clientService.formingQuerySearchClients();
        printList(clientService.getClientsBySearch(query));
    }

    @Override
    public void viewAll() {
        printList(clientService.getAllClients());
    }

    public void viewClientsByManager() {
        printList(managerService.getManagerClients(Session.getInstance().getUser()));
    }
}
