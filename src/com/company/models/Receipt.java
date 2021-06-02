package com.company.models;

import com.company.tools.Formatting;

import java.text.Format;

public class Receipt {
    public Account buyer;

    @Override
    public String toString()
    {
        return
                Formatting.horizontalLine() +
                        "Shopify!" +
                        Formatting.horizontalLine() +
                        "There are all your selected products: \n" +
                        buyer.getCart().getAllProducts() +
                        Formatting.horizontalLine() +
                        "The total cost of these products is: $" +
                        buyer.getCart().getTotalCost();
    }
}
