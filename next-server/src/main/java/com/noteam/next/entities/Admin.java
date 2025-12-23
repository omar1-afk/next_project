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
    private Integer id = Integer.valueOf(0);

    @Column(name = "name", nullable = false)
    private String name = "mm";

    @Column(name = "age", nullable = false)
    private Integer age = Integer.valueOf(5);

    @Column(name = "image", nullable = true)
    private String image = "I";

    @Column(name = "social_security_number", nullable = false)
    private String socialSecurityNumber = "S";

    @Column(name = "email", nullable = false)
    private String email = ".COM";

    @Column(name = "password", nullable = false)
    private String password = "Password";

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

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

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Authorities.adminAuthority);
    }
}
