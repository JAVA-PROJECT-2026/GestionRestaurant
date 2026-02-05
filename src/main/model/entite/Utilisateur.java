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
public class Utilisateur {
    private String id;
    private String login;
    private String password;
    
    public Utilisateur(String login, String password){
        this.id = UUID.randomUUID().toString();
        this.login = login;
        this.password = password;
    }
    
    public Utilisateur() {}    
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    public String getLogin(){
        return this.login;
    }
    
    public void setLogin(String login){
        this.login = login;   
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
