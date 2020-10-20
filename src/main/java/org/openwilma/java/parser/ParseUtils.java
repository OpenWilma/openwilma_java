package org.openwilma.java.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openwilma.java.enums.UserType;

public class ParseUtils {

    public static Element getFirstOrNull(Elements elements) {
        if (elements.isEmpty())
            return null;
        else
            return elements.get(0);
    }

    public static UserType getTypeAsEnum(int type) {
        switch (type) {
            case 1:
                return UserType.TEACHER;
            case 2:
                return UserType.STUDENT;
            case 3:
                return UserType.STAFF;
            case 4:
                return UserType.GUARDIAN;
            case 5:
                return UserType.WORKPLACE_INSTRUCTOR;
            case 6:
                return UserType.MANAGEMENT;
            default:
                return UserType.WILMA_ACCOUNT;
        }
    }

    public static UserType getTypeAsEnum(String type) {
        switch (type) {
            case "teacher":
                return UserType.TEACHER;
            case "student":
                return UserType.STUDENT;
            case "personnel":
                return UserType.STAFF;
            case "guardian":
                return UserType.GUARDIAN;
            case "instructor":
                return UserType.WORKPLACE_INSTRUCTOR;
            case "management":
                return UserType.MANAGEMENT;
            default:
                return UserType.WILMA_ACCOUNT;
        }
    }
}
