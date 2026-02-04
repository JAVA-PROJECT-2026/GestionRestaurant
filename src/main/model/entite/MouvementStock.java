/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.time.LocalDateTime;


/**
 *
 * @author isaac
 */
public class MouvementStock {
    
     private int idMouv;
    private String typeMouv;
    private int idProd;
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
        return typeMouv;
    }

    public void setTypeMouv(String typeMouv) {
        this.typeMouv = typeMouv;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
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
