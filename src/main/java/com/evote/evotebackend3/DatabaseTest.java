package com.evote.evotebackend3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

    public static void main(String[] args) {
       try{
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/evote_database3.0", "root", "");

           if(connection != null){
               System.out.println("Connexion reussie avec succes");
           }

       } catch (SQLException e) {
           System.out.println("Erreur de connexion : "+e.getMessage());
       }

    }

}
