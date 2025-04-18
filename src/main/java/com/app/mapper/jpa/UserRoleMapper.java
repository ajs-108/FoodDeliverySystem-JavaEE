package com.app.mapper.jpa;

import com.app.dto.jpa.UserRoleDTO;
import com.app.model.jpa.UserRole;

public class UserRoleMapper {
    public UserRoleDTO toDTO(UserRole role) {
        UserRoleDTO roleDTO = new UserRoleDTO();
        if (role == null) {
            return null;
        }
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setUserRole(role.getUserRole());
        return roleDTO;
    }

    public UserRole toRole(UserRoleDTO roleDTO) {
        UserRole role = new UserRole();
        if (roleDTO == null) {
            return null;
        }
        role.setRoleId(roleDTO.getRoleId());
        role.setUserRole(roleDTO.getUserRole());
        return role;
    }
}
