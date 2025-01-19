package createUserManagement.repository;

import createUserManagement.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();
        public void addUser(User user ){
            users.add(user);
        }
        public User findUserByUUID(String uuid){
             users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst().orElse(null);
            return null;
        }
    public List<User> getAllUsers() {
        return users;
    }
}
