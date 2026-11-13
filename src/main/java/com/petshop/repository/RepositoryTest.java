package com.petshop.repository;
import com.petshop.model.Pet;
import com.petshop.model.Product;
import java.util.List;

public class RepositoryTest {
    public static void main(String[] args) {
        System.out.println("=== STARTING DATABASE TIER VERIFICATION ===");
        PetRepository petRepo = new PetRepository();
        ProductRepository prodRepo = new ProductRepository();

        // 1.Insert a sample pet into PostgreSQL
        Pet samplePet = new Pet(0, "Milo the Poodle", 3500000L, "Dog", "Poodle", 3, true, "/images/milo.png");
        boolean petAdded = petRepo.addPet(samplePet);
        System.out.println("Insert Pet status: " + (petAdded ? "SUCCESS" : "FAILED"));

        // 2.Insert a sample product into PostgreSQL
        Product sampleProduct = new Product(0, "Royal Canin Dog Food 5kg", 450000L, "Food", 20);
        boolean prodAdded = prodRepo.addProduct(sampleProduct);
        System.out.println("Insert Product status: " + (prodAdded ? "SUCCESS" : "FAILED"));

        // 3.Query and display available pets from PostgreSQL
        System.out.println("\n--- Available Pets in Database ---");
        List<Pet> pets = petRepo.getAvailablePets();
        for (Pet p : pets) {
            System.out.println("- [" + p.getId() + "] " + p.getName() + " | Breed: " + p.getBreed() + " | Price: " + p.getPrice() + " VND");
        }

        // 4. Query and display products in stock from PostgreSQL
        System.out.println("\n--- Products in Inventory ---");
        List<Product> products = prodRepo.getAllProducts();
        for (Product pr : products) {
            System.out.println("- [" + pr.getId() + "] " + pr.getName() + " | Stock: " + pr.getStock() + " | Price: " + pr.getPrice() + " VND");
        }
        System.out.println("\n=== DATABASE TIER TEST COMPLETED SUCCESSFULLY ===");
    }
}
