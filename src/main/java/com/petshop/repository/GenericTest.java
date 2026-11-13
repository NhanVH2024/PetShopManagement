package com.petshop.repository;
import com.petshop.controller.InventoryUtils;


import com.petshop.model.Pet;
import com.petshop.model.Product;
import java.util.ArrayList;

public class GenericTest {
    public static void main(String[] args) {
        System.out.println("=== TESTING GENERIC & ARRAYLIST FILTER METHOD ===");

        // SCENARIO 1: Filtering an ArrayList of Pets
        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(new Pet(1, "Milo", 3500000L, "Dog", "Poodle", 3, true, "poodle.png"));
        petList.add(new Pet(2, "Luna", 5000000L, "Cat", "British Shorthair", 4, true, "bsh.png"));
        petList.add(new Pet(3, "Max", 4200000L, "Dog", "Corgi", 2, true, "corgi.png"));

        // Call the generic method using a Lambda expression to filter "Poodle" breed
        ArrayList<Pet> poodles = InventoryUtils.filterItems(petList, pet -> "Poodle".equalsIgnoreCase(pet.getBreed()));

        System.out.println("\n--- Filtered Pets (Breed: Poodle) ---");
        for (Pet p : poodles) {
            System.out.println("- " + p.getName() + " | Breed: " + p.getBreed());
        }
        // SCENARIO 2: Filtering an ArrayList of Product
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Royal Canin Food", 320000L, "Food", 15));
        productList.add(new Product(2, "Leather Dog Collar", 85000L, "Accessory", 4));  // Low stock!
        productList.add(new Product(3, "Cat Scratching Post", 210000L, "Accessory", 3)); // Low stock!

        // Call the exact same generic method to filter products with stock <= 5
        ArrayList<Product> lowStockProducts = InventoryUtils.filterItems(productList, prod -> prod.getStock() <= 5);

        System.out.println("\n--- Filtered Products (Low Stock Alert <= 5) ---");
        for (Product pr : lowStockProducts) {
            System.out.println("- " + pr.getName() + " | Current Stock: " + pr.getStock());
        }
    }
}
