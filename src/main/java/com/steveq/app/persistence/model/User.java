package com.steveq.app.persistence.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users_table")
public class User implements UserDetails, Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "password")
    private Password pass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "user_authorities_table",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authorities_id")}
    )
    private Set<Authority> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<GeoNote> geoNotes;

    public User(){}

    public User(String name, Password pass) {
        this.name = name;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Password getPass() {
        return pass;
    }

    public void setPass(Password pass) {
        this.pass = pass;
    }

    public Set<Authority> getRoles() {
        return roles;
    }

    public void setRoles(Set<Authority> roles) {
        this.roles = roles;
    }

    public Set<GeoNote> getGeoNotes() {
        return geoNotes;
    }

    public void setGeoNotes(Set<GeoNote> geoNotes) {
        this.geoNotes = geoNotes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass=" + pass +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        for(Authority authority : this.roles)
            auths.add(new SimpleGrantedAuthority(authority.getName()));

        return auths;
    }

    @Override
    public String getPassword() {
        return this.pass.getHash();
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
