package com.hfing.shoesstore.Activity.domain;

public class Role {
    private int id;
    private String roleName;

    // Constructors
    public Role() {}

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
