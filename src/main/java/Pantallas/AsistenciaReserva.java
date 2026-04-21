/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Pantallas;

import com.mycompany.restmaster.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class AsistenciaReserva extends javax.swing.JPanel {
private int idReservaActual = -1;

    /**
     * Creates new form AsistenciaReserva
     */
    public AsistenciaReserva() {
        initComponents();
       
        
        chkSi.addActionListener(e -> {
    if (chkSi.isSelected()) {
        chkNo.setSelected(false);
        txtLlegada.setEnabled(true);  
    } else {
        txtLlegada.setEnabled(false); 
        txtLlegada.setText("");       
    }
});

chkNo.addActionListener(e -> {
    if (chkNo.isSelected()) {
        chkSi.setSelected(false);
        txtLlegada.setEnabled(false); 
        txtLlegada.setText("");       
    } else {
        txtLlegada.setEnabled(true);  
    }
});


    }
    
   public void buscarReserva(String C) {
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();

    try {
        String sql;
        PreparedStatement ps;

        if (C.matches("\\d+")) {
            sql = "SELECT r.*, m.NumeroMesa FROM TblReservarMesas r " +
                  "INNER JOIN TblMesas m ON r.IdMesa = m.IdMesa " +
                  "WHERE r.IdReserva = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(C));
        } else {
            sql = "SELECT r.*, m.NumeroMesa FROM TblReservarMesas r " +
                  "INNER JOIN TblMesas m ON r.IdMesa = m.IdMesa " +
                  "WHERE r.Nombre LIKE ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + C + "%");
        }

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            idReservaActual = rs.getInt("IdReserva");
            txtNombre.setText(rs.getString("Nombre"));
            txtTel.setText(rs.getString("Telefono"));
            txtFecha.setText(rs.getString("Fecha"));
            txtHora.setText(rs.getString("Hora"));
            txtPer.setText(rs.getString("Personas"));
            txtMesa.setText(rs.getString("NumeroMesa")); 
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ninguna reserva.");
        }

        rs.close();
        ps.close();
        conexion.cerrarConexion();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar reserva: " + e.getMessage());
        e.printStackTrace();
    }
}
public void registrarAsistencia() {
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();
    try {
      
        String valor = txtNombre.getText().trim();
        int IdReserva = -1;
        if (valor.matches("\\d+")) { 
            IdReserva = Integer.parseInt(valor);  
        } else {  
            IdReserva = obtenerIdPorNombre(valor, con);
            if (IdReserva == -1) {
                JOptionPane.showMessageDialog(null, "No se encontro ninguna reserva con el nombre: " + valor);
                return;  
            }
        }
        Boolean asistio = null;
        if (chkSi.isSelected() && !chkNo.isSelected()) {
            asistio = true;
        } else if (!chkSi.isSelected() && chkNo.isSelected()) {
            asistio = false;
        } else {
            JOptionPane.showMessageDialog(null, "Por favor selecciona solo una opción: Sí o No para asistencia.");
            return;
        }
        String horaLlegada = txtLlegada.getText().trim();
      
        if (asistio && (horaLlegada.isEmpty() || !horaLlegada.matches("\\d{2}:\\d{2}"))) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa una hora de llegada válida en formato HH:mm");
            return;
        }
    if (txtObser.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, escriba una observación.");
        return;
    }
        String observaciones = txtObser.getText();
        String asistioTexto = asistio ? "Sí" : "No";
    String sql = "INSERT INTO TblAsistenciasReservas (IdReserva, asistio, hora_llegada, observaciones) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, IdReserva);
        ps.setString(2, asistioTexto);
        ps.setString(3, horaLlegada); 
        ps.setString(4, observaciones);
        
        int filas = ps.executeUpdate();

        if (filas > 0) {
            JOptionPane.showMessageDialog(null, "Asistencia registrada correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la asistencia.");
        }
         txtBuscar.setText("");       
        txtNombre.setText("");    
        txtTel.setText("");       
        txtFecha.setText("");      
        txtHora.setText("");       
        txtPer.setText("");        
        txtMesa.setText("");      
        txtLlegada.setText("");  
        txtObser.setText("");      
        chkSi.setSelected(false);
        chkNo.setSelected(false);
        
        ps.close();
        conexion.cerrarConexion();
        ps.close();
        conexion.cerrarConexion();

    } catch (SQLException | NumberFormatException e) {
       
        JOptionPane.showMessageDialog(null, "Error al registrar asistencia: " + e.getMessage());
        e.printStackTrace();
    }
}

