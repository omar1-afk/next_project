package com.noteam.next.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Authorities {
    public static GrantedAuthority adminAuthority = new SimpleGrantedAuthority("admin");
    public static GrantedAuthority employeeAuthority = new SimpleGrantedAuthority("employee");
}
