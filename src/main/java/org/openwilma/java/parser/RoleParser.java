package org.openwilma.java.parser;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openwilma.java.classes.RawSlug;
import org.openwilma.java.classes.Role;
import org.openwilma.java.classes.WilmaUserTypes;
import org.openwilma.java.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleParser {

    private static Element getFirstOrNull(Elements elements) {
        if (elements.isEmpty())
            return null;
        else
            return elements.get(0);
    }

    public static List<Role> parseRoles(String htmlContent) {
        List<Role> roles = new ArrayList<>();
        Document wilma = Jsoup.parse(htmlContent);
        // Searching for role selection element
        Element selectionTag = wilma.getElementById("role-selection");
        if (selectionTag == null) {
            // There isn't any roles to select. returning empty list
            return roles;
        }


        Element selectionParent = selectionTag.parent();
        if (selectionParent != null) {
            Elements rolesPanel = selectionParent.getElementsByClass("panel");
            if (rolesPanel != null) {
                for (Element rolePanel : rolesPanel) {
                    Element panelBody = getFirstOrNull(rolePanel.getElementsByClass("panel-body"));
                    if (panelBody != null) {
                        Elements roleLinks = rolePanel.getElementsByClass("text-style-link");
                        for (Element roleLink : roleLinks) {
                            String roleName = roleLink.text();

                            RawSlug slug = RoleSlugParser.parseSlug(roleLink.attr("href"));

                            // Removing school name from roleName
                            Element schoolElement = getFirstOrNull(roleLink.getElementsByTag("small"));
                            String schoolName = null;
                            if (schoolElement != null) {
                                schoolName = schoolElement.text();
                            }
                            if (schoolName != null)
                                roleName = roleName.replace(schoolName, "").trim();
                            else
                                roleName = roleName.trim();

                            Element schoolClassElement = getFirstOrNull(roleLink.getElementsByClass("lem"));
                            String schoolClassName = null;
                            if (schoolClassElement != null) {
                                if (schoolClassElement.text().split(", ").length == 2)
                                    schoolClassName = schoolClassElement.text().split(", ")[1];
                            }
                            if (schoolClassName != null)
                                roleName = roleName.replace(", "+schoolClassName, "").trim();
                            else
                                roleName = roleName.trim();

                            Element classTeacherElement = getFirstOrNull(panelBody.getElementsByClass("links small first bothmargins"));
                            String classTeacherName = null;
                            if (classTeacherElement != null) {
                                if (classTeacherElement.text().split(": ").length == 2)
                                    classTeacherName = classTeacherElement.text().split(": ")[1];
                            }


                            // If slug is null, don't add that role.
                            if (slug != null) {
                                Role role = new Role(roleName, slug.getType(), slug.getId(), slug.getSlug(), classTeacherName, schoolClassName, schoolName);
                                roles.add(role);
                            }
                        }
                    }
                }
            }

        }
        return roles;
    }

    static class RoleSlugParser {
        public static RawSlug parseSlug(String slugUrl) {
            Pattern sessionPattern = Pattern.compile(Config.slugRegex);
            Matcher valueMatcher = sessionPattern.matcher(slugUrl);
            if (valueMatcher.find()) {
                if (valueMatcher.groupCount() == 2) {
                    int type = Integer.parseInt(valueMatcher.group(1));
                    int primusId = Integer.parseInt(valueMatcher.group(2));
                    return new RawSlug(primusId, type, slugUrl);
                }
            }
            return null;
        }
    }
}
