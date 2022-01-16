package com.epam.esm.gift.repository.bootstrap;

public class ColumnProperty {

    private final String propName;
    private final String realName;

    public ColumnProperty(String propName, String realName) {
        this.propName = propName;
        this.realName = realName;
    }

    public String propName() {
        return propName;
    }

    public String realName() {
        return realName;
    }
}
