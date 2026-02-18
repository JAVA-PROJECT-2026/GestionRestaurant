/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import main.model.entite.enums.TypeMouvement;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author isaac
 */
public class MouvementStock {
    


    private TypeMouvement typeMouv;
    private String idMouv;
    private String idProd;
    private int quantite;
    private Date dateMouv;
    private String motif;

    
    public MouvementStock(){}
    
    public MouvementStock(TypeMouvement typeMouv, String idProd, int quantite, String motif ) {
        this.idMouv = UUID.randomUUID().toString();
        this.idProd = idProd;
        this.quantite = quantite;
        this.typeMouv = typeMouv;
        this.motif = motif;
    }

    public String getIdMouv() {
        return idMouv;
    }

    public void setIdMouv(String idMouv) {
        this.idMouv = idMouv;
    }

    public TypeMouvement getTypeMouv() {
        return typeMouv;
    }

    public void setTypeMouv(TypeMouvement typeMouv) {
        this.typeMouv = typeMouv;   
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
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantite ne peut pas etre négatif");
        }
        this.quantite = quantite;
    }

    public Date getDateMouv() {
        return dateMouv;
    }

    public void setDateMouv(Date dateMouv) {
        this.dateMouv = dateMouv;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
