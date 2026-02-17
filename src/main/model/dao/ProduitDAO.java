/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import main.util.DatabaseConnection;
import main.model.entite.Produit;
import main.model.entite.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des produits
 * @author isaac
 */
public class ProduitDAO {

    /**
     * Insérer un nouveau produit
     * @param produit Le produit à insérer
     * @return true si l'insertion a réussi
     */
    public boolean insertProduit(Produit produit) {
        // ✅ CORRIGÉ: ajout de idProd et nom correct de colonne (idCat)
        String sql = "INSERT INTO Produit (idProd, nom, idCat, prixDeVente, stockActuel, seuilAlerte) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, produit.getIdProd());
            pst.setString(2, produit.getNom());
            pst.setString(3, produit.getCategorie().getIdCat());  // ✅ CORRIGÉ: idCat
            pst.setDouble(4, produit.getPrixVente());
            pst.setInt(5, produit.getStockActuel());
            pst.setInt(6, produit.getSeuilAlerte());

            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du produit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Supprimer un produit
     * @param produit Le produit à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteProduit(Produit produit) {
        String sql = "DELETE FROM Produit WHERE idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, produit.getIdProd());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du produit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mettre à jour un produit
     * @param produit Le produit à mettre à jour
     * @return true si la mise à jour a réussi
     */
    public boolean updateProduit(Produit produit) {
        String sql = "UPDATE Produit SET nom = ?, prixDeVente = ?, seuilAlerte = ?, stockActuel = ?, idCat = ? WHERE idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, produit.getNom());
            pst.setDouble(2, produit.getPrixVente());
            pst.setInt(3, produit.getSeuilAlerte());
            pst.setInt(4, produit.getStockActuel());
            pst.setString(5, produit.getCategorie().getIdCat());
            pst.setString(6, produit.getIdProd());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Mise à jour du produit réussie !");
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du produit: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Récupérer tous les produits
     * @return Liste de tous les produits
     */
    public List<Produit> findAllProduits() {
        List<Produit> produits = new ArrayList<>();
        // ✅ CORRIGÉ: jointure avec Categorie pour récupérer le libellé
        String sql = "SELECT p.*, c.libelle FROM Produit p LEFT JOIN Categorie c ON p.idCat = c.idCat";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProd(rs.getString("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setPrixVente(rs.getDouble("prixDeVente"));
                produit.setSeuilAlerte(rs.getInt("seuilAlerte"));
                produit.setStockActuel(rs.getInt("stockActuel"));
                
                // Créer la catégorie associée
                Categorie categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));
                produit.setCategorie(categorie);
                
                produits.add(produit);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits: " + e.getMessage());
            e.printStackTrace();
        }
        
        return produits;
    }
    
    /**
     * Trouver un produit par son ID
     * @param idProd L'ID du produit
     * @return Le produit trouvé ou null
     */
    public Produit findById(String idProd) {
        String sql = "SELECT p.*, c.libelle FROM Produit p LEFT JOIN Categorie c ON p.idCat = c.idCat WHERE p.idProd = ?";
        Produit produit = null;
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idProd);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                produit = new Produit();
                produit.setIdProd(rs.getString("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setPrixVente(rs.getDouble("prixDeVente"));
                produit.setSeuilAlerte(rs.getInt("seuilAlerte"));
                produit.setStockActuel(rs.getInt("stockActuel"));
                
                Categorie categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));
                produit.setCategorie(categorie);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du produit: " + e.getMessage());
            e.printStackTrace();
        }
        
        return produit;
    }
    
    /**
     * Récupérer les produits en alerte (stock <= seuil)
     * @return Liste des produits en alerte
     */
    public List<Produit> findProduitsEnAlerte() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.*, c.libelle FROM Produit p " +
                     "LEFT JOIN Categorie c ON p.idCat = c.idCat " +
                     "WHERE p.stockActuel <= p.seuilAlerte";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProd(rs.getString("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setPrixVente(rs.getDouble("prixDeVente"));
                produit.setSeuilAlerte(rs.getInt("seuilAlerte"));
                produit.setStockActuel(rs.getInt("stockActuel"));
                
                Categorie categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));
                produit.setCategorie(categorie);
                
                produits.add(produit);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits en alerte: " + e.getMessage());
            e.printStackTrace();
        }
        
        return produits;
    }
    
    /**
     * Mettre à jour uniquement le stock d'un produit
     * @param idProd L'ID du produit
     * @param nouvelleQuantite La nouvelle quantité en stock
     * @return true si la mise à jour a réussi
     */
    public boolean updateStock(String idProd, int nouvelleQuantite) {
        String sql = "UPDATE Produit SET stockActuel = ? WHERE idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setInt(1, nouvelleQuantite);
            pst.setString(2, idProd);
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Récupérer les produits d'une catégorie
     * @param idCat L'ID de la catégorie
     * @return Liste des produits de cette catégorie
     */
    public List<Produit> findByCategorie(String idCat) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.*, c.libelle FROM Produit p " +
                     "LEFT JOIN Categorie c ON p.idCat = c.idCat " +
                     "WHERE p.idCat = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCat);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProd(rs.getString("idProd"));
                produit.setNom(rs.getString("nom"));
                produit.setPrixVente(rs.getDouble("prixDeVente"));
                produit.setSeuilAlerte(rs.getInt("seuilAlerte"));
                produit.setStockActuel(rs.getInt("stockActuel"));
                
                Categorie categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));
                produit.setCategorie(categorie);
                
                produits.add(produit);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par catégorie: " + e.getMessage());
            e.printStackTrace();
        }
        
        return produits;
    }
}