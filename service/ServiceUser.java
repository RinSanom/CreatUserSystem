package createUserManagement.service;

import createUserManagement.controller.UserController;
import createUserManagement.model.User;
import createUserManagement.repository.UserRepository;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceUser {
    private UserRepository repository;
    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot7379508041:AAHVnaE9M1spEW6sY25jBTyG1cy1mxjrgBQ/sendMessage";
    private static final String CHAT_ID = "1880202595";
    public ServiceUser(UserRepository repository) {
        this.repository = repository;
    }
    public User createUser(String name, String email , String uuid){
        User user = new User(name , email  , uuid );
        repository.addUser(user);
        sendTelegram(user);
        return user;
    }
    public void sendTelegram(User user) {
        String message = "New User Created: " + user.getName();
        try {
            URL url = new URL(TELEGRAM_API_URL + "?chat_id=" + CHAT_ID + "&text=" + message);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println("Telegram response code: " + responseCode);
            connection.disconnect();
            System.out.println("Notification sent to Telegram bot!");
        } catch (Exception e) {
            System.out.println("Error sending message to Telegram: " + e.getMessage());
        }
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
            if (!user.isDeleted()) {
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
