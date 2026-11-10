package com.petshop.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InventoryUtils {

    public static <T> ArrayList<T> filterItems(ArrayList<T> inputList, Predicate<T> condition) {
        ArrayList<T> filteredList = new ArrayList<>();
        
        // Loop through the arraylist and apply the generic condition
        for (T item : inputList) {
            if (condition.test(item)) {
                filteredList.add(item); // Match found, add to the result list
            }
        }
        
        return filteredList;
    }
}
