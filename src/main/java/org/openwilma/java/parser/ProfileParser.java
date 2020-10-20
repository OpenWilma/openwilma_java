package org.openwilma.java.parser;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openwilma.java.classes.Role;
import org.openwilma.java.classes.User;
import org.openwilma.java.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class ProfileParser {

    public static User parseProfile(String htmlContent) {
        User user = new User();
        Document wilma = Jsoup.parse(htmlContent);
        Element schoolSpan = ParseUtils.getFirstOrNull(wilma.getElementsByClass("school"));
        Element nameSpan = ParseUtils.getFirstOrNull(wilma.getElementsByClass("teacher"));
        Element formKeyInput = ParseUtils.getFirstOrNull(wilma.getElementsByAttributeValue("name", "formkey"));
        if (schoolSpan != null && !schoolSpan.text().trim().isEmpty())
            user.setSchool(schoolSpan.text().trim());
        if (nameSpan != null && !nameSpan.text().trim().isEmpty())
            user.setName(nameSpan.text().trim());
        if (formKeyInput != null && !formKeyInput.attr("value").trim().isEmpty()) {
            String formKey = formKeyInput.attr("value").trim();
            String[] keyParts = formKey.split(":");
            if (keyParts.length > 1) {
                String type = keyParts[0];
                String id = keyParts[1];
                if (id.matches("[0-9]+")) {
                    user.setId(Integer.parseInt(id));
                }
                user.setType(ParseUtils.getTypeAsEnum(type));
            }
        }
        return user;
    }

    public static boolean roleSelectorExists(String htmlContent) {
        Document wilma = Jsoup.parse(htmlContent);
        // Searching for role selection element
        Element selectionTag = wilma.getElementById("role-selection");
        // There isn't any roles to select.
        return selectionTag != null;
    }
}
