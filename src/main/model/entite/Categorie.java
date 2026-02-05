/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;

/**
 *
 * @author isaac
 */
public class Categorie {
    
    private int idCat;
    private String libelle;
    
    public String getLibelle(){
        return this.libelle;
    }
    
    public int getIdCat(){
        return this.idCat;
    }
    
    // sera utiliser pour afficher les categorie dans les interfaces swing
    @Override
    public String toString(){
        return this.libelle;
    }
    
    
}
