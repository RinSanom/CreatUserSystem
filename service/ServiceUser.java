package createUserManagement.service;

import createUserManagement.model.User;
import createUserManagement.repository.UserRepository;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class ServiceUser {
    private UserRepository repository;
    public ServiceUser(UserRepository repository) {
        this.repository = repository;
    }
    public User createUser(String name, String email , String uuid){
        User user = new User(name , email  , uuid );
        repository.addUser(user);
        sendTelegram(user);
        return user;
    }
    public void sendTelegram(User user){
        System.out.println("New User Created[ "+user.getName());
    }
    public User searchUser(String uuid){
        return repository.findUserByUUID(uuid);
    }
    public boolean updateUser(String uuid, String name, String email, boolean isDeleted ){
        User user = repository.findUserByUUID(uuid);
        if(user != null){
            user.setName(name);
            user.setEmail(email);
            user.setDeleted(isDeleted);

            return true;
        }
        return false;
    }
    public boolean deletedUser(String uuid){
        User user = repository.findUserByUUID(uuid);
        if(user != null){
            user.setDeleted(true);
            return true;
        }
        return false;
    }
    public void displayUser(){
        System.out.println("=================All User ================");
        Table table = new Table(5 , BorderStyle.UNICODE_BOX_DOUBLE_BORDER , ShownBorders.ALL);
        String coll [] = { "ID" , "UUID" , "NAME" , "EMAIL" , "Active"};
        for(int i =0; i<coll.length; i++){
            table.addCell(coll[i] , new CellStyle(CellStyle.HorizontalAlign.center));
            table.setColumnWidth( i , 20 , 50 );
        }
        for (User user : repository.getAllUsers()) {
            if (!user.isDeleted()) { // Display only active (non-deleted) users
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getUuid());
                table.addCell(user.getName());
                table.addCell(user.getEmail());
                table.addCell(user.isDeleted() ? "No" : "Yes");
            }
        }
        System.out.println(table.render());
    }
}
