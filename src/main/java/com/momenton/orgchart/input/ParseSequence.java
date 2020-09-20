package com.momenton.orgchart.input;

public enum ParseSequence {
    NAME(0), ID(1), MANAGER_ID(2);
    private final int order;

    ParseSequence(int order) {
        this.order = order;
    }

    public int getValue() {
        return order;
    }
}
