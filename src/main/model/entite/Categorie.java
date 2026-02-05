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
    
    public String getLibelle(){
        return this.libelle;
    }
    
    public String getIdCat(){
        return this.idCat;
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
    
    public Categorie(){}


    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


}
