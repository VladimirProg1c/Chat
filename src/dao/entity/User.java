package dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String userPass;
    //private Date created_at;
    //private Set<Chat> chats = new HashSet<>();
    //private Set<Message> messages = new HashSet<>();

    public User() { }

    public User(String userName) {
        this.userName = userName;
    }
    public User(String userName, Long id) {
        this.userName = userName;
        this.id = id;
    }
    public User(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setUserPass(String userpass){
        this.userPass = userpass;
    }
    public String getUserPass() {
        return userPass;
    }
  /*  public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
*/
   /* public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }*/

}
