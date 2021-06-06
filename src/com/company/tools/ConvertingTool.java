package com.company.tools;

import com.company.models.categories.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertingTool {
    public static String[] categoriesToString(int page, int amount)
    {
        List<String> categories = new ArrayList<>();
        for(int i = page; i < page + amount; i++)
        {
            categories.add(Category.values()[i].toString());
        }

        return Arrays.copyOf(categories.toArray(), categories.size(), String[].class);
    }
}
