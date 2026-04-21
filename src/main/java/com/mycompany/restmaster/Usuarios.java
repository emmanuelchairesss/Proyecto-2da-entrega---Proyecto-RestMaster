package com.mycompany.restmaster;


import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel
 */
public class Usuarios extends javax.swing.JFrame {

   int xmouse,ymouse;
   
    Conexion conexion = new Conexion();
    
  
    private void validarLogin() {
    String usuario = txtUsuario.getText();
    String contrasena = new String(txtContra.getPassword()); 

    String sql = "SELECT Nombre, Rol FROM tblLogin WHERE Nombre = ? AND Contrasena = ?";

    try (Connection con = conexion.establecerConexion();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, usuario);
        pst.setString(2, contrasena);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {

            String usuarioBD = rs.getString("Nombre");
            String rol = rs.getString("Rol"); 

            JOptionPane.showMessageDialog(this, 
                "¡Acceso correcto!\nRol: " + rol, 
                "Bienvenido", 
                JOptionPane.INFORMATION_MESSAGE);
            MenuPrincipalp menuPrincipal = new MenuPrincipalp(usuarioBD, rol);
            menuPrincipal.setVisible(true);

            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Error de conexión: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    public Usuarios() {
        initComponents();
       
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Panelprincipal = new javax.swing.JPanel();
        pfondowhite = new javax.swing.JLabel();
        pverdefondo = new javax.swing.JPanel();
        lblbienvenidpo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblusuario = new javax.swing.JLabel();
        lbliniciarsesion = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblcontra = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        Panelentrar = new javax.swing.JPanel();
        btnEntrar = new javax.swing.JButton();
        Pcerrar = new javax.swing.JPanel();
        pcerrar = new javax.swing.JPanel();
        lblcerrar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Roboto Condensed Light", 1, 36)); // NOI18N
        jLabel1.setText("X");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        Panelprincipal.setBackground(new java.awt.Color(255, 255, 255));

        pfondowhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/download (4).jpeg"))); // NOI18N

        pverdefondo.setBackground(new java.awt.Color(102, 153, 0));

        lblbienvenidpo.setBackground(new java.awt.Color(255, 255, 255));
        lblbienvenidpo.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 36)); // NOI18N
        lblbienvenidpo.setForeground(new java.awt.Color(255, 255, 255));
        lblbienvenidpo.setText("BIENVENIDO !");

        jLabel2.setBackground(new java.awt.Color(102, 153, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/download (17).jpeg"))); // NOI18N

        javax.swing.GroupLayout pverdefondoLayout = new javax.swing.GroupLayout(pverdefondo);
        pverdefondo.setLayout(pverdefondoLayout);
        pverdefondoLayout.setHorizontalGroup(
            pverdefondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pverdefondoLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblbienvenidpo, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(44, 44, 44))
        );
        pverdefondoLayout.setVerticalGroup(
            pverdefondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pverdefondoLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(lblbienvenidpo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(pverdefondoLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblusuario.setFont(new java.awt.Font("Roboto Condensed Black", 0, 18)); // NOI18N
        lblusuario.setText("USUARIO:");

        lbliniciarsesion.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        lbliniciarsesion.setText("INICIAR SESION");

        txtUsuario.setFont(new java.awt.Font("Roboto Condensed Light", 1, 14)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(153, 153, 153));
        txtUsuario.setToolTipText("Ingrese su Nombre de Usuario...");
        txtUsuario.setBorder(null);

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        lblcontra.setFont(new java.awt.Font("Roboto Condensed Black", 0, 18)); // NOI18N
        lblcontra.setText("CONTRASEÑA:");

        txtContra.setFont(new java.awt.Font("Roboto SemiCondensed Light", 0, 14)); // NOI18N
        txtContra.setToolTipText("Ingrese su Contraseña...");
        txtContra.setBorder(null);
        txtContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        Panelentrar.setBackground(new java.awt.Color(102, 153, 0));
        Panelentrar.setLayout(new java.awt.BorderLayout());

        btnEntrar.setBackground(new java.awt.Color(102, 153, 0));
        btnEntrar.setFont(new java.awt.Font("Roboto Condensed ExtraBold", 1, 24)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setText("INGRESAR");
        btnEntrar.setBorder(null);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarMouseExited(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        Panelentrar.add(btnEntrar, java.awt.BorderLayout.CENTER);

        Pcerrar.setBackground(new java.awt.Color(255, 255, 255));
        Pcerrar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PcerrarMouseDragged(evt);
            }
        });
        Pcerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PcerrarMousePressed(evt);
            }
        });

        pcerrar.setBackground(new java.awt.Color(255, 255, 255));

        lblcerrar.setFont(new java.awt.Font("Roboto Condensed Light", 1, 36)); // NOI18N
        lblcerrar.setText(" x");
        lblcerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblcerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblcerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblcerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblcerrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pcerrarLayout = new javax.swing.GroupLayout(pcerrar);
        pcerrar.setLayout(pcerrarLayout);
        pcerrarLayout.setHorizontalGroup(
            pcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pcerrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );
        pcerrarLayout.setVerticalGroup(
            pcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pcerrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblcerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PcerrarLayout = new javax.swing.GroupLayout(Pcerrar);
        Pcerrar.setLayout(PcerrarLayout);
        PcerrarLayout.setHorizontalGroup(
            PcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PcerrarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PcerrarLayout.setVerticalGroup(
            PcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PcerrarLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/avatar-de-usuario.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/6351930_eye_password_see_view_icon (1).png"))); // NOI18N

        javax.swing.GroupLayout PanelprincipalLayout = new javax.swing.GroupLayout(Panelprincipal);
        Panelprincipal.setLayout(PanelprincipalLayout);
        PanelprincipalLayout.setHorizontalGroup(
            PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelprincipalLayout.createSequentialGroup()
                .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelprincipalLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbliniciarsesion)
                            .addComponent(txtUsuario)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addComponent(txtContra))
                        .addGap(29, 29, 29)
                        .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addGroup(PanelprincipalLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(Panelentrar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pverdefondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelprincipalLayout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(Pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pfondowhite)))
        );
        PanelprincipalLayout.setVerticalGroup(
            PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelprincipalLayout.createSequentialGroup()
                .addComponent(Pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelprincipalLayout.createSequentialGroup()
                        .addComponent(pverdefondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(lbliniciarsesion)
                        .addGap(62, 62, 62)
                        .addComponent(lblusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(lblcontra, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelprincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelprincipalLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel4)))
                        .addGap(36, 36, 36)
                        .addComponent(Panelentrar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pfondowhite, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelprincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelprincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   private String usuario;
    private String contraseña;
    
   
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    
    public boolean validarCredenciales(String usuario, String contraseña) {
       
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña);
    }
    
    public boolean esBotonLoginHabilitado() {
        return (usuario != null && !usuario.isEmpty()) && (contraseña != null && !contraseña.isEmpty());
    }
    
    private void lblcerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblcerrarMouseExited
        pcerrar.setBackground(Color.white);
        lblcerrar.setBackground(Color.black);
    }//GEN-LAST:event_lblcerrarMouseExited

    private void lblcerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblcerrarMouseClicked
       System.exit(0);
    }//GEN-LAST:event_lblcerrarMouseClicked

    private void lblcerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblcerrarMouseEntered
     pcerrar.setBackground(Color.red);
         lblcerrar.setBackground(Color.white);
    }//GEN-LAST:event_lblcerrarMouseEntered

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed

       validarLogin();
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseExited
     
        Panelentrar.setBackground(new Color(102,153,0));
    }//GEN-LAST:event_btnEntrarMouseExited

    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
      
