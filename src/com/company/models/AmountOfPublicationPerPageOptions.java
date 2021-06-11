package com.company.models;

public enum AmountOfPublicationPerPageOptions {
    VERYSMALL(4),
    SMALL(8),
    MEDIUM(12),
    LARGE(24),
    VERYLARGE(64);

    private int capacity;

    AmountOfPublicationPerPageOptions(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
