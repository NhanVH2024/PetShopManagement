package com.petshop.repository;

import com.petshop.model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetRepository {

    // 1.Get all pet that's avalible (is_available = true)
    public List<Pet> getAvailablePets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets WHERE is_available = true ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                pets.add(new Pet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("species"),
                    rs.getString("breed"),
                    rs.getInt("age_in_months"),
                    rs.getBoolean("is_available"),
                    rs.getString("photo_path")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAvailablePets: " + e.getMessage());
        }
        return pets;
    }

    // 2. Tìm thú cưng theo ID
    public Pet getPetById(int id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Pet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getLong("price"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age_in_months"),
                        rs.getBoolean("is_available"),
                        rs.getString("photo_path")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getPetById: " + e.getMessage());
        }
        return null;
    }

    // 3. Thêm mới một thú cưng vào database
    public boolean addPet(Pet pet) {
        String sql = "INSERT INTO pets (name, price, species, breed, age_in_months, is_available, photo_path) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pet.getName());
            pstmt.setLong(2, pet.getPrice());
            pstmt.setString(3, pet.getSpecies());
            pstmt.setString(4, pet.getBreed());
            pstmt.setInt(5, pet.getAgeInMonths());
            pstmt.setBoolean(6, pet.isAvailable());
            pstmt.setString(7, pet.getPhotoPath());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi addPet: " + e.getMessage());
            return false;
        }
    }

    // 4. Cập nhật trạng thái thú cưng sang Đã bán
    public boolean markAsSold(int petId) {
        String sql = "UPDATE pets SET is_available = false WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, petId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi markAsSold: " + e.getMessage());
            return false;
        }
    }
        // 5. Retrieve all pets (both available and sold) for Manager audit
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets ORDER BY id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                pets.add(new Pet(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("species"),
                    rs.getString("breed"),
                    rs.getInt("age_in_months"),
                    rs.getBoolean("is_available"),
                    rs.getString("photo_path")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllPets: " + e.getMessage());
        }
        return pets;
    }

    // 6. Update pet information (Edit inventory)
    public boolean updatePet(Pet pet) {
        String sql = "UPDATE pets SET name = ?, price = ?, species = ?, breed = ?, "
                   + "age_in_months = ?, is_available = ?, photo_path = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pet.getName());
            pstmt.setLong(2, pet.getPrice());
            pstmt.setString(3, pet.getSpecies());
            pstmt.setString(4, pet.getBreed());
            pstmt.setInt(5, pet.getAgeInMonths());
            pstmt.setBoolean(6, pet.isAvailable());
            pstmt.setString(7, pet.getPhotoPath());
            pstmt.setInt(8, pet.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in updatePet: " + e.getMessage());
            return false;
        }
    }

    // 7. Delete pet permanently (Manager role permission only)
    public boolean deletePet(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in deletePet: " + e.getMessage());
            return false;
        }
    }

}
