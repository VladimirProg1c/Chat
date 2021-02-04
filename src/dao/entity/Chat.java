package dao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Chat implements Comparable<Chat>{

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Date created_at;

    public Chat() {}

    public Chat(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public int compareTo(Chat o) {
        return 0;
    }
}
