/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author isaac
 */
public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/GestionRestaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "Sedaminou111002";
            
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    
    public static void main(String[] args) {
        try {
            Connection con = getConnection();
            if (con != null) {
                System.out.println("✅ CONNEXION RÉUSSIE à GestionRestaurant !");
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ ÉCHEC DE LA CONNEXION : " + e.getMessage());
        }
    }
    
}
