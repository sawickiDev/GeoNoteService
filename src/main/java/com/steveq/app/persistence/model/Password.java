package com.steveq.app.persistence.model;

import javax.persistence.*;

@Entity
@Table(name="passwords_table")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hash")
    private String hash;

    @OneToOne(mappedBy = "pass")
    private OauthClient client;

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

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                '}';
    }
}
