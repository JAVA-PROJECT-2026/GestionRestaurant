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
public class LigneCommande {
    
    private String idCom;
    private String idProd;
    private int quantite;
    private double prixUnitaire;
    private double montantLigne;
    
    public LigneCommande() {}
    
public LigneCommande(String idCom, String idProd, int quantite, double prixUnitaire){
    this.idCom = idCom;
    this.idProd = idProd;
    this.prixUnitaire = prixUnitaire;
    this.quantite = quantite;
}

    public String getIdCom() {
        return idCom;
    }

    public void setIdCom(String idCom) {
        this.idCom = idCom;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMontantLigne() {
        return montantLigne;
    }
    // c'est pas une érreur, je n'ai pasvolontairement fait le setters de montantLigne
    
}
