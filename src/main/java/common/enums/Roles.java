package common.enums;

public enum Roles {
    ROLE_SUPER_ADMIN(1001),
    ROLE_CUSTOMER(1002),
    ROLE_DELIVERY_PERSON(1003);

    private int roleId;

    Roles(int roleId) {
        this.roleId = roleId;
    }

    public static Roles fromId(int roleId) {
        for (Roles role : Roles.values()) {
            if (roleId == role.getRoleId()) {
                return role;
            }
        }
        return null;
    }

    public int getRoleId() {
        return roleId;
    }
}
