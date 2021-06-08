package com.company.models;

enum AmountOfPublicationPerPageOptions {
    VERYSMALL(4),
    SMALL(8),
    MEDIUM(12),
    LARGE(24),
    VERYLARGE(64);

    private int capacity;
    AmountOfPublicationPerPageOptions(int capacity)
    {
        this.capacity = capacity;
    }

    public int getCapacity()
    {
        return capacity;
    }
}

public class Preferences {
    public int amountOfPublicationsPerPage = AmountOfPublicationPerPageOptions.SMALL.getCapacity();
    public boolean compactBrowsing = true;
    public Preferences() { }
}
