/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.model.entite.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaac
 */
public class ProduitDAO {

    // DAO pour inserer un produit
    public void insertProduit(Produit produit){
        String sql = "INSERT INTO Produit (nom, idCategorie, prixDeVente, stockActuel, seuilAlerte) VALUES(?, ?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)){

            pst.setString(1, produit.getNom());
            pst.setString(2, produit.getCategorie().getIdCat());
            pst.setDouble(3, produit.getPrixVente());
            pst.setInt(4, produit.getStockActuel());
            pst.setInt(5, produit.getSeuilAlerte());

            pst.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
    //DAO pour supprimer un produit
    public void deleteProduit(Produit produit) {
        
        String sql = "DELETE FROM Produit WHERE idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, produit.getIdProd());
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    //DAO update nom d'un produit 
    public void updateProduit(Produit produit) {
        
        String sql = "UPDATE Produit SET nom = ?, prixDeVente = ?, seuilAlerte = ?, stockActuel = ? WHERE idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, produit.getNom());
            pst.setDouble(2, produit.getPrixVente());
            pst.setInt(3, produit.getSeuilAlerte());
            pst.setInt(4, produit.getStockActuel());
            pst.setString(5, produit.getIdProd());
            
            int eleme_updated = pst.executeUpdate();
            
            if (eleme_updated > 0) {
                System.out.println("Mise à jour réussi !");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    //DAO pour read un produit
    public List<Produit> findAllProduits(){
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM Produit";
        
        try(Connection conn = DatabaseConnection.getConnection(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);){
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProd(rs.getString("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setPrixVente(rs.getDouble("prixDeVente"));
                produit.setSeuilAlerte(rs.getInt("seuilAlerte"));
                produit.setStockActuel(rs.getInt("stockActuel"));
                
                produits.add(produit);
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return produits;
    }
}
