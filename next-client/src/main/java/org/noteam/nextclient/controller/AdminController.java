package org.noteam.nextclient.controller;

import org.noteam.nextclient.models.Admin;

public class AdminController {
   Admin admin;
    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
    public void setAdmin(Admin admin) {}
}
