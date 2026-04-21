/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pantallas;

import com.mycompany.restmaster.Conexion;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ReservarMesas extends javax.swing.JPanel {

    private HashMap<JButton, String> estadosMesas = new HashMap<>();
    private JButton[] btnMesas;
    private JButton mesaSeleccionada = null;


    private final Color VERDE = new Color(102,153,0);
    private final Color ROJO = new Color(153,76,76);
    private final Color AMARILLO = new Color(184,134,11);
    private final Color AZUL = new Color(0,102,153);

    public ReservarMesas() {
        initComponents();
        inicializarMesas();
        agregarEventosBotonesEstado();

        this.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0 && this.isShowing()) {
                cargarEstadoMesasDesdeBD();
            }
        });
    }

    public HashMap<JButton, String> getEstadosMesas() {
        return estadosMesas;
    }

    public JButton getMesaSeleccionada() {
        return mesaSeleccionada;
    }

    public JButton getBtnReservarMesa() {
        return btnReservarMesa;
    }

    private void inicializarMesas() {
        btnMesas = new JButton[]{btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6, btnMesa7,
            btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12, btnMesa13};
        
        JPopupMenu menu = new JPopupMenu();

        JMenuItem disponible = new JMenuItem("Disponible");
        JMenuItem ocupada = new JMenuItem("Ocupada");

        menu.add(disponible);
        menu.add(ocupada);

        disponible.addActionListener(e -> cambiarEstadoMesa("disponible", VERDE));
        ocupada.addActionListener(e -> cambiarEstadoMesa("ocupada", ROJO));

        for (JButton btnMesa : btnMesas) {
    estadosMesas.put(btnMesa, "disponible");

    btnMesa.setBackground(VERDE);
    btnMesa.setBorder(BorderFactory.createLineBorder(VERDE, 2));

    btnMesa.setFocusPainted(false);
    btnMesa.setContentAreaFilled(true);
    btnMesa.setOpaque(true);
    btnMesa.setBorderPainted(true);
    btnMesa.setMargin(new java.awt.Insets(0, 0, 0, 0));

    btnMesa.addActionListener(e -> seleccionarMesa(btnMesa));

    btnMesa.addMouseListener(new MouseAdapter() {
        @Override
            public void mousePressed(MouseEvent e) {

                mesaSeleccionada = btnMesa;

                if (e.getClickCount() == 2) {
                    cambiarEstadoMesa("ocupada", ROJO);
                }

                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}

    public void cargarEstadoMesasDesdeBD() {
        try {
            Conexion conexion = new Conexion();
            Connection con = conexion.establecerConexion();

            String sql = "SELECT IdMesa, Estado FROM TblReservarMesas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int idMesa = rs.getInt("IdMesa");
                String estado = rs.getString("Estado");

                String nombreMesa = "Mesa " + idMesa;

                for (JButton btn : btnMesas) {
                    if (btn.getText().equals(nombreMesa)) {

                        estadosMesas.put(btn, estado);

                        switch (estado) {
                            case "disponible":
                                btn.setBackground(VERDE);
                                btn.setBorder(BorderFactory.createLineBorder(VERDE, 2));
                                break;

                            case "ocupada":
                                btn.setBackground(ROJO);
                                btn.setBorder(BorderFactory.createLineBorder(ROJO, 2));
                                break;

                            case "reservada":
                                btn.setBackground(AMARILLO);
                                btn.setBorder(BorderFactory.createLineBorder(AMARILLO, 2));
                                break;
                        }
                    }
                }
            }

            conexion.cerrarConexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregarEventosBotonesEstado() {
        btnDisponible.addActionListener(e -> mostrarMesasPorEstado("disponible"));
        btnOcupada.addActionListener(e -> mostrarMesasPorEstado("ocupada"));
        btnReservada.addActionListener(e -> mostrarMesasPorEstado("reservada"));
        btnTodas.addActionListener(e -> mostrarTodasLasMesas());

        btnReservarMesa.setEnabled(false);
    }

    public void seleccionarMesa(JButton mesa) {

        for (JButton btn : btnMesas) {
            String estado = estadosMesas.get(btn);

            switch (estado) {
                case "disponible":
                    btn.setBorder(BorderFactory.createLineBorder(VERDE, 2));
                    break;
                case "ocupada":
                    btn.setBorder(BorderFactory.createLineBorder(ROJO, 2));
                    break;
                case "reservada":
                    btn.setBorder(BorderFactory.createLineBorder(AMARILLO, 2));
                    break;
            }
        }

        mesa.setBorder(BorderFactory.createLineBorder(AZUL, 3));

        mesaSeleccionada = mesa;

        String estado = estadosMesas.get(mesa);

        if (estado.equals("disponible")) {
            btnReservarMesa.setEnabled(true);
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(false);

        } else if (estado.equals("reservada")) {
            btnReservarMesa.setEnabled(false);
            btnEditar.setEnabled(true);
            btnCancelar.setEnabled(true);

        } else {
            btnReservarMesa.setEnabled(false);
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }

    public void cambiarEstadoMesa(String estado, Color color) {
        if (mesaSeleccionada != null) {

            estadosMesas.put(mesaSeleccionada, estado);
            mesaSeleccionada.setBackground(color);

            switch (estado) {
                case "disponible":
                    mesaSeleccionada.setBorder(BorderFactory.createLineBorder(VERDE, 2));
                    break;

                case "ocupada":
                    mesaSeleccionada.setBorder(BorderFactory.createLineBorder(ROJO, 2));
                    break;

                case "reservada":
                    mesaSeleccionada.setBorder(BorderFactory.createLineBorder(AMARILLO, 2));
                    break;
            }

            try {
                Conexion conexion = new Conexion();

                String sql = "UPDATE dbo.TblReservarMesas SET Estado = ? WHERE IdMesa = ?";
                PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql);

                ps.setString(1, estado);

                String textoMesa = mesaSeleccionada.getText();
                int numeroMesa = Integer.parseInt(textoMesa.replaceAll("[^0-9]", ""));

                ps.setInt(2, numeroMesa);

                ps.executeUpdate();
                conexion.cerrarConexion();

            } catch (Exception e) {
                e.printStackTrace();
            }

            mostrarTodasLasMesas();
        }
    }

    public void mostrarMesasPorEstado(String estadoFiltrado) {
        for (JButton mesa : btnMesas) {
            String estado = estadosMesas.get(mesa);
            mesa.setVisible(estado.equals(estadoFiltrado));
        }
    }

    public void mostrarTodasLasMesas() {
        for (JButton mesa : btnMesas) {
            mesa.setVisible(true);

            String estado = estadosMesas.get(mesa);

            switch (estado) {
                case "disponible":
                    mesa.setBackground(VERDE);
                    mesa.setBorder(BorderFactory.createLineBorder(VERDE, 2));
                    break;

                case "ocupada":
                    mesa.setBackground(ROJO);
                    mesa.setBorder(BorderFactory.createLineBorder(ROJO, 2));
                    break;

                case "reservada":
                    mesa.setBackground(AMARILLO);
                    mesa.setBorder(BorderFactory.createLineBorder(AMARILLO, 2));
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnMesa1 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        btnMesa3 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        btnMesa5 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        btnMesa2 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        btnMesa4 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        btnMesa6 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        btnMesa7 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        btnMesa9 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        btnMesa8 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        btnMesa10 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        btnMesa13 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        btnMesa12 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        btnMesa11 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        Pd = new javax.swing.JPanel();
        btnDisponible = new javax.swing.JButton();
        Pocu = new javax.swing.JPanel();
        btnOcupada = new javax.swing.JButton();
        Prese = new javax.swing.JPanel();
        btnReservada = new javax.swing.JButton();
        Ptodoas = new javax.swing.JPanel();
        btnTodas = new javax.swing.JButton();
        Preservar = new javax.swing.JPanel();
        btnReservarMesa = new javax.swing.JButton();
        Pcance = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        Pedi = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 454, -1, 230));

        jPanel3.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 219, -1, -1));

        jPanel4.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 424, -1, -1));

        jLabel1.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/cocina (1).png"))); // NOI18N
        jLabel1.setText("Cocina");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 73, -1, -1));

        jLabel2.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/49590_boxes_customers_inventory_products_icon.png"))); // NOI18N
        jLabel2.setText("Bodega");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 67, -1));

        jPanel5.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(827, 0, -1, -1));

        jPanel6.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(823, 450, -1, 240));

        jPanel8.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 454, -1, 230));

        jPanel9.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 454, -1, 230));

        jPanel10.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 454, -1, 230));

        jLabel4.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/caja-registradora.png"))); // NOI18N
        jLabel4.setText("Caja");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 511, 70, -1));

        jLabel5.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/2612540_girl_lady_user_woman_icon.png"))); // NOI18N
        jLabel5.setText("Baños M");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 450, 77, 80));

        jPanel12.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 0, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/2625517_avatar_lady_man_woman_icon.png"))); // NOI18N
        jLabel7.setText("Baños H");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 450, 77, -1));

        jPanel13.setBackground(new java.awt.Color(102, 153, 0));
        jPanel13.setOpaque(false);

        btnMesa1.setBackground(new java.awt.Color(102, 153, 0));
        btnMesa1.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa1.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa1.setText("Mesa 1");
        btnMesa1.setBorder(null);
        btnMesa1.setBorderPainted(false);
        btnMesa1.setContentAreaFilled(false);
        btnMesa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa1.setOpaque(true);
        btnMesa1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 20, -1, -1));

        jPanel14.setBackground(new java.awt.Color(102, 153, 0));
        jPanel14.setOpaque(false);
        jPanel14.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa3.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa3.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa3.setText("Mesa 3");
        btnMesa3.setBorderPainted(false);
        btnMesa3.setContentAreaFilled(false);
        btnMesa3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa3.setOpaque(true);
        btnMesa3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa3, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 170, -1, -1));

        jPanel15.setBackground(new java.awt.Color(102, 153, 0));
        jPanel15.setOpaque(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa5.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa5.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa5.setText("Mesa 5");
        btnMesa5.setBorderPainted(false);
        btnMesa5.setContentAreaFilled(false);
        btnMesa5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa5.setOpaque(true);
        btnMesa5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 333, -1, -1));

        jPanel16.setBackground(new java.awt.Color(102, 153, 0));
        jPanel16.setOpaque(false);
        jPanel16.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa2.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa2.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa2.setText("Mesa 2");
        btnMesa2.setBorderPainted(false);
        btnMesa2.setContentAreaFilled(false);
        btnMesa2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa2.setOpaque(true);
        btnMesa2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa2, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 20, -1, -1));

        jPanel17.setBackground(new java.awt.Color(102, 153, 0));
        jPanel17.setOpaque(false);
        jPanel17.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa4.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa4.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa4.setText("Mesa 4");
        btnMesa4.setBorderPainted(false);
        btnMesa4.setContentAreaFilled(false);
        btnMesa4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa4.setOpaque(true);
        btnMesa4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa4, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 168, -1, -1));

        jPanel18.setBackground(new java.awt.Color(102, 153, 0));
        jPanel18.setOpaque(false);
        jPanel18.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa6.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa6.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa6.setText("Mesa 6");
        btnMesa6.setBorderPainted(false);
        btnMesa6.setContentAreaFilled(false);
        btnMesa6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa6.setOpaque(true);
        btnMesa6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa6, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa6, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 333, -1, -1));

        jPanel19.setBackground(new java.awt.Color(102, 153, 0));
        jPanel19.setOpaque(false);
        jPanel19.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa7.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa7.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa7.setText("Mesa 7");
        btnMesa7.setBorderPainted(false);
        btnMesa7.setContentAreaFilled(false);
        btnMesa7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa7.setOpaque(true);
        btnMesa7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa7, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(434, 240, -1, -1));

        jPanel20.setBackground(new java.awt.Color(102, 153, 0));
        jPanel20.setOpaque(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa9.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa9.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa9.setText("Mesa 9");
        btnMesa9.setBorderPainted(false);
        btnMesa9.setContentAreaFilled(false);
        btnMesa9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa9.setOpaque(true);
        btnMesa9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 168, -1, -1));

        jPanel21.setBackground(new java.awt.Color(102, 153, 0));
        jPanel21.setOpaque(false);
        jPanel21.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa8.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa8.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa8.setText("Mesa 8");
        btnMesa8.setBorderPainted(false);
        btnMesa8.setContentAreaFilled(false);
        btnMesa8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa8.setOpaque(true);
        btnMesa8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa8, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 20, -1, -1));

        jPanel22.setBackground(new java.awt.Color(102, 153, 0));
        jPanel22.setOpaque(false);
        jPanel22.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa10.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa10.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa10.setText("Mesa 10");
        btnMesa10.setBorderPainted(false);
        btnMesa10.setContentAreaFilled(false);
        btnMesa10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa10.setOpaque(true);
        btnMesa10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa10, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(btnMesa10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(534, 333, -1, -1));

        jPanel23.setBackground(new java.awt.Color(102, 153, 0));
        jPanel23.setOpaque(false);
        jPanel23.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa13.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa13.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa13.setText("Mesa 13");
        btnMesa13.setBorderPainted(false);
        btnMesa13.setContentAreaFilled(false);
        btnMesa13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa13.setOpaque(true);
        btnMesa13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 333, -1, -1));

        jPanel24.setBackground(new java.awt.Color(102, 153, 0));
        jPanel24.setOpaque(false);
        jPanel24.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa12.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa12.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa12.setText("Mesa 12");
        btnMesa12.setBorderPainted(false);
        btnMesa12.setContentAreaFilled(false);
        btnMesa12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa12.setOpaque(true);
        btnMesa12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMesa12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesa12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 164, -1, -1));

        jPanel25.setBackground(new java.awt.Color(102, 153, 0));
        jPanel25.setOpaque(false);
        jPanel25.setPreferredSize(new java.awt.Dimension(86, 66));

        btnMesa11.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 12)); // NOI18N
        btnMesa11.setForeground(new java.awt.Color(255, 255, 255));
        btnMesa11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/MESAJA.png"))); // NOI18N
        btnMesa11.setText("Mesa 11");
        btnMesa11.setBorderPainted(false);
        btnMesa11.setContentAreaFilled(false);
        btnMesa11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMesa11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMesa11.setOpaque(true);
        btnMesa11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMesa11, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(btnMesa11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/puerta-de-salida.png"))); // NOI18N
        jLabel6.setText("Entrada/Salida");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 460, 100, -1));

        jPanel26.setBackground(new java.awt.Color(102, 51, 0));

        Pd.setBackground(new java.awt.Color(102, 51, 0));

        btnDisponible.setBackground(new java.awt.Color(102, 153, 0));
        btnDisponible.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnDisponible.setForeground(new java.awt.Color(255, 255, 255));
        btnDisponible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/disponible.png"))); // NOI18N
        btnDisponible.setText("Disponible");
        btnDisponible.setBorderPainted(false);
        btnDisponible.setContentAreaFilled(false);
        btnDisponible.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisponible.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDisponible.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDisponible.setIconTextGap(20);
        btnDisponible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisponibleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisponibleMouseExited(evt);
            }
        });
        btnDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PdLayout = new javax.swing.GroupLayout(Pd);
        Pd.setLayout(PdLayout);
        PdLayout.setHorizontalGroup(
            PdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnDisponible, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PdLayout.setVerticalGroup(
            PdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnDisponible, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        Pocu.setBackground(new java.awt.Color(102, 51, 0));

        btnOcupada.setBackground(new java.awt.Color(255, 0, 0));
        btnOcupada.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnOcupada.setForeground(new java.awt.Color(255, 255, 255));
        btnOcupada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/ocupado.png"))); // NOI18N
        btnOcupada.setText("Ocupada");
        btnOcupada.setBorderPainted(false);
        btnOcupada.setContentAreaFilled(false);
        btnOcupada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOcupada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnOcupada.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnOcupada.setIconTextGap(20);
        btnOcupada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOcupadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOcupadaMouseExited(evt);
            }
        });
        btnOcupada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcupadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PocuLayout = new javax.swing.GroupLayout(Pocu);
        Pocu.setLayout(PocuLayout);
        PocuLayout.setHorizontalGroup(
            PocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnOcupada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PocuLayout.setVerticalGroup(
            PocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PocuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnOcupada, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        Prese.setBackground(new java.awt.Color(102, 51, 0));

        btnReservada.setBackground(new java.awt.Color(255, 255, 0));
        btnReservada.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnReservada.setForeground(new java.awt.Color(255, 255, 255));
        btnReservada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/reservado (3).png"))); // NOI18N
        btnReservada.setText("Reservada");
        btnReservada.setBorderPainted(false);
        btnReservada.setContentAreaFilled(false);
        btnReservada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnReservada.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnReservada.setIconTextGap(21);
        btnReservada.setInheritsPopupMenu(true);
        btnReservada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReservadaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReservadaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PreseLayout = new javax.swing.GroupLayout(Prese);
        Prese.setLayout(PreseLayout);
        PreseLayout.setHorizontalGroup(
            PreseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PreseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnReservada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PreseLayout.setVerticalGroup(
            PreseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PreseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnReservada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, Short.MAX_VALUE))
        );

        Ptodoas.setBackground(new java.awt.Color(102, 51, 0));

        btnTodas.setBackground(new java.awt.Color(204, 255, 255));
        btnTodas.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnTodas.setForeground(new java.awt.Color(255, 255, 255));
        btnTodas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/mesa-restaurante (1).png"))); // NOI18N
        btnTodas.setText("Todas");
        btnTodas.setBorderPainted(false);
        btnTodas.setContentAreaFilled(false);
        btnTodas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTodas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnTodas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTodas.setIconTextGap(20);
        btnTodas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTodasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTodasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PtodoasLayout = new javax.swing.GroupLayout(Ptodoas);
        Ptodoas.setLayout(PtodoasLayout);
        PtodoasLayout.setHorizontalGroup(
            PtodoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PtodoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnTodas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PtodoasLayout.setVerticalGroup(
            PtodoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PtodoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnTodas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        Preservar.setBackground(new java.awt.Color(102, 51, 0));

        btnReservarMesa.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnReservarMesa.setForeground(new java.awt.Color(255, 255, 255));
        btnReservarMesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/reserva.png"))); // NOI18N
        btnReservarMesa.setText("Reservar esta Mesa");
        btnReservarMesa.setBorderPainted(false);
        btnReservarMesa.setContentAreaFilled(false);
        btnReservarMesa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservarMesa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnReservarMesa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnReservarMesa.setIconTextGap(20);
        btnReservarMesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReservarMesaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReservarMesaMouseExited(evt);
            }
        });
        btnReservarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarMesaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PreservarLayout = new javax.swing.GroupLayout(Preservar);
        Preservar.setLayout(PreservarLayout);
        PreservarLayout.setHorizontalGroup(
            PreservarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PreservarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnReservarMesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, Short.MAX_VALUE))
        );
        PreservarLayout.setVerticalGroup(
            PreservarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PreservarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnReservarMesa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        Pcance.setBackground(new java.awt.Color(102, 51, 0));

        btnCancelar.setBackground(new java.awt.Color(255, 153, 0));
        btnCancelar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/9004715_cross_delete_remove_cancel_icon (1).png"))); // NOI18N
        btnCancelar.setText("Cancelar Reserva");
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar.setIconTextGap(20);
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

        javax.swing.GroupLayout PcanceLayout = new javax.swing.GroupLayout(Pcance);
        Pcance.setLayout(PcanceLayout);
        PcanceLayout.setHorizontalGroup(
            PcanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PcanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PcanceLayout.setVerticalGroup(
            PcanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PcanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        Pedi.setBackground(new java.awt.Color(102, 51, 0));

        btnEditar.setBackground(new java.awt.Color(255, 153, 153));
        btnEditar.setFont(new java.awt.Font("Rockwell Condensed", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/6586142_draw_edit_pencil_write_writing_icon (1).png"))); // NOI18N
        btnEditar.setText("Editar Reserva");
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnEditar.setIconTextGap(20);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PediLayout = new javax.swing.GroupLayout(Pedi);
        Pedi.setLayout(PediLayout);
        PediLayout.setHorizontalGroup(
            PediLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
            .addGroup(PediLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
        );
        PediLayout.setVerticalGroup(
            PediLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(PediLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/mesa-restaurante.png"))); // NOI18N
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pocu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Prese, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Ptodoas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Preservar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pcance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pedi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addGap(30, 30, 30)
                .addComponent(Pd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Pocu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Prese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ptodoas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Preservar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(Pedi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Pcance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(869, 0, -1, 690));

        jPanel7.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 454, 106, -1));

        jPanel11.setBackground(new java.awt.Color(153, 102, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 217, 106, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnMesa7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa7ActionPerformed

    private void btnMesa12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa12ActionPerformed

    private void btnMesa9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa9ActionPerformed

    private void btnMesa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa2ActionPerformed

    private void btnMesa4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa4ActionPerformed

    private void btnMesa13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa13ActionPerformed

    private void btnMesa8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa8ActionPerformed

    private void btnMesa10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa10ActionPerformed

    private void btnDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDisponibleActionPerformed

    private void btnReservarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarMesaActionPerformed
                                             
    if (mesaSeleccionada != null && estadosMesas.get(mesaSeleccionada).equals("disponible")) {

        FormularioReserva formularioDialog = new FormularioReserva(null, true);
        formularioDialog.setMesaSeleccionada(mesaSeleccionada.getText());
        formularioDialog.setVisible(true);

        if (formularioDialog.isReservaExitosa()) {
            estadosMesas.put(mesaSeleccionada, "reservada");
            mesaSeleccionada.setBackground(new Color(184,134,11)); // 🟡

            seleccionarMesa(mesaSeleccionada);
        }
    }

    }//GEN-LAST:event_btnReservarMesaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
                                           
    if (mesaSeleccionada != null && estadosMesas.get(mesaSeleccionada).equals("reservada")) {

        try {
            String textoMesa = mesaSeleccionada.getText();
            int numeroMesa = Integer.parseInt(textoMesa.replaceAll("[^0-9]", ""));

            Conexion conexion = new Conexion();
            Connection con = conexion.establecerConexion();

            String sqlMesa = "SELECT IdMesa FROM TblMesas WHERE NumeroMesa = ?";
            PreparedStatement psMesa = con.prepareStatement(sqlMesa);
            psMesa.setInt(1, numeroMesa);
            ResultSet rsMesa = psMesa.executeQuery();

            int idMesa;

            if (rsMesa.next()) {
                idMesa = rsMesa.getInt("IdMesa");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sqlReserva = "SELECT IdReserva FROM TblReservarMesas WHERE IdMesa = ? AND Estado = 'reservada'";
            PreparedStatement psReserva = con.prepareStatement(sqlReserva);
            psReserva.setInt(1, idMesa);
            ResultSet rsReserva = psReserva.executeQuery();

            if (rsReserva.next()) {
                int idReserva = rsReserva.getInt("IdReserva");

                FormularioReserva formularioDialog = new FormularioReserva(null, true);
                formularioDialog.setMesaSeleccionada(textoMesa);
                formularioDialog.setIdReserva(idReserva);
                formularioDialog.cargarDatosReserva(idReserva);

                formularioDialog.mostrarBtnActualizar();
                formularioDialog.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rsMesa.close();
            psMesa.close();
            rsReserva.close();
            psReserva.close();
            conexion.cerrarConexion();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una mesa reservada para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }


    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
                                          
    if (mesaSeleccionada != null && estadosMesas.get(mesaSeleccionada).equals("reservada")) {

        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que desea cancelar la reserva?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {

                String textoMesa = mesaSeleccionada.getText();
                int numeroMesa = Integer.parseInt(textoMesa.replaceAll("[^0-9]", ""));

                Conexion conexion = new Conexion();
                Connection con = conexion.establecerConexion();

                String sqlMesa = "SELECT IdMesa FROM TblMesas WHERE NumeroMesa = ?";
                PreparedStatement psMesa = con.prepareStatement(sqlMesa);
                psMesa.setInt(1, numeroMesa);
                ResultSet rsMesa = psMesa.executeQuery();

                if (!rsMesa.next()) {
                    JOptionPane.showMessageDialog(this, "No se encontró la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idMesa = rsMesa.getInt("IdMesa");

                String sqlReserva = "SELECT IdReserva FROM TblReservarMesas WHERE IdMesa = ? AND Estado = 'reservada'";
                PreparedStatement psReserva = con.prepareStatement(sqlReserva);
                psReserva.setInt(1, idMesa);
                ResultSet rsReserva = psReserva.executeQuery();

                if (!rsReserva.next()) {
                    JOptionPane.showMessageDialog(this, "No se encontró la reserva.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idReserva = rsReserva.getInt("IdReserva");

                String deleteSql = "DELETE FROM TblReservarMesas WHERE IdReserva = ?";
                PreparedStatement psDelete = con.prepareStatement(deleteSql);
                psDelete.setInt(1, idReserva);
                psDelete.executeUpdate();

                String updateMesa = "UPDATE TblMesas SET Estado = 'disponible' WHERE IdMesa = ?";
                PreparedStatement psUpdate = con.prepareStatement(updateMesa);
                psUpdate.setInt(1, idMesa);
                psUpdate.executeUpdate();

                estadosMesas.put(mesaSeleccionada, "disponible");
                mesaSeleccionada.setBackground(new Color(102,153,0)); 

                seleccionarMesa(mesaSeleccionada);

                JOptionPane.showMessageDialog(this, "Reserva cancelada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                rsMesa.close();
                psMesa.close();
                rsReserva.close();
                psReserva.close();
                psDelete.close();
                psUpdate.close();
                conexion.cerrarConexion();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cancelar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una mesa reservada para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
          Pedi.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
         Pedi.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
         Pcance.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        Pcance.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnDisponibleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisponibleMouseEntered
      Pd.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnDisponibleMouseEntered

    private void btnDisponibleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisponibleMouseExited
        Pd.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnDisponibleMouseExited

    private void btnOcupadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOcupadaMouseEntered
        Pocu.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnOcupadaMouseEntered

    private void btnOcupadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOcupadaMouseExited
         Pocu.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnOcupadaMouseExited

    private void btnReservadaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservadaMouseEntered
         Prese.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnReservadaMouseEntered

    private void btnReservadaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservadaMouseExited
       Prese.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnReservadaMouseExited

    private void btnTodasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTodasMouseEntered
         Ptodoas.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnTodasMouseEntered

    private void btnTodasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTodasMouseExited
         Ptodoas.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnTodasMouseExited

    private void btnReservarMesaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservarMesaMouseEntered
       Preservar.setBackground(new Color(206,103,0));
    }//GEN-LAST:event_btnReservarMesaMouseEntered

    private void btnReservarMesaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservarMesaMouseExited
        Preservar.setBackground(new Color(102,51,0));
    }//GEN-LAST:event_btnReservarMesaMouseExited

    private void btnMesa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMesa1ActionPerformed

    private void btnOcupadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcupadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOcupadaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pcance;
    private javax.swing.JPanel Pd;
    private javax.swing.JPanel Pedi;
    private javax.swing.JPanel Pocu;
    private javax.swing.JPanel Prese;
    private javax.swing.JPanel Preservar;
    private javax.swing.JPanel Ptodoas;
    private javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnDisponible;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnMesa1;
    private javax.swing.JButton btnMesa10;
    private javax.swing.JButton btnMesa11;
    private javax.swing.JButton btnMesa12;
    private javax.swing.JButton btnMesa13;
    private javax.swing.JButton btnMesa2;
    private javax.swing.JButton btnMesa3;
    private javax.swing.JButton btnMesa4;
    private javax.swing.JButton btnMesa5;
    private javax.swing.JButton btnMesa6;
    private javax.swing.JButton btnMesa7;
    private javax.swing.JButton btnMesa8;
    private javax.swing.JButton btnMesa9;
    private javax.swing.JButton btnOcupada;
    private javax.swing.JButton btnReservada;
    private javax.swing.JButton btnReservarMesa;
    private javax.swing.JButton btnTodas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
