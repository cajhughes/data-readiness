package com.cajhughes.ready.model;

public class AttributeTableRow {
    private String name;
    private int count;

    public AttributeTableRow(final String name, final Integer count) {
        this.name = name;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
