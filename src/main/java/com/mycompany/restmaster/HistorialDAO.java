/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restmaster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author valen
 */
public class HistorialDAO {

    private Conexion conexion = new Conexion();

    public void obtenerListadoHistorial(DefaultTableModel modeloTabla) {

        Connection conMySQL = conexion.establecerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        modeloTabla.setRowCount(0);

        try {

            String consulta =
                    "SELECT " +
                    "r.IdReserva, " +
                    "r.Nombre, " +
                    "r.Telefono, " +
                    "r.Correo, " +
                    "r.Fecha, " +
                    "r.Hora, " +
                    "r.Personas, " +
                    "m.NumeroMesa AS Mesa, " +
                    "r.Estado " +
                    "FROM TblReservarMesas r " +
                    "INNER JOIN TblMesas m ON r.IdMesa = m.IdMesa";

            pstm = conMySQL.prepareStatement(consulta);
            rs = pstm.executeQuery();

            while (rs.next()) {

                Object[] fila = {
                    rs.getInt("IdReserva"),
                    rs.getString("Nombre"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getString("Fecha"),
                    rs.getString("Hora"),
                    rs.getInt("Personas"),
                    rs.getInt("Mesa"),
                    rs.getString("Estado"),
                    "",
                    ""
                };

                modeloTabla.addRow(fila);
            }

        } catch (SQLException ex) {

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                conexion.cerrarConexion();

            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    public boolean eliminarCliente(int idReserva) {

        Connection conMySQL = conexion.establecerConexion();
        PreparedStatement pstm = null;
        boolean eliminado = false;

        try {

            String consulta =
                    "DELETE FROM TblReservarMesas WHERE IdReserva = ?";

            pstm = conMySQL.prepareStatement(consulta);
            pstm.setInt(1, idReserva);

            int filasAfectadas = pstm.executeUpdate();

            eliminado = filasAfectadas > 0;

        } catch (SQLException ex) {

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pstm != null) pstm.close();
                conexion.cerrarConexion();

            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return eliminado;
    }

    public void buscarPorCampo(String campo, String valorBuscado, DefaultTableModel modeloTabla) {

        Connection conMySQL = conexion.establecerConexion();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        modeloTabla.setRowCount(0);

        try {

            String consulta =
                    "SELECT " +
                    "r.IdReserva, " +
                    "r.Nombre, " +
                    "r.Telefono, " +
                    "r.Correo, " +
                    "r.Fecha, " +
                    "r.Hora, " +
                    "r.Personas, " +
                    "m.NumeroMesa AS Mesa, " +
                    "r.Estado " +
                    "FROM TblReservarMesas r " +
                    "INNER JOIN TblMesas m ON r.IdMesa = m.IdMesa " +
                    "WHERE " + campo + " LIKE ?";

            pstm = conMySQL.prepareStatement(consulta);
            pstm.setString(1, "%" + valorBuscado + "%");

            rs = pstm.executeQuery();

            while (rs.next()) {

                Object[] fila = {
                    rs.getInt("IdReserva"),
                    rs.getString("Nombre"),
                    rs.getString("Telefono"),
                    rs.getString("Correo"),
                    rs.getString("Fecha"),
                    rs.getString("Hora"),
                    rs.getInt("Personas"),
                    rs.getInt("Mesa"),
                    rs.getString("Estado"),
                    "",
                    ""
                };

                modeloTabla.addRow(fila);
            }

        } catch (SQLException ex) {

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                conexion.cerrarConexion();

            } catch (SQLException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
