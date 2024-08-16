package com.backend.view.handler;

import com.backend.model.User;
import com.backend.service.impl.EmployeeService;
import com.backend.util.ErrorResponses;
import com.backend.util.SuccessResponses;

public class EmployeeMenuHandler extends BaseHandler{

    EmployeeService employeeService = new EmployeeService();

    @Override
    public void handlerMenu() {

    }

    @Override
    public void create() {

        String userName = getField("username");

        while (employeeService.getByUsername(userName) != null) {
            ErrorResponses.printRandom(ErrorResponses.RESPONSES_TO_USERNAME_ALREADY_EXIST);
            userName = getField("username");
        }

        String password = getField("password");

        System.out.println("Enter role (ADMIN/MANAGER): ");

        User.Role role = null;
        while (role == null) {
            System.out.print("Enter role (ADMIN/MANAGER): ");
            try {
                role = User.Role.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                ErrorResponses.printCustomMessage("User role must be ADMIN or MANAGER.");
            }
        }

        String name = getField("name");
        String email = getField("email");
        String phone = getField("phone");

        User employee = new User(0, userName, password, role, name, email, phone);
        if (employeeService.addEmployee(employee)) {
            SuccessResponses.printCustomMessage("Employee added successfully.");
        }
    }

    @Override
    public void update() {
        System.out.println("Enter username of the employee to update: ");
        String userName = scanner.nextLine();

        if (employeeService.getByUsername(userName)==null) {
            ErrorResponses.printCustomMessage("Employee not found.");
            return;
        }

        String password = getNewField("password");
        String name = getNewField("name");
        String email = getNewField("email");
        String phone = getNewField("phone");

        User existingEmployee = employeeService.getByUsername(userName);
        User updatedEmployee = new User(
                existingEmployee.getId(),
                userName,
                password.isEmpty() ? existingEmployee.getPassword() : password,
                existingEmployee.getRole(),
                name.isEmpty() ? existingEmployee.getName() : name,
                email.isEmpty() ? existingEmployee.getEmail() : email,
                phone.isEmpty() ? existingEmployee.getPhone() : phone
        );
        updatedEmployee.setId(existingEmployee.getId());

        if (employeeService.updateEmployee(updatedEmployee)) {
            SuccessResponses.printCustomMessage("Employee updated successfully.");
        }
    }

    @Override
    public void delete() {
        System.out.println("Enter username of the employee to remove: ");
        String userName = scanner.nextLine();

        if (employeeService.removeEmployee(userName)) {
            SuccessResponses.printCustomMessage("Employee removed successfully.");
        }
    }

    @Override
    public void search() {

    }

    @Override
    public void viewAll() {
        printList(employeeService.getAllEmployees());
    }
}
