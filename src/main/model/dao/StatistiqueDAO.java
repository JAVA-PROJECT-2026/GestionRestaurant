/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.util.DatabaseConnection;
import java.sql.Connection;
import java.util.*;
import java.sql.*;
/**
 * DAO dédié aux statistiques du restaurant
 * Fournit toutes les requêtes analytiques pour le dashboard
 * @author isaac
 */
public class StatistiqueDAO {

    // =========================================================
    //  CHIFFRE D'AFFAIRES
    // =========================================================

    /**
     * CA par jour sur les 30 derniers jours (commandes validées)
     * @return Map<"YYYY-MM-DD", montant>
     */
    public Map<String, Double> getCAParJour() {
        Map<String, Double> result = new LinkedHashMap<>();
        String sql = "SELECT DATE(dateCommande) as jour, SUM(total) as ca " +
                     "FROM Commande " +
                     "WHERE etat = 'VALIDEE' " +
                     "  AND dateCommande >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY DATE(dateCommande) " +
                     "ORDER BY jour ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.put(rs.getString("jour"), rs.getDouble("ca"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getCAParJour: " + e.getMessage());
        }
        return result;
    }

    /**
     * CA par semaine sur les 12 dernières semaines
     * @return Map<"Semaine N", montant>
     */
    public Map<String, Double> getCAParSemaine() {
        Map<String, Double> result = new LinkedHashMap<>();
        String sql = "SELECT CONCAT('Sem. ', WEEK(dateCommande)) as semaine, " +
                     "       SUM(total) as ca " +
                     "FROM Commande " +
                     "WHERE etat = 'VALIDEE' " +
                     "  AND dateCommande >= DATE_SUB(CURDATE(), INTERVAL 12 WEEK) " +
                     "GROUP BY WEEK(dateCommande) " +
                     "ORDER BY WEEK(dateCommande) ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.put(rs.getString("semaine"), rs.getDouble("ca"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getCAParSemaine: " + e.getMessage());
        }
        return result;
    }

    // =========================================================
    //  COMMANDES
    // =========================================================

    /**
     * Nombre de commandes par état
     * @return Map<"EN_COURS"|"VALIDEE"|"ANNULEE", count>
     */
    public Map<String, Integer> getCommandesParEtat() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT etat, COUNT(*) as nb FROM Commande GROUP BY etat";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.put(rs.getString("etat"), rs.getInt("nb"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getCommandesParEtat: " + e.getMessage());
        }
        return result;
    }

    /**
     * Nombre de commandes validées par jour (30 derniers jours)
     * @return Map<"YYYY-MM-DD", count>
     */
    public Map<String, Integer> getNbCommandesParJour() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT DATE(dateCommande) as jour, COUNT(*) as nb " +
                     "FROM Commande " +
                     "WHERE etat = 'VALIDEE' " +
                     "  AND dateCommande >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                     "GROUP BY DATE(dateCommande) " +
                     "ORDER BY jour ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.put(rs.getString("jour"), rs.getInt("nb"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getNbCommandesParJour: " + e.getMessage());
        }
        return result;
    }

    // =========================================================
    //  PRODUITS
    // =========================================================

    /**
     * Top N produits les plus vendus (commandes validées)
     * @param limite nombre de produits à retourner
     * @return Map<nomProduit, quantitéTotaleVendue>
     */
    public Map<String, Integer> getTopProduits(int limite) {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT p.nom, SUM(lc.quantite) as totalVendu " +
                     "FROM LigneCommande lc " +
                     "JOIN Produit p ON lc.idProd = p.idProd " +
                     "JOIN Commande c ON lc.idCom = c.idCom " +
                     "WHERE c.etat = 'VALIDEE' " +
                     "GROUP BY lc.idProd, p.nom " +
                     "ORDER BY totalVendu DESC " +
                     "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, limite);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("nom"), rs.getInt("totalVendu"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getTopProduits: " + e.getMessage());
        }
        return result;
    }

    /**
     * Nombre de produits par catégorie
     * @return Map<libelleCategorie, nbProduits>
     */
    public Map<String, Integer> getProduitsParCategorie() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT c.libelle, COUNT(p.idProd) as nb " +
                     "FROM Categorie c " +
                     "LEFT JOIN Produit p ON c.idCat = p.idCat " +
                     "GROUP BY c.idCat, c.libelle " +
                     "ORDER BY nb DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.put(rs.getString("libelle"), rs.getInt("nb"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur getProduitsParCategorie: " + e.getMessage());
        }
        return result;
    }

    /**
     * Produits en rupture de stock (stockActuel <= seuilAlerte)
     * @return nombre de produits en alerte
     */
    public int getNbProduitsEnAlerte() {
        String sql = "SELECT COUNT(*) FROM Produit WHERE stockActuel <= seuilAlerte";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Erreur getNbProduitsEnAlerte: " + e.getMessage());
        }
        return 0;
    }

    // =========================================================
    //  KPI GLOBAUX
    // =========================================================

    /**
     * Chiffre d'affaires total des commandes validées
     */
    public double getCATotal() {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM Commande WHERE etat = 'VALIDEE'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            System.err.println("Erreur getCATotal: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Nombre total de commandes validées
     */
    public int getNbCommandesValidees() {
        String sql = "SELECT COUNT(*) FROM Commande WHERE etat = 'VALIDEE'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Erreur getNbCommandesValidees: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Panier moyen (CA total / nb commandes validées)
     */
    public double getPanierMoyen() {
        String sql = "SELECT AVG(total) FROM Commande WHERE etat = 'VALIDEE'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            System.err.println("Erreur getPanierMoyen: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Nombre total de produits dans le catalogue
     */
    public int getNbProduits() {
        String sql = "SELECT COUNT(*) FROM Produit";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Erreur getNbProduits: " + e.getMessage());
        }
        return 0;
    }
}