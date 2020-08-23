package org.openwilma.java.classes;

public enum WilmaUserTypes {
    TEACHER(1),
    STUDENT(2),
    STAFF(3),
    PARENT(4),
    WORKPLACE_INSTRUCTOR(5),
    MANAGEMENT(6),
    WILMA_ACCOUNT(7);

    private int id;

     WilmaUserTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
