package com.mycompany.restmaster;


import Pantallas.AsistenciaReserva;
import Pantallas.Inventario;
import Pantallas.ReservarMesas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.ImageIcon;
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
public class MenuPrincipalp extends javax.swing.JFrame {
public ColoresPaneles colb;
private String usuarioLogeado;
private String rol;

    /**
     * Creates new form MenuPrincipalp
     */
    public MenuPrincipalp() {
        initComponents();
        this.setLocationRelativeTo(null);
        colb = new ColoresPaneles(this);
       Date();
        
    }
    public MenuPrincipalp(String usuario, String rol) {
        this();
        this.usuarioLogeado = usuario;
        this.rol = rol;

        System.out.println("Usuario: " + usuario);
        System.out.println("Rol: " + rol);
        if (rol.equalsIgnoreCase("Gerente")) {
            btnReservarMesas.setEnabled(false);
        } else if (rol.equalsIgnoreCase("Recepcionista")) {
            btnInventario.setEnabled(false);
        }

        configurarImagenYRol(rol);
    }
    private void configurarImagenYRol(String rol) {
        if (rol.equalsIgnoreCase("Gerente")) {
            lblUser.setIcon(new ImageIcon("src/main/resources/Imagen/GG.png"));
            lblLRol.setText("GERENTE");
        } else if (rol.equalsIgnoreCase("Recepcionista")) {
            lblUser.setIcon(new ImageIcon("src/main/resources/Imagen/rr.png"));
            lblLRol.setText("RECEPCIONISTA");
        }
    
    lblUser.revalidate();
    lblUser.repaint();
    lblLRol.revalidate();
    lblLRol.repaint();
    
}


public void Date(){
    
   LocalDate now=LocalDate.now();
   Locale spanishLocale=new Locale("es","ES");
   fecha.setText(now.format(DateTimeFormatter.ofPattern("'Hoy es' EEEE dd 'de' MMMM 'de' yyyy",spanishLocale)));
   
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipal = new javax.swing.JPanel();
        PanelMenu = new javax.swing.JPanel();
        lblRestMaster = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Pmesas = new javax.swing.JPanel();
        btnReservarMesas = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblLRol = new javax.swing.JLabel();
        PanelCerrarSesion = new javax.swing.JPanel();
        btnCerrarSesion = new javax.swing.JButton();
        PanelInventario = new javax.swing.JPanel();
        btnInventario = new javax.swing.JButton();
        PanelAsistenciaReserva = new javax.swing.JPanel();
        btnAsisReserva = new javax.swing.JButton();
        PanelFondo = new javax.swing.JPanel();
        Pcerrar = new javax.swing.JPanel();
        lblCerrar = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelTodos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        PanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        PanelMenu.setBackground(new java.awt.Color(102, 153, 0));

        lblRestMaster.setFont(new java.awt.Font("SimSun-ExtB", 1, 36)); // NOI18N
        lblRestMaster.setForeground(new java.awt.Color(255, 255, 255));
        lblRestMaster.setText("Rest Master");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        Pmesas.setBackground(new java.awt.Color(102, 153, 0));

        btnReservarMesas.setBackground(new java.awt.Color(120, 170, 0));
        btnReservarMesas.setFont(new java.awt.Font("Roboto Condensed Black", 1, 18)); // NOI18N
        btnReservarMesas.setForeground(new java.awt.Color(255, 255, 255));
        btnReservarMesas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/mesa (1).png"))); // NOI18N
        btnReservarMesas.setText("Reservar Mesas");
        btnReservarMesas.setBorder(null);
        btnReservarMesas.setBorderPainted(false);
        btnReservarMesas.setContentAreaFilled(false);
        btnReservarMesas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservarMesas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReservarMesas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReservarMesas.setIconTextGap(20);
        btnReservarMesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReservarMesasMouseClicked(evt);
            }
        });
        btnReservarMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarMesasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PmesasLayout = new javax.swing.GroupLayout(Pmesas);
        Pmesas.setLayout(PmesasLayout);
        PmesasLayout.setHorizontalGroup(
            PmesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PmesasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReservarMesas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PmesasLayout.setVerticalGroup(
            PmesasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PmesasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnReservarMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBackground(new java.awt.Color(102, 153, 0));

        lblLRol.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        lblLRol.setForeground(new java.awt.Color(255, 255, 255));
        lblLRol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLRol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLRol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        PanelCerrarSesion.setBackground(new java.awt.Color(102, 153, 0));
        PanelCerrarSesion.setPreferredSize(new java.awt.Dimension(217, 62));

        btnCerrarSesion.setBackground(new java.awt.Color(102, 153, 0));
        btnCerrarSesion.setFont(new java.awt.Font("Roboto Condensed Black", 1, 18)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/cerrar-sesion-de-usuario.png"))); // NOI18N
        btnCerrarSesion.setText("Cerrar Sesion");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setContentAreaFilled(false);
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCerrarSesion.setIconTextGap(20);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelCerrarSesionLayout = new javax.swing.GroupLayout(PanelCerrarSesion);
        PanelCerrarSesion.setLayout(PanelCerrarSesionLayout);
        PanelCerrarSesionLayout.setHorizontalGroup(
            PanelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCerrarSesionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelCerrarSesionLayout.setVerticalGroup(
            PanelCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        PanelInventario.setBackground(new java.awt.Color(102, 153, 0));

        btnInventario.setBackground(new java.awt.Color(102, 153, 0));
        btnInventario.setFont(new java.awt.Font("Roboto Condensed Black", 1, 18)); // NOI18N
        btnInventario.setForeground(new java.awt.Color(255, 255, 255));
        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/schedule_8646480.png"))); // NOI18N
        btnInventario.setText("Inventario");
        btnInventario.setBorder(null);
        btnInventario.setBorderPainted(false);
        btnInventario.setContentAreaFilled(false);
        btnInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInventario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnInventario.setIconTextGap(20);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelInventarioLayout = new javax.swing.GroupLayout(PanelInventario);
        PanelInventario.setLayout(PanelInventarioLayout);
        PanelInventarioLayout.setHorizontalGroup(
            PanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelInventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelInventarioLayout.setVerticalGroup(
            PanelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnInventario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        PanelAsistenciaReserva.setBackground(new java.awt.Color(102, 153, 0));
        PanelAsistenciaReserva.setPreferredSize(new java.awt.Dimension(217, 62));

        btnAsisReserva.setBackground(new java.awt.Color(102, 153, 0));
        btnAsisReserva.setFont(new java.awt.Font("Roboto Condensed Black", 1, 18)); // NOI18N
        btnAsisReserva.setForeground(new java.awt.Color(255, 255, 255));
        btnAsisReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/95908_image_photo_check_icon.png"))); // NOI18N
        btnAsisReserva.setText("Asistencia a Reserva");
        btnAsisReserva.setBorder(null);
        btnAsisReserva.setBorderPainted(false);
        btnAsisReserva.setContentAreaFilled(false);
        btnAsisReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsisReserva.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAsisReserva.setIconTextGap(20);
        btnAsisReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsisReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelAsistenciaReservaLayout = new javax.swing.GroupLayout(PanelAsistenciaReserva);
        PanelAsistenciaReserva.setLayout(PanelAsistenciaReservaLayout);
        PanelAsistenciaReservaLayout.setHorizontalGroup(
            PanelAsistenciaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAsistenciaReservaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAsisReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelAsistenciaReservaLayout.setVerticalGroup(
            PanelAsistenciaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAsisReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelMenuLayout = new javax.swing.GroupLayout(PanelMenu);
        PanelMenu.setLayout(PanelMenuLayout);
        PanelMenuLayout.setHorizontalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pmesas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRestMaster))
                .addGap(19, 19, 19))
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(PanelInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
            .addComponent(PanelAsistenciaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(lblRestMaster)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127)
                .addComponent(Pmesas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(PanelInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelAsistenciaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );

        PanelFondo.setBackground(new java.awt.Color(153, 204, 0));

        Pcerrar.setBackground(new java.awt.Color(153, 204, 0));
        Pcerrar.setForeground(new java.awt.Color(102, 153, 0));

        lblCerrar.setBackground(new java.awt.Color(153, 204, 0));
        lblCerrar.setFont(new java.awt.Font("Roboto Condensed Light", 1, 36)); // NOI18N
        lblCerrar.setText("  X");
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCerrarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PcerrarLayout = new javax.swing.GroupLayout(Pcerrar);
        Pcerrar.setLayout(PcerrarLayout);
        PcerrarLayout.setHorizontalGroup(
            PcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PcerrarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PcerrarLayout.setVerticalGroup(
            PcerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PcerrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        fecha.setFont(new java.awt.Font("Roboto SemiCondensed ExtraLight", 1, 24)); // NOI18N
        fecha.setForeground(new java.awt.Color(255, 255, 255));
        fecha.setText("Hoy es {dayname} {day} de {month} de {year}");

        jLabel2.setFont(new java.awt.Font("Roboto SemiCondensed ExtraLight", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bienvenido !");

        javax.swing.GroupLayout PanelFondoLayout = new javax.swing.GroupLayout(PanelFondo);
        PanelFondo.setLayout(PanelFondoLayout);
        PanelFondoLayout.setHorizontalGroup(
            PanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFondoLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(PanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelFondoLayout.setVerticalGroup(
            PanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFondoLayout.createSequentialGroup()
                .addGroup(PanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Pcerrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fecha)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        PanelTodos.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelTodosLayout = new javax.swing.GroupLayout(PanelTodos);
        PanelTodos.setLayout(PanelTodosLayout);
        PanelTodosLayout.setHorizontalGroup(
            PanelTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1119, Short.MAX_VALUE)
        );
        PanelTodosLayout.setVerticalGroup(
            PanelTodosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addComponent(PanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelPrincipalLayout.createSequentialGroup()
                        .addComponent(PanelTodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(PanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(PanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelPrincipalLayout.createSequentialGroup()
                .addComponent(PanelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelTodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReservarMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarMesasActionPerformed
      ReservarMesas p = new ReservarMesas();
    
    p.setSize(1120, 684);
    p.setLocation(0, 0);
    
    
    PanelTodos.removeAll();  
    PanelTodos.add(p, BorderLayout.CENTER);
    PanelTodos.revalidate();
    PanelTodos.repaint();

    }//GEN-LAST:event_btnReservarMesasActionPerformed

    private void lblCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseExited
         Pcerrar.setBackground(new Color(153,204,0));
        lblCerrar.setBackground(new Color(153,204,0));
    }//GEN-LAST:event_lblCerrarMouseExited

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
      System.exit(0);
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void lblCerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseEntered
        Pcerrar.setBackground(Color.red);
         lblCerrar.setBackground(Color.white);
    }//GEN-LAST:event_lblCerrarMouseEntered

    private void btnReservarMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservarMesasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReservarMesasMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
     
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); 
            new Usuarios().setVisible(true); 
        
    }

    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        Inventario p = new Inventario();

        p.setSize(1120, 684);
        p.setLocation(0, 0);

        PanelTodos.removeAll();
        PanelTodos.add(p, BorderLayout.CENTER);
        PanelTodos.revalidate();
        PanelTodos.repaint();
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnAsisReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsisReservaActionPerformed
        AsistenciaReserva p = new AsistenciaReserva();
    
   p.setSize(1120, 684);
    p.setLocation(0, 0);
    

    PanelTodos.removeAll();  
    PanelTodos.add(p, BorderLayout.CENTER); 
    PanelTodos.revalidate();
    PanelTodos.repaint();
    }//GEN-LAST:event_btnAsisReservaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(MenuPrincipalp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipalp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipalp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipalp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipalp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PanelAsistenciaReserva;
    public javax.swing.JPanel PanelCerrarSesion;
    private javax.swing.JPanel PanelFondo;
    public javax.swing.JPanel PanelInventario;
    private javax.swing.JPanel PanelMenu;
    private javax.swing.JPanel PanelPrincipal;
    private javax.swing.JPanel PanelTodos;
    private javax.swing.JPanel Pcerrar;
    public javax.swing.JPanel Pmesas;
    public javax.swing.JButton btnAsisReserva;
    public javax.swing.JButton btnCerrarSesion;
    public javax.swing.JButton btnInventario;
    public javax.swing.JButton btnReservarMesas;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblLRol;
    private javax.swing.JLabel lblRestMaster;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}
