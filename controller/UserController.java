package createUserManagement.controller;

import createUserManagement.model.User;
import createUserManagement.service.ServiceUser;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import javax.management.ConstructorParameters;
import java.util.Scanner;
import java.util.UUID;
public final class UserController {
    private ServiceUser service;

    public UserController(ServiceUser service) {
        this.service = service;
    }
    public void start() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nUser Management System:");
            System.out.println("1. Create User");
            System.out.println("2. Search User by UUID");
            System.out.println("3. Update User by UUID");
            System.out.println("4. Delete User by UUID");
            System.out.println("5. Display All Users");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> createUser();
                case 2 -> searchUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> displayUsers();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public void createUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        String uuid = UUID.randomUUID().toString();
        User user = service.createUser(name , email , uuid);
        System.out.println("User created: " + user);
    }
    public void searchUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter UUID for search : ");
        String uuid = scanner.nextLine();
        User user = service.searchUser(uuid);
        if(user == null ){
            System.out.println("User With UUID [ "+uuid+"] Not Found");
            return;
        }
        String[] headers = {"ID", "UUID", "Name", "Email", "Active"};
        String[] userData = {
                String.valueOf(user.getId()),
                user.getUuid(),
                user.getName(),
                user.getEmail(),
                user.isDeleted() ? "No" : "Yes"
        };
        Table table = new Table(headers.length, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for (String header : headers) {
            table.addCell(header, new CellStyle(CellStyle.HorizontalAlign.center));
        }

        // Add user data with center alignment
        for (String data : userData) {
            table.addCell(data, new CellStyle(CellStyle.HorizontalAlign.center));
        }


        System.out.println(table.render());
    }
    public void updateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter UUID: ");
        String uuid = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        System.out.print("Is user deleted (true/false): ");
        boolean isDeleted = scanner.nextBoolean();
        scanner.nextLine();
        if (service.updateUser(uuid, name, email, isDeleted)) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter UUID: ");
        String uuid = scanner.nextLine();
        if (service.deletedUser(uuid)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void displayUsers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter page number: ");
        scanner.nextLine();
        service.displayUser();
    }
}
