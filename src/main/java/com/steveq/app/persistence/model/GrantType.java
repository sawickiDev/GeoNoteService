package com.steveq.app.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grant_type_table")
public class GrantType implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "grant_type_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "grantTypes")
    private Set<OauthClient> clients = new HashSet<>();

    public GrantType(){}

    public GrantType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OauthClient> getClients() {
        return clients;
    }

    public void setClients(Set<OauthClient> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "GrantType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
