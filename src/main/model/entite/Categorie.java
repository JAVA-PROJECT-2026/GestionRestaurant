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

public class Categorie {
    
    private String idCat;
    private String libelle;
    private String description;  // ✅ NOUVEAU
    
    public String getLibelle(){
        return this.libelle;
    }
    
    public String getIdCat(){
        return this.idCat;
    }
    
    // ✅ NOUVEAU - Getter pour description
    public String getDescription(){
        return this.description;
    }
    
    // sera utiliser pour afficher les categorie dans les interfaces swing
    @Override
    public String toString(){
        return this.libelle;
    }
    
    public Categorie(String libelle) {
        this.idCat = UUID.randomUUID().toString();
        this.libelle = libelle;
    }
    
    // ✅ NOUVEAU - Constructeur avec description
    public Categorie(String libelle, String description) {
        this.idCat = UUID.randomUUID().toString();
        this.libelle = libelle;
        this.description = description;
    }
    
    public Categorie(){}
    
    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    // ✅ NOUVEAU - Setter pour description
    public void setDescription(String description) {
        this.description = description;
    }
}