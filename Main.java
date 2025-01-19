package createUserManagement;

import createUserManagement.controller.UserController;
import createUserManagement.repository.UserRepository;
import createUserManagement.service.ServiceUser;

public class Main {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository();
        ServiceUser serviceUser = new ServiceUser(repository);
        UserController controller = new UserController(serviceUser);
        controller.start();
    }
}
