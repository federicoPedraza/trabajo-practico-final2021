package com.company.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Account linkedAccount;
    private List<Publication> allProducts = new ArrayList<>();

    public Cart(Account linkedAccount)
    {
        this.linkedAccount = linkedAccount;
    }

    public void add(Publication product)
    {
        this.allProducts.add(product);
    }

    public String remove(Publication product)
    {
        if(allProducts.contains(product))
        {
            allProducts.remove(product);
            return product.getTitle() + " has been removed from your cart.";
        }
        return "You don't have this product in your cart.";
    }

    public double getTotalCost()
    {
        int total = 0;

        for(int i = 0; i < allProducts.size(); i++)
        {
            total += allProducts.get(i).getCost();
        }

        return total;
    }

    public String getAllProducts()
    {
        String string = "";

        for(int i = 0; i < allProducts.size(); i++)
        {
            string += i + ". " + allProducts.get(i).getTitle() + ".\n";
        }

        return string;
    }
}
