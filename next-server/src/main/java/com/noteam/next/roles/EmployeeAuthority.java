package com.noteam.next.roles;

import org.springframework.security.core.GrantedAuthority;

public class EmployeeAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "employee";
    }
}
