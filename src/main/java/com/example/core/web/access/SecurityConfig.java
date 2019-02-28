package com.example.core.web.access;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class SecurityConfig implements ConfigAttribute {

    private final String attrib;

    public SecurityConfig(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attrib = config;
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>(
                attributeNames.length);

        for (String attribute : attributeNames) {
            attributes.add(new SecurityConfig(attribute.trim()));
        }

        return attributes;
    }
}
