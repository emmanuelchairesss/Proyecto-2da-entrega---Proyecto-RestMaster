/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restmaster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author allmi
 */
public class InventarioDAO {
    
    private Conexion conexion = new Conexion();
    
   
    public void obtenerListadoProductos(DefaultTableModel modeloTabla) {

    Connection conMySQL = conexion.establecerConexion();
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try {
        String consulta = "SELECT " +
                "p.id_Producto, " +
                "p.nomProducto, " +
                "p.precioProducto, " +
                "e.cantidad " +
                "FROM TblProductos p " +
                "INNER JOIN TblExistenciaProductos e " +
                "ON p.id_Producto = e.id_Producto";

        pstm = conMySQL.prepareStatement(consulta);
        rs = pstm.executeQuery();

        while (rs.next()) {

            Object[] fila = {
                rs.getInt("id_Producto"),
                rs.getString("nomProducto"),
                rs.getDouble("precioProducto"),
                rs.getInt("cantidad"),
                "",
                ""
            };

            modeloTabla.addRow(fila);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            conexion.cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
    public Producto obtenerProducto(int idProducto) {

    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    Producto productoDevuelto = null;

    try {
        con = conexion.establecerConexion();

        String consulta = "SELECT " +
                "p.id_Producto, " +
                "p.nomProducto, " +
                "p.precioProducto, " +
                "e.cantidad " +
                "FROM TblProductos p " +
                "INNER JOIN TblExistenciaProductos e " +
                "ON p.id_Producto = e.id_Producto " +
                "WHERE p.id_Producto = ?";

        pstm = con.prepareStatement(consulta);
        pstm.setInt(1, idProducto);

        rs = pstm.executeQuery();

        if (rs.next()) {
            productoDevuelto = new Producto();

            productoDevuelto.setIdProducto(rs.getInt("id_Producto"));
            productoDevuelto.setNombreProducto(rs.getString("nomProducto"));
            productoDevuelto.setPrecioProducto(rs.getDouble("precioProducto"));
            productoDevuelto.setCantidadProducto(rs.getInt("cantidad"));
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (con != null) conexion.cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return productoDevuelto;
}
    
   public int actualizarProducto(Producto productoActualizar) {
    Connection con = null;
    PreparedStatement pstm = null;
    int registrosAfectados = 0;

    try {
        con = conexion.establecerConexion();

       
        String updateProducto = "UPDATE TblProductos SET nomProducto=?, precioProducto=? WHERE id_Producto=?";
        pstm = con.prepareStatement(updateProducto);
        pstm.setString(1, productoActualizar.getNombreProducto());
        pstm.setDouble(2, productoActualizar.getPrecioProducto());
        pstm.setInt(3, productoActualizar.getIdProducto());

        pstm.executeUpdate();

        String updateExistencia = "UPDATE TblExistenciaProductos SET cantidad=? WHERE id_Producto=?";
        pstm = con.prepareStatement(updateExistencia);
        pstm.setInt(1, productoActualizar.getCantidadProducto());
        pstm.setInt(2, productoActualizar.getIdProducto());

        registrosAfectados = pstm.executeUpdate();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return registrosAfectados;
}
    
   public int insertarProducto(Producto productoNuevo) {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    int registrosAfectados = 0;

    try {
        con = conexion.establecerConexion();

       
        String insertProducto = "INSERT INTO TblProductos (nomProducto, precioProducto) VALUES (?, ?)";
        pstm = con.prepareStatement(insertProducto, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, productoNuevo.getNombreProducto());
        pstm.setDouble(2, productoNuevo.getPrecioProducto());

        registrosAfectados = pstm.executeUpdate();

        rs = pstm.getGeneratedKeys();
        int idProducto = 0;

        if (rs.next()) {
            idProducto = rs.getInt(1);
        }

        String insertExistencia = "INSERT INTO TblExistenciaProductos (id_Producto, cantidad) VALUES (?, ?)";
        pstm = con.prepareStatement(insertExistencia);
        pstm.setInt(1, idProducto);
        pstm.setInt(2, productoNuevo.getCantidadProducto());

        pstm.executeUpdate();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return registrosAfectados;
}
    
   public int eliminarProducto(int idProducto) {
    Connection con = null;
    PreparedStatement pstm = null;
    int registrosAfectados = 0;

    try {
        con = conexion.establecerConexion();

        String deleteExistencia = "DELETE FROM TblExistenciaProductos WHERE id_Producto = ?";
        pstm = con.prepareStatement(deleteExistencia);
        pstm.setInt(1, idProducto);
        pstm.executeUpdate();

        String deleteProducto = "DELETE FROM TblProductos WHERE id_Producto = ?";
        pstm = con.prepareStatement(deleteProducto);
        pstm.setInt(1, idProducto);
        registrosAfectados = pstm.executeUpdate();

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (pstm != null) pstm.close();
            if (con != null) conexion.cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return registrosAfectados;
}
}
