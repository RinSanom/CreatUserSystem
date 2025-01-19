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
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    return user;
                }
            }
            return null;
        }
    public List<User> getAllUsers() {
        return users;
    }
}
