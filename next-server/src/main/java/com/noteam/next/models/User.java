package com.noteam.next.models;

import com.noteam.next.roles.EmployeeAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
  private long id;
  private String email;
  private String password;
  private boolean isGenerated;

  // WARNING: Remove password field
  // public User(long id, String email, String password) {
  // this.id = id;
  // this.email = email;
  // this.password = password;
  // this.isGenerated = false;
  // }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    EmployeeAuthority employeeAuthority = new EmployeeAuthority();
    return List.of(new EmployeeAuthority[] { employeeAuthority });
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
    isGenerated = true;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getEmail() {
    return email;
  }

}
