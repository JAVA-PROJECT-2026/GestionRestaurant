/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.util.UUID;

/**
 *
 * @author isaac
 */

/**
 * Modèle représentant un Produit
 */
public class Produit {
    
    private String id;
    private String nom;
    private Categorie categorie; // Relation POO : On utilise l'objet, pas l'ID
    private double prixVente;
    private int stockActuel;
    private int seuilAlerte;

    
    public Produit() {}

    
    public Produit(int id, String nom, Categorie categorie, double prixVente, int stockActuel, int seuilAlerte) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.categorie = categorie;
        this.prixVente = prixVente; 
        this.stockActuel = stockActuel; 
        this.seuilAlerte = seuilAlerte;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        if (prixVente > 0) {
            this.prixVente = prixVente;
        } else {
            System.err.println("Le prix de vente doit être strictement positif.");
            throw new IllegalArgumentException("Le prix de vente doit être strictement positif.");
        }
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        if (stockActuel >= 0) {
            this.stockActuel = stockActuel;
        } else {
            System.err.println("Le stock ne peut pas être négatif.");
            throw new IllegalArgumentException("Le stock ne peut pas être négatif.");
        }
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    // Utile pour afficher le nom du produit dans les JComboBox de l'interface Swing
    @Override
    public String toString() {
        return this.nom;
    }
}