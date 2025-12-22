package com.noteam.next.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.noteam.next.models.User;
import com.noteam.next.roles.Authorities;

@Entity
@Table(name = "admins")
public class Admin implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer admin_id = Integer.valueOf(0);

    @Column(name = "name", nullable = false)
    private String name = "mm";

    @Column(name = "age", nullable = false)
    private Integer age = Integer.valueOf(5);

    @Column(name = "image", nullable = true)
    private String image = "I";

    @Column(name = "social_security_number", nullable = false)
    private String social_security_number = "S";

    @Column(name = "email", nullable = false)
    private String email = ".COM";

    @Column(name = "password", nullable = false)
    private String password = "Password";

    @Column(nullable = false, name = "created_at")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updated_at = LocalDateTime.now();

    // public Integer getAdmin_id() {
    // return id;
    // }

    //
    // public void setAdmin_id(Integer admin_id) { (we don't need to set the admin
    // id)
    // this.admin_id = admin_id;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSocial_security_number() {
        return social_security_number;
    }

    public void setSocial_security_number(String social_security_number) {
        this.social_security_number = social_security_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return email;
    }

    public Integer getId() {
        return admin_id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Authorities.adminAuthority);
    }
}