        Panelentrar.setBackground(new Color (145,215,0));
    }//GEN-LAST:event_btnEntrarMouseEntered

    private void PcerrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PcerrarMousePressed
        xmouse=evt.getX();
        ymouse=evt.getY();
    }//GEN-LAST:event_PcerrarMousePressed

    private void PcerrarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PcerrarMouseDragged
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-  xmouse,y- ymouse);

    }//GEN-LAST:event_PcerrarMouseDragged

    private void txtContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraActionPerformed

    private void btnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseClicked
      
 
    }//GEN-LAST:event_btnEntrarMouseClicked

  
    public static void main(String args[]) {
        Conexion objetoconexion=new Conexion();
        objetoconexion.establecerConexion();
        FlatLightLaf.setup();
    //    UIManager.put( "CheckBox.arc", 0 );
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panelentrar;
    private javax.swing.JPanel Panelprincipal;
    private javax.swing.JPanel Pcerrar;
    public javax.swing.JButton btnEntrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblbienvenidpo;
    private javax.swing.JLabel lblcerrar;
    private javax.swing.JLabel lblcontra;
    private javax.swing.JLabel lbliniciarsesion;
    private javax.swing.JLabel lblusuario;
    private javax.swing.JPanel pcerrar;
    private javax.swing.JLabel pfondowhite;
    private javax.swing.JPanel pverdefondo;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
