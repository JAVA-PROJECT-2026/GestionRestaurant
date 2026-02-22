/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import main.model.dao.StatistiqueDAO;
import java.util.Map;

/**
 * Controller pour la gestion des statistiques du restaurant.
 * Sert de pont entre la vue (StatistiquesPanel) et la couche DAO.
 * @author isaac
 */
public class StatistiqueController {

    private StatistiqueDAO statistiqueDAO;

    public StatistiqueController() {
        this.statistiqueDAO = new StatistiqueDAO();
    }

    // =========================================================
    //  KPI GLOBAUX (cartes en haut du dashboard)
    // =========================================================

    /** Chiffre d'affaires total des commandes validées
     * @return  */
    public double getCATotal() {
        return statistiqueDAO.getCATotal();
    }

    /** Nombre total de commandes validées
     * @return  */
    public int getNbCommandesValidees() {
        return statistiqueDAO.getNbCommandesValidees();
    }

    /** Panier moyen par commande validée
     * @return  */
    public double getPanierMoyen() {
        return statistiqueDAO.getPanierMoyen();
    }

    /** Nombre total de produits dans le catalogue
     * @return  */
    public int getNbProduits() {
        return statistiqueDAO.getNbProduits();
    }

    /** Nombre de produits en alerte de stock
     * @return  */
    public int getNbProduitsEnAlerte() {
        return statistiqueDAO.getNbProduitsEnAlerte();
    }

    // =========================================================
    //  DONNÉES POUR LES GRAPHIQUES
    // =========================================================

    /**
     * CA par jour (30 derniers jours) → pour la courbe CA
     * @return Map<date "YYYY-MM-DD", montant>
     */
    public Map<String, Double> getCAParJour() {
        return statistiqueDAO.getCAParJour();
    }

    /**
     * CA par semaine (12 dernières semaines) → pour la courbe CA hebdo
     * @return Map<"Sem. N", montant>
     */
    public Map<String, Double> getCAParSemaine() {
        return statistiqueDAO.getCAParSemaine();
    }

    /**
     * Nombre de commandes validées par jour → pour la courbe d'activité
     * @return Map<date "YYYY-MM-DD", count>
     */
    public Map<String, Integer> getNbCommandesParJour() {
        return statistiqueDAO.getNbCommandesParJour();
    }

    /**
     * Répartition des commandes par état → pour l'histogramme états
     * @return Map<"EN_COURS"|"VALIDEE"|"ANNULEE", count>
     */
    public Map<String, Integer> getCommandesParEtat() {
        return statistiqueDAO.getCommandesParEtat();
    }

    /**
     * Top 5 produits les plus vendus → pour l'histogramme top produits
     * @return Map<nomProduit, quantitéVendue>
     */
    public Map<String, Integer> getTopProduits() {
        return statistiqueDAO.getTopProduits(5);
    }

    /**
     * Top N produits les plus vendus (personnalisable)
     * @param n nombre de produits
     * @return Map<nomProduit, quantitéVendue>
     */
    public Map<String, Integer> getTopProduits(int n) {
        return statistiqueDAO.getTopProduits(n);
    }

    /**
     * Nombre de produits par catégorie → pour l'histogramme catégories
     * @return Map<libelleCategorie, nbProduits>
     */
    public Map<String, Integer> getProduitsParCategorie() {
        return statistiqueDAO.getProduitsParCategorie();
    }
}