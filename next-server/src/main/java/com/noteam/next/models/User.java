package com.noteam.next.models;

import com.noteam.next.roles.EmployeeAuthority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface User extends UserDetails {
  // WARNING: Remove password field
  // public User(long id, String email, String password) {
  // this.id = id;
  // this.email = email;
  // this.password = password;
  // this.isGenerated = false;
  // }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities();

  public Integer getId();

  @Override
  public String getPassword();

  @Override
  public String getUsername();

  public String getEmail();

}
