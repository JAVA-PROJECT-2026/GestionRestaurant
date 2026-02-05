/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;
<<<<<<< HEAD

=======
<<<<<<< HEAD
>>>>>>> 9188760 (Ajout DAO commande)
import main.util.DatabaseConnection;
=======
>>>>>>> 38fb5e7 (Ajout DAO commande)
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.Commande;
import main.model.entite.enums.EtatCommande;

/**
 * DAO pour la gestion des commandes
 * @author isaac
 */
public class CommandeDAO {
    
    /**
     * Insérer une nouvelle commande
     * @param commande La commande à insérer
     * @return true si l'insertion a réussi
     */
    public boolean insert(Commande commande) {
        String sql = "INSERT INTO Commande (idCom, dateCommande, etat, total) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, commande.getIdCom());
            pst.setTimestamp(2, new Timestamp(commande.getDateCommande().getTime()));
            pst.setString(3, commande.getEtat().name());
            pst.setDouble(4, commande.getTotalCommande());
           
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la commande: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
 
    /**
     * Mettre à jour une commande (état et total)
     * @param commande La commande à mettre à jour
     * @return true si la mise à jour a réussi
     */
    public boolean update(Commande commande) {
        // ✅ CORRIGÉ: correspondance entre SQL et paramètres
        String sql = "UPDATE Commande SET etat = ?, total = ? WHERE idCom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, commande.getEtat().name());
            pst.setDouble(2, commande.getTotalCommande());
            pst.setString(3, commande.getIdCom());
           
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la commande: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Supprimer une commande
     * @param commande La commande à supprimer
     * @return true si la suppression a réussi
     */
    public boolean delete(Commande commande) {
        String sql = "DELETE FROM Commande WHERE idCom = ?";
           
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, commande.getIdCom());
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la commande: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Trouver une commande par ID
     * @param idCom L'ID de la commande
     * @return La commande trouvée ou null
     */
    public Commande findById(String idCom) {
        String sql = "SELECT * FROM Commande WHERE idCom = ?";
        Commande commande = null;
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                commande = new Commande();
                commande.setIdCom(rs.getString("idCom"));
                commande.setDateCommande(rs.getTimestamp("dateCommande"));
                commande.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                commande.setTotalCommande(rs.getDouble("total"));
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la commande: " + e.getMessage());
            e.printStackTrace();
        }
        
        return commande;
    }
    
    /**
     * Récupérer toutes les commandes
     * @return Liste de toutes les commandes
     */
    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande ORDER BY dateCommande DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCom(rs.getString("idCom"));
                commande.setDateCommande(rs.getTimestamp("dateCommande"));
                commande.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                commande.setTotalCommande(rs.getDouble("total"));
                
                commandes.add(commande);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des commandes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return commandes;
    }
    
    /**
     * Récupérer les commandes par état
     * @param etat L'état des commandes à récupérer
     * @return Liste des commandes avec cet état
     */
    public List<Commande> findByEtat(EtatCommande etat) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande WHERE etat = ? ORDER BY dateCommande DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, etat.name());
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCom(rs.getString("idCom"));
                commande.setDateCommande(rs.getTimestamp("dateCommande"));
                commande.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                commande.setTotalCommande(rs.getDouble("total"));
                
                commandes.add(commande);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par état: " + e.getMessage());
            e.printStackTrace();
        }
        
        return commandes;
    }
    
    /**
     * Récupérer les commandes d'une période
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des commandes de cette période
     */
    public List<Commande> findByPeriode(Date dateDebut, Date dateFin) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande WHERE dateCommande BETWEEN ? AND ? ORDER BY dateCommande DESC";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setTimestamp(1, new Timestamp(dateDebut.getTime()));
            pst.setTimestamp(2, new Timestamp(dateFin.getTime()));
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCom(rs.getString("idCom"));
                commande.setDateCommande(rs.getTimestamp("dateCommande"));
                commande.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                commande.setTotalCommande(rs.getDouble("total"));
                
                commandes.add(commande);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par période: " + e.getMessage());
            e.printStackTrace();
        }
        
        return commandes;
    }
    
    /**
     * Calculer le chiffre d'affaires total des commandes validées
     * @return Le chiffre d'affaires total
     */
    public double getChiffreAffairesTotal() {
        String sql = "SELECT SUM(total) as ca FROM Commande WHERE etat = 'VALIDEE'";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("ca");
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul du CA: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    /**
     * Compter le nombre de commandes par état
     * @param etat L'état à compter
     * @return Le nombre de commandes
     */
    public int countByEtat(EtatCommande etat) {
        String sql = "SELECT COUNT(*) as nb FROM Commande WHERE etat = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, etat.name());
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("nb");
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}