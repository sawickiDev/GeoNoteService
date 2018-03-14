package com.steveq.app.persistence.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="passwords_table")
public class Password implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passwords_seq")
    @SequenceGenerator(name = "passwords_seq", sequenceName = "passwords_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "hash")
    private String hash;

    @OneToOne(mappedBy = "pass")
    private OauthClient client;

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

    public OauthClient getClient() {
        return client;
    }

    public void setClient(OauthClient client) {
        this.client = client;
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
