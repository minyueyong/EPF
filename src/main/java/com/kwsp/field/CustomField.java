
package com.kwsp.field;

import org.springframework.data.mongodb.core.aggregation.Field;

public class CustomField implements Field {

    private String name;

    public CustomField(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTarget() {
        return name; // You can customize this if needed
    }

    @Override
    public boolean isAliased() {
        return false; // You can customize this if needed
    }
}
