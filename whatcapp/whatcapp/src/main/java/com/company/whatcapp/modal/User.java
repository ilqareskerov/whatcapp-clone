package com.company.whatcapp.modal;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String full_name;
    private String email;
    private String profile_picture;
    private String password;

    public User() {
    }

    public User(long id, String full_name, String email, String profile_picture, String password) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.profile_picture = profile_picture;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Objects.equals(getFull_name(), user.getFull_name()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getProfile_picture(), user.getProfile_picture()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFull_name(), getEmail(), getProfile_picture(), getPassword());
    }
}
