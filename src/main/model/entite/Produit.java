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
    
    private String idProd;
    private String nom;
    private double prixDeVente;
    private int stockActuel;
    private int seuilAlerte;
    private Categorie categorie;

    public Produit() {}
    public Produit(String nom, double prixDeVente, int stockActuel, int seuilAlerte, String idCat) {
    this.idProd = UUID.randomUUID().toString();
    this.nom = nom;
    this.prixDeVente = prixDeVente;
    this.stockActuel = stockActuel;
    this.seuilAlerte = seuilAlerte;

    this.categorie = new Categorie();
    this.categorie.setIdCat(idCat);
}



    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
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
        return this.prixDeVente;
    }

    public void setPrixVente(double prixVente) {
        if (prixVente > 0) {
            this.prixDeVente = prixVente;
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

        this.stockActuel = stockActuel;

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