private int obtenerIdPorNombre(String nombre, Connection con) {
    int IdReserva = -1;

    try {
     
        String sql = "SELECT IdReserva FROM TblReservarMesas WHERE nombre = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            IdReserva = rs.getInt("IdReserva");  
        }

        rs.close();
        ps.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar la reserva por nombre: " + e.getMessage());
        e.printStackTrace();
    }
    return IdReserva; 
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        Pbuscar = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtMesa = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtPer = new javax.swing.JTextField();
        Pbuscar1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        chkSi = new javax.swing.JCheckBox();
        chkNo = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtLlegada = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtObser = new javax.swing.JTextField();
        Pregis = new javax.swing.JPanel();
        btnReAsis = new javax.swing.JButton();
        pCanc = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 240, 240));
        setPreferredSize(new java.awt.Dimension(1120, 684));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/mesa (1).png"))); // NOI18N
        jLabel1.setText("Registro de Asistencia a reservaciones");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setIconTextGap(10);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(630, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1120, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 136, 266, 39));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jButton2.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/interno (1).png"))); // NOI18N
        jButton2.setText("NOMBRE / ID RESERVA:");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.setIconTextGap(6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 136, -1, 39));

        Pbuscar.setBackground(new java.awt.Color(0, 102, 102));

        btnBuscar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/46833_search_user_icon.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setIconTextGap(10);
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarMouseExited(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PbuscarLayout = new javax.swing.GroupLayout(Pbuscar);
        Pbuscar.setLayout(PbuscarLayout);
        PbuscarLayout.setHorizontalGroup(
            PbuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PbuscarLayout.setVerticalGroup(
            PbuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        add(Pbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(531, 136, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("            NOMBRE:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 251, -1, 40));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));

        jLabel4.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("             FECHA:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(741, 251, -1, 40));

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));

        jLabel5.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("             HORA:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 337, -1, 40));

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));

        jLabel6.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("           PERSONAS:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 337, -1, 40));

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));

        jLabel7.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("               MESA:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(741, 337, -1, 40));

        jPanel10.setBackground(new java.awt.Color(0, 102, 102));

        jLabel3.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("           TELEFONO:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 251, 135, -1));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 251, 195, 40));

        txtMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMesaActionPerformed(evt);
            }
        });
        add(txtMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(882, 337, 199, 40));

        txtTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelActionPerformed(evt);
            }
        });
        add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 251, 196, 40));

        txtHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoraActionPerformed(evt);
            }
        });
        add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 337, 195, 40));

        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(882, 251, 199, 40));

        txtPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPerActionPerformed(evt);
            }
        });
        add(txtPer, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 337, 196, 40));

        Pbuscar1.setBackground(new java.awt.Color(0, 102, 102));
        Pbuscar1.setPreferredSize(new java.awt.Dimension(116, 40));

        jLabel8.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/1688838_audience_person_target_user_icon.png"))); // NOI18N
        jLabel8.setText("      ¿ASISTIO?");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel8.setIconTextGap(6);

        javax.swing.GroupLayout Pbuscar1Layout = new javax.swing.GroupLayout(Pbuscar1);
        Pbuscar1.setLayout(Pbuscar1Layout);
        Pbuscar1Layout.setHorizontalGroup(
            Pbuscar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        Pbuscar1Layout.setVerticalGroup(
            Pbuscar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(Pbuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 501, 148, 38));

        chkSi.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 18)); // NOI18N
        chkSi.setText("SI");
        add(chkSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 501, -1, -1));

        chkNo.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 18)); // NOI18N
        chkNo.setText("NO");
        add(chkNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 501, -1, -1));

        jPanel2.setBackground(new java.awt.Color(219, 112, 147));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 422, 1120, -1));

        jPanel12.setBackground(new java.awt.Color(0, 102, 102));

        jLabel9.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/5736354_clock_hour_schedule_stopwatch_time_icon.png"))); // NOI18N
        jLabel9.setText("   HORA LLEGADA");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 40));
        add(txtLlegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 148, 40));

        jPanel11.setBackground(new java.awt.Color(0, 102, 102));

        jLabel10.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/1494924_circle_content_edit_line_magnifying glass_icon.png"))); // NOI18N
        jLabel10.setText("    OBSERVACIONES:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel10.setIconTextGap(6);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 550, -1, 40));

        txtObser.setPreferredSize(new java.awt.Dimension(64, 20));
        txtObser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObserActionPerformed(evt);
            }
        });
        add(txtObser, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 550, 551, 40));

        Pregis.setBackground(new java.awt.Color(102, 153, 0));

        btnReAsis.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        btnReAsis.setForeground(new java.awt.Color(255, 255, 255));
        btnReAsis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/9004716_tick_check_accept_mark_icon.png"))); // NOI18N
        btnReAsis.setText(" MARCAR ASISTENCIA");
        btnReAsis.setBorder(null);
        btnReAsis.setBorderPainted(false);
        btnReAsis.setContentAreaFilled(false);
        btnReAsis.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReAsis.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnReAsis.setIconTextGap(6);
        btnReAsis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReAsisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReAsisMouseExited(evt);
            }
        });
        btnReAsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReAsisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PregisLayout = new javax.swing.GroupLayout(Pregis);
        Pregis.setLayout(PregisLayout);
        PregisLayout.setHorizontalGroup(
            PregisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PregisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReAsis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PregisLayout.setVerticalGroup(
            PregisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReAsis, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        add(Pregis, new org.netbeans.lib.awtextra.AbsoluteConstraints(739, 454, -1, -1));

        pCanc.setBackground(new java.awt.Color(153, 0, 0));

        btnCancelar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/9004715_cross_delete_remove_cancel_icon.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar.setIconTextGap(8);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pCancLayout = new javax.swing.GroupLayout(pCanc);
        pCanc.setLayout(pCancLayout);
        pCancLayout.setHorizontalGroup(
            pCancLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
            .addGroup(pCancLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pCancLayout.createSequentialGroup()
                    .addComponent(btnCancelar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pCancLayout.setVerticalGroup(
            pCancLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(pCancLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
        );

        add(pCanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(965, 454, -1, -1));

        jLabel11.setFont(new java.awt.Font("Roboto SemiCondensed Light", 1, 16)); // NOI18N
        jLabel11.setText("Confirme si el cliente asistio a la reserva llenando los datos:");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 454, -1, -1));

        jPanel13.setBackground(new java.awt.Color(219, 112, 147));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, -1));

        jPanel16.setBackground(new java.awt.Color(219, 112, 147));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 1120, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
    
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMesaActionPerformed
  
    }//GEN-LAST:event_txtMesaActionPerformed

    private void txtTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelActionPerformed

    private void txtHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoraActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void txtPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPerActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
     
    String criterio = txtBuscar.getText().trim();

    if (!criterio.isEmpty()) {
        buscarReserva(criterio);
    } else {
        JOptionPane.showMessageDialog(null, "Ingrese el ID o el nombre para buscar.");
    }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
        
        Pbuscar.setBackground(new Color(0,151,151));
       // btnBuscar.setBackground(Color.black);
    }//GEN-LAST:event_btnBuscarMouseEntered

    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
        Pbuscar.setBackground(new Color(0,102,102));
       // btnBuscar.setBackground(Color.black);
    }//GEN-LAST:event_btnBuscarMouseExited

    private void btnReAsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReAsisActionPerformed

        registrarAsistencia();
 
    }//GEN-LAST:event_btnReAsisActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    btnCancelar.addActionListener(e -> {
    txtBuscar.setText("");
    txtNombre.setText("");
    txtTel.setText("");
    txtFecha.setText("");
    txtHora.setText("");
    txtPer.setText("");
    txtMesa.setText("");
    chkSi.setSelected(false);
    chkNo.setSelected(false);
    txtLlegada.setText("");
    txtObser.setText("");
});

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnReAsisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReAsisMouseEntered
      
         Pregis.setBackground(new Color(145,215,0));
    }//GEN-LAST:event_btnReAsisMouseEntered

    private void btnReAsisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReAsisMouseExited
        Pregis.setBackground(new Color(102,153,0));
       
    }//GEN-LAST:event_btnReAsisMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        pCanc.setBackground(new Color(230,0,0));
    
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        pCanc.setBackground(new Color(153,0,0));
       
    }//GEN-LAST:event_btnCancelarMouseExited

    private void txtObserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pbuscar;
    private javax.swing.JPanel Pbuscar1;
    private javax.swing.JPanel Pregis;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnReAsis;
    private javax.swing.JCheckBox chkNo;
    private javax.swing.JCheckBox chkSi;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel pCanc;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtLlegada;
    private javax.swing.JTextField txtMesa;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObser;
    private javax.swing.JTextField txtPer;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
