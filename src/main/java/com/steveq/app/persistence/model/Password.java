package com.steveq.app.persistence.model;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="passwords_table")
public class Password implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "passwords_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "hash")
    private String hash;

    @OneToOne(mappedBy = "pass")
    private User user;

    public Password(){}

    public Password(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                '}';
    }
}
