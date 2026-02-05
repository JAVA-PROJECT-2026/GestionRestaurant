/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import main.model.entite.enums.TypeMouvement;
import java.time.LocalDateTime;

/**
 *
 * @author isaac
 */
public class MouvementStock {
    
    private int idMouv;
    private TypeMouvement typeMouv;
    private Produit produit;
    private int quantite;
    private LocalDateTime dateMouv;
    private String motif;

    public MouvementStock() {}

    public int getIdMouv() {
        return idMouv;
    }

    public void setIdMouv(int idMouv) {
        this.idMouv = idMouv;
    }

    public String getTypeMouv() {
        return typeMouv.name();
    }

    public void setTypeMouv(TypeMouvement typeMouv) {
        this.typeMouv = typeMouv;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProd(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantite ne peut pas etre négatif");
        }
        this.quantite = quantite;
    }

    public LocalDateTime getDateMouv() {
        return dateMouv;
    }

    public void setDateMouv(LocalDateTime dateMouv) {
        this.dateMouv = dateMouv;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
