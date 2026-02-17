/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.util.DatabaseConnection;
import main.model.entite.MouvementStock;
import main.model.entite.enums.TypeMouvement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.Produit;

/**
 * DAO pour la gestion des mouvements de stock
 * @author isaac
 */
public class StockDAO {
    
    /**
     * Insérer un nouveau mouvement de stock
     * @param stock Le mouvement à insérer
     * @return true si l'insertion a réussi
     */
    public boolean insertStock(MouvementStock stock) {
       
        String sql = "INSERT INTO MouvementStock (idMouv, typeMouv, idProd, quantite, dateMouv, motif) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, stock.getIdMouv());
            pst.setString(2, stock.getTypeMouv());
            pst.setString(3, stock.getIdProd());
            pst.setInt(4, stock.getQuantite());
            pst.setTimestamp(5, new Timestamp(stock.getDateMouv().getTime()));
            pst.setString(6, stock.getMotif());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du mouvement: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Récupérer tous les mouvements de stock
     * @return Liste de tous les mouvements
     */
    public List<MouvementStock> getAllStock() {
        List<MouvementStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock ORDER BY dateMouv DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                MouvementStock stock = new MouvementStock();
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setIdProd(rs.getString("idProd"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setDateMouv(rs.getTimestamp("dateMouv"));
                stock.setMotif(rs.getString("motif"));
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des mouvements: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    /**
     * Récupérer les mouvements avec détails des produits
     * @return Liste des mouvements avec noms de produits
     */
    public List<Object[]> getAllStockWithDetails() {
        List<Object[]> stocks = new ArrayList<>();
        String sql = "SELECT ms.*, p.nom " +
                     "FROM MouvementStock ms " +
                     "JOIN Produit p ON ms.idProd = p.idProd " +
                     "ORDER BY ms.dateMouv DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] stock = new Object[7];
                stock[0] = rs.getString("idMouv");
                stock[1] = rs.getString("typeMouv");
                stock[2] = rs.getString("nom");  // nom du produit
                stock[3] = rs.getInt("quantite");
                stock[4] = rs.getTimestamp("dateMouv");
                stock[5] = rs.getString("motif");
                stock[6] = rs.getString("idProd");
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des détails: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    /**
     * Supprimer un mouvement de stock
     * @param stock Le mouvement à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteStock(MouvementStock stock) {
        String sql = "DELETE FROM MouvementStock WHERE idMouv = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, stock.getIdMouv());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du mouvement: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mettre à jour un mouvement de stock
     * @param stock Le mouvement à mettre à jour
     * @return true si la mise à jour a réussi
     */
    public boolean updateStock(MouvementStock stock) {

        String sql = "UPDATE MouvementStock SET typeMouv = ?, idProd = ?, quantite = ?, dateMouv = ?, motif = ? WHERE idMouv = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, stock.getTypeMouv());
            pst.setString(2, stock.getIdProd());
            pst.setInt(3, stock.getQuantite());
            pst.setTimestamp(4, new Timestamp(stock.getDateMouv().getTime()));
            pst.setString(5, stock.getMotif());
            pst.setString(6, stock.getIdMouv());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Mise à jour du mouvement réussie !");
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du mouvement: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Trouver un mouvement par ID
     * @param idMouv L'ID du mouvement
     * @return Le mouvement trouvé ou null
     */
    public MouvementStock findById(String idMouv) {
        String sql = "SELECT * FROM MouvementStock WHERE idMouv = ?";
        MouvementStock stock = null;
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idMouv);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                stock = new MouvementStock();
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setIdProd(rs.getString("idProd"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setDateMouv(rs.getTimestamp("dateMouv"));
                stock.setMotif(rs.getString("motif"));
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du mouvement: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stock;
    }
    
    /**
     * Récupérer les mouvements d'un produit
     * @param idProd L'ID du produit
     * @return Liste des mouvements de ce produit
     */
    public List<MouvementStock> findByProduit(String idProd) {
        List<MouvementStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock WHERE idProd = ? ORDER BY dateMouv DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idProd);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                MouvementStock stock = new MouvementStock();
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setIdProd(rs.getString("idProd"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setDateMouv(rs.getTimestamp("dateMouv"));
                stock.setMotif(rs.getString("motif"));
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par produit: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    /**
     * Récupérer les mouvements par type
     * @param type Le type de mouvement (ENTREE ou SORTIE)
     * @return Liste des mouvements de ce type
     */
    public List<MouvementStock> findByType(TypeMouvement type) {
        List<MouvementStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock WHERE typeMouv = ? ORDER BY dateMouv DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, type.name());
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                MouvementStock stock = new MouvementStock();
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setIdProd(rs.getString("idProd"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setDateMouv(rs.getTimestamp("dateMouv"));
                stock.setMotif(rs.getString("motif"));
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par type: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    /**
     * Récupérer les mouvements d'une période
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des mouvements de cette période
     */
    public List<MouvementStock> findByPeriode(Date dateDebut, Date dateFin) {
        List<MouvementStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock WHERE dateMouv BETWEEN ? AND ? ORDER BY dateMouv DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setTimestamp(1, new Timestamp(dateDebut.getTime()));
            pst.setTimestamp(2, new Timestamp(dateFin.getTime()));
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                MouvementStock stock = new MouvementStock();
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setIdProd(rs.getString("idProd"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setDateMouv(rs.getTimestamp("dateMouv"));
                stock.setMotif(rs.getString("motif"));
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par période: " + e.getMessage());
            e.printStackTrace();
        }
        
        return stocks;
    }
    
    /**
     * Vérifier si un produit a suffisamment de stock
     * @param idProd L'ID du produit
     * @param qte La quantité demandée
     * @return true si le stock est suffisant
     */
    public boolean hasEnoughStock(String idProd, int qte) {
        ProduitDAO produitDAO = new ProduitDAO();
        Produit p = produitDAO.findById(idProd);
        
        if (p != null && p.getStockActuel()>= qte) {
            return true;
        }
        return false;
    }
}


    
    
    