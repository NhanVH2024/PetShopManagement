package com.petshop.model;

public class Pet extends SaleItem {
    private String species;
    private String breed;
    private int ageInMonths;
    private boolean isAvailable;
    private String photoPath;

    public Pet(int id, String name, long price, String species, String breed, int ageInMonths, boolean isAvailable, String photoPath) {
        super(id, name, price);
        this.species = species;
        this.breed = breed;
        this.ageInMonths = ageInMonths;
        this.isAvailable = isAvailable;
        this.photoPath = photoPath;
    }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAgeInMonths() { return ageInMonths; }
    public void setAgeInMonths(int ageInMonths) { this.ageInMonths = ageInMonths; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    @Override
    public boolean canSell(int quantity) {
        return this.isAvailable && quantity == 1;
    }

    @Override
    public String cannotSellMessage() {
        return "Thú cưng này đã bán hoặc không sẵn sàng để bán!";
    }

    @Override
    public void afterSale(int quantity) {
        this.isAvailable = false;
    }
}
