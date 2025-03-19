package common.enums;

public enum Roles {
    ROLE_SUPER_ADMIN(1001),
    ROLE_CUSTOMER(1002),
    ROLE_DELIVERY_PERSON(1003);

    private int id;

    Roles(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
