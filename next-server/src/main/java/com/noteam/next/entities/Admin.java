package com.noteam.next.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.noteam.next.models.User;
import com.noteam.next.roles.AdminAuthority;

@Entity
@Table(name = "admins")
public class Admin implements User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "age", nullable = false)
  private Integer age;

  @Column(name = "image", nullable = true)
  private String image;

  @Column(name = "social_security_number", nullable = false)
  private String social_security_number;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(nullable = false, name = "created_at", insertable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
  private LocalDateTime created_at;

  @Column(name = "updated_at", insertable = false, updatable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
  private LocalDateTime updated_at;

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
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    AdminAuthority employeeAuthority = new AdminAuthority();
    return List.of(new AdminAuthority[] { employeeAuthority });
  }
}
