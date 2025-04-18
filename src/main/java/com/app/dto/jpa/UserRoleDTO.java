package com.app.dto.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int roleId;
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
        return "UserRoleDTO{" +
                "roleId=" + roleId +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
