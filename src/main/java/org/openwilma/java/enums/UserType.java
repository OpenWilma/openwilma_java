package org.openwilma.java.enums;

public enum UserType {
    TEACHER(1),
    STUDENT(2),
    STAFF(3),
    GUARDIAN(4),
    WORKPLACE_INSTRUCTOR(5),
    MANAGEMENT(6),
    // Wilma account is the account which has roles, when you enter to the role selector in browser, that account that you're currently logged in is called Wilma Account, and when you click the user, it's a Role
    WILMA_ACCOUNT(7);

    private int userType;

    UserType(final int userType) {
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
