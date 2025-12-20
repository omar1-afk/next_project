package com.noteam.next.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.noteam.next.models.User;
import com.noteam.next.roles.EmployeeAuthority;

@Entity
@Table(name = "drivers")
public class Driver implements User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer driver_id;

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

  @Column(name = "is_busy", nullable = false)
  private Boolean isbusy;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime created_at;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updated_at;

  public Integer getDriver_id() {
    return driver_id;
  }

  // public void setDriver_id(Integer driver_id) { (we don't need to set the
  // driver id)
  // this.driver_id = driver_id;
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

  public Boolean getIsbusy() {
    return isbusy;
  }

  public void setIsbusy(Boolean isbusy) {
    this.isbusy = isbusy;
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
    return driver_id;
  }

  public void setId(int id) {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    EmployeeAuthority employeeAuthority = new EmployeeAuthority();
    return List.of(new EmployeeAuthority[] { employeeAuthority });
  }
}
