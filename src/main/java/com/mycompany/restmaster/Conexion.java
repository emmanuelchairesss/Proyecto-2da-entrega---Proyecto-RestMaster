/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restmaster;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/**
 *
 * @author LENOVO
 */

public class Conexion {
    Connection conectar=null;
    String usuario="Emmaaa";
    String contrasena="1234";
    String bd="RestMasterMalichas";
    String ip="localhost";
    String puerto="1433";
    
  String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + ";databaseName=" + bd + ";encrypt=true;trustServerCertificate=true;";

    public Connection establecerConexion(){
     try {
            if (conectar == null || conectar.isClosed()) {
                conectar = DriverManager.getConnection(cadena, usuario, contrasena);
                System.out.println("Se conecto correctamente a la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conectar;
    }
    
    public void cerrarConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexion: " + e.getMessage());
        }
    }
}


