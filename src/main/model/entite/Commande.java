/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.util.Date;
import java.util.UUID;
import main.model.entite.enums.EtatCommande;

/**
 *
 * @author isaac
 */


public class Commande {
    private String idCom;
    private Date dateCommande;
    private EtatCommande etat;
    private double total;

    public Commande(Date dateCommande, EtatCommande etat, double total) {
        this.idCom = UUID.randomUUID().toString();
        this.dateCommande = dateCommande;
        this.etat = etat;
        this.total = total;
        
    }
    public Commande(){}

    public String getIdCom() {
        return idCom;
    }

    public void setIdCom(String idCom) {
        this.idCom = idCom;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }
    
    public double getTotalCommande(){
        return total;
    }
    
    public void setTotalCommande(double total){
        this.total =total;
    }
    
}
