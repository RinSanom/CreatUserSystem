package createUserManagement.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class User {
    private int id;
    @Getter
    private  String uuid;
    private String name;
    private String email;
    private boolean isDeleted;
    public User(int id, String uuid, String name, String email, boolean isDeleted) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public User(String name, String email , String uuid) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

}
