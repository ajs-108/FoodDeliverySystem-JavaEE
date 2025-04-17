package com.app.model.jpa;

import jakarta.persistence.*;
@Entity(name = "role")
@Table(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "role_id", insertable = false, updatable = false)
    private int roleId;

    @Column(name = "user_role", insertable = false, updatable = false,
            nullable = false, length = 40, unique = true)
    private String userRole;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "roleId=" + roleId +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
