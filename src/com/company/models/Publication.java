package com.company.models;

import com.company.models.categories.Category;

import java.time.LocalDate;
import java.util.HashMap;

public class Publication {
    private int id = 0;
    private String title;
    private String description;
    private Account seller;
    private double cost;
    private int availableAmount = 0;
    private int seenTimes = 0;
    private Category[] categories;
    private LocalDate creationDate;

    private static int publicationCount = 0;
    private HashMap<Integer, Publication> allPublications = new HashMap<Integer, Publication>();

    public Publication(String title, String description, int availableAmount, Account seller, double cost, Category[] categories) {
        id = publicationCount;
        publicationCount++;
        this.title = title;
        this.description = description;
        this.seller = seller;
        this.categories = categories;
        this.availableAmount = availableAmount;
        this.cost = cost;
        seenTimes = 0;
        creationDate = LocalDate.now();
        allPublications.put(id, this);
    }

    //TODO: Test this code, extreme low possibilites of working correctly.
    public Publication[] searchForPublication(String searchingValue)
    {
        return allPublications.values().stream()
                .filter(publication ->
                        publication.getTitle().contains(searchingValue) ||
                        publication.getCategoriesStringified().contains(searchingValue)).toArray(Publication[]::new);
    }

    public int getAvailableAmount()
    {
        return availableAmount;
    }

    public void setAvailableAmount(int value)
    {
        availableAmount = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if(cost <= 0) cost *= -1;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public int getSeenTimes() {
        return seenTimes;
    }

    public void setSeenTimes(int seenTimes) {
        this.seenTimes = seenTimes;
    }

    public Category[] getCategories() {
        return categories;
    }

    public String getCategoriesStringified()
    {
        String string = "";
        for (Category category : categories)
        {
            string += "-" + category.toString() + "-";
        }

        return string;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
