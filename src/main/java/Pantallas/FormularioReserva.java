/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Pantallas;

import com.mycompany.restmaster.Conexion;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser; 
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;

public class FormularioReserva extends javax.swing.JDialog {

    private boolean reservaExitosa = false;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public FormularioReserva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configurarComboHora();
        txtFecha.setText(sdf.format(new Date()));
        setLocationRelativeTo(parent);
        
         btnActualizar.setVisible(false); 
    }
    
 public void mostrarBtnActualizar() {
        btnActualizar.setVisible(true);
        btnConfirmar.setVisible(false);      
    }

   private void configurarComboHora() {
    cmbHora.removeAllItems();

    for (int i = 7; i <= 15; i++) { 
        cmbHora.addItem(String.format("%02d:00", i));
    }
}

   public void setMesaSeleccionada(String mesa) {
    int numeroMesa = Integer.parseInt(mesa.replaceAll("[^0-9]", ""));

    int capacidad = obtenerCapacidadPorMesa(numeroMesa);

    lblMesaSeleccionada.setText("Mesa " + numeroMesa + " - Capacidad: " + capacidad);

    spnPersonas.setValue(1);
    spnPersonas.setModel(new javax.swing.SpinnerNumberModel(1, 1, capacidad, 1));
}
    
  public int obtenerCapacidadPorMesa(int numeroMesa) {
    int capacidad = 0;

    try {
        Conexion conexion = new Conexion();
        String sql = "SELECT Capacidad FROM TblMesas WHERE NumeroMesa = ?";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroMesa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                capacidad = rs.getInt("Capacidad");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return capacidad;
}

    public boolean isReservaExitosa() {
        return reservaExitosa;
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getTelefono() {
        return txtTelefono.getText().trim();
    }

    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    private int idReserva;

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    public String getFecha() {
        return txtFecha.getText().trim();
    }

    public String getHora() {
        return (String) cmbHora.getSelectedItem();
    }

    public int getPersonas() {
        return (Integer) spnPersonas.getValue();
    }

    public boolean formularioValido() {
        if (getNombre().isEmpty() || getTelefono().isEmpty() || getFecha().isEmpty() || getHora() == null || getPersonas() <= 0) {
            return false;
        }
        if (!getTelefono().matches("\\d{10}")) {
            return false;
        }
        try {
            sdf.setLenient(false);
            sdf.parse(getFecha());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public String getMesaSeleccionada() {
        return lblMesaSeleccionada.getText();
    }

    
      public void cargarDatosReserva(int idReserva) {
        try {
            Conexion conexion = new Conexion();
            String sql = "SELECT Nombre, Telefono, Correo, Fecha, Hora, Personas FROM TblReservarMesas WHERE IdReserva = ?";

            try (Connection con = conexion.establecerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idReserva);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    txtNombre.setText(rs.getString("Nombre"));
                    txtTelefono.setText(rs.getString("Telefono"));
                    txtCorreo.setText(rs.getString("Correo"));

                    Date fecha = rs.getDate("Fecha");
                    txtFecha.setText(sdf.format(fecha));

                    Time hora = rs.getTime("Hora");
                    cmbHora.setSelectedItem(hora.toString().substring(0,5));

                    spnPersonas.setValue(rs.getInt("Personas"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
          private void cargarDatosReserva(ResultSet rs) throws SQLException {

        if (txtIdReserva != null) {
            txtIdReserva.setText(String.valueOf(rs.getInt("IdReserva")));
        }

        txtNombre.setText(rs.getString("Nombre"));
        txtTelefono.setText(rs.getString("Telefono"));
        txtCorreo.setText(rs.getString("Correo"));


        java.sql.Date fecha = rs.getDate("Fecha");
        txtFecha.setText(sdf.format(fecha));


        java.sql.Time hora = rs.getTime("Hora");
        cmbHora.setSelectedItem(hora.toString().substring(0, 5));

        spnPersonas.setValue(rs.getInt("Personas"));


        int idMesa = rs.getInt("IdMesa");
        lblMesaSeleccionada.setText("Mesa " + obtenerNumeroMesa(idMesa));
    }
          private int obtenerNumeroMesa(int idMesa) {
        int numero = 0;

        try {
            Conexion conexion = new Conexion();
            String sql = "SELECT NumeroMesa FROM TblMesas WHERE IdMesa = ?";

            try (Connection con = conexion.establecerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idMesa);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    numero = rs.getInt("NumeroMesa");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return numero;
    }

    public void buscarReserva(String criterio) {
        Conexion conexion = new Conexion();
        Connection con = conexion.establecerConexion();
        try {
            String sql;
            PreparedStatement ps;
            if (criterio.matches("\\d+")) {
                sql = "SELECT * FROM TblReservarMesas WHERE IdReserva = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(criterio));
            } else {
                sql = "SELECT * FROM TblReservarMesas WHERE nombre LIKE ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + criterio + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtIdReserva.setText(String.valueOf(rs.getInt("IdReserva")));
                txtNombre.setText(rs.getString("Nombre"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtCorreo.setText(rs.getString("Correo"));
                txtFecha.setText(sdf.format(rs.getDate("Fecha"))); 
                cmbHora.setSelectedItem(rs.getString("Hora")); 
                spnPersonas.setValue(rs.getInt("Personas"));    
                lblMesaSeleccionada.setText(rs.getString("Mesa"));
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
  
    private void buscarReservaPorId(int idReserva) {
    try {
        Conexion conexion = new Conexion();
        String sql = "SELECT * FROM TblReservarMesas WHERE IdReserva = ?";
        try (Connection con = conexion.establecerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cargarDatosReserva(rs); 
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ninguna reserva con ese ID.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al buscar la reserva por ID.");
    }
}

private void buscarReservaPorNombre(String nombre) {
    try {
        Conexion conexion = new Conexion();
        String sql = "SELECT * FROM TblReservarMesas WHERE Nombre = ?";
        try (Connection con = conexion.establecerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cargarDatosReserva(rs); 
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ninguna reserva con ese nombre.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al buscar la reserva por nombre.");
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
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        cmbHora = new javax.swing.JComboBox<>();
        spnPersonas = new javax.swing.JSpinner();
        PanelNombre3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        PanelNombre4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        PanelNombre5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        PanelNombre6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        PanelNombre7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        PanelNombre8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        PanelNombre9 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        PanelNombre10 = new javax.swing.JPanel();
        btnFecha = new javax.swing.JButton();
        PactBuscar = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        Pconfi = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        Pcancee = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        PactActualizar = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        lblMesaSeleccionada = new javax.swing.JLabel();
        txtIdReserva = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulario de Reserva de Mesas");
        setBackground(new java.awt.Color(255, 204, 204));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(432, 562));

        txtNombre.setToolTipText("Ingresar nombre del cliente");

        txtTelefono.setToolTipText("Ingresar número de telefono del cliente");
        txtTelefono.setPreferredSize(new java.awt.Dimension(64, 30));

        txtCorreo.setToolTipText("Ingresar correo electronico del cliente");
        txtCorreo.setPreferredSize(new java.awt.Dimension(64, 36));

        txtFecha.setToolTipText("Ingresar fecha: dd/mm/yyyy");
        txtFecha.setPreferredSize(new java.awt.Dimension(64, 36));
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        cmbHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbHora.setPreferredSize(new java.awt.Dimension(72, 36));
        cmbHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbHoraActionPerformed(evt);
            }
        });

        spnPersonas.setPreferredSize(new java.awt.Dimension(64, 36));

        PanelNombre3.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre3.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel11.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/285655_user_icon (1).png"))); // NOI18N
        jLabel11.setText("NOMBRE");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel11.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre3Layout = new javax.swing.GroupLayout(PanelNombre3);
        PanelNombre3.setLayout(PanelNombre3Layout);
        PanelNombre3Layout.setHorizontalGroup(
            PanelNombre3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNombre3Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(36, 36, 36))
        );
        PanelNombre3Layout.setVerticalGroup(
            PanelNombre3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelNombre4.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre4.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel12.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/8956786_call_telephone_phone_conversation_telephone call_icon.png"))); // NOI18N
        jLabel12.setText("TELEFONO");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel12.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre4Layout = new javax.swing.GroupLayout(PanelNombre4);
        PanelNombre4.setLayout(PanelNombre4Layout);
        PanelNombre4Layout.setHorizontalGroup(
            PanelNombre4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel12)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        PanelNombre4Layout.setVerticalGroup(
            PanelNombre4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelNombre5.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre5.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel13.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/7101527_gmail_email_mail_icon.png"))); // NOI18N
        jLabel13.setText("CORREO");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel13.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre5Layout = new javax.swing.GroupLayout(PanelNombre5);
        PanelNombre5.setLayout(PanelNombre5Layout);
        PanelNombre5Layout.setHorizontalGroup(
            PanelNombre5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre5Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel13)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PanelNombre5Layout.setVerticalGroup(
            PanelNombre5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelNombre6.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre6.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel14.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/4732024_add_calendar_date_event_month_icon.png"))); // NOI18N
        jLabel14.setText("FECHA");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel14.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre6Layout = new javax.swing.GroupLayout(PanelNombre6);
        PanelNombre6.setLayout(PanelNombre6Layout);
        PanelNombre6Layout.setHorizontalGroup(
            PanelNombre6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel14)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        PanelNombre6Layout.setVerticalGroup(
            PanelNombre6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelNombre7.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre7.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel15.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/3859125_app_hour_interface_time_timer_icon.png"))); // NOI18N
        jLabel15.setText("HORA");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel15.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre7Layout = new javax.swing.GroupLayout(PanelNombre7);
        PanelNombre7.setLayout(PanelNombre7Layout);
        PanelNombre7Layout.setHorizontalGroup(
            PanelNombre7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel15)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        PanelNombre7Layout.setVerticalGroup(
            PanelNombre7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelNombre8.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre8.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel16.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/5172568_add_contact_user_icon (1).png"))); // NOI18N
        jLabel16.setText("PERSONAS");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel16.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre8Layout = new javax.swing.GroupLayout(PanelNombre8);
        PanelNombre8.setLayout(PanelNombre8Layout);
        PanelNombre8Layout.setHorizontalGroup(
            PanelNombre8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre8Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel16)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        PanelNombre8Layout.setVerticalGroup(
            PanelNombre8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelNombre9.setBackground(new java.awt.Color(202, 101, 0));
        PanelNombre9.setPreferredSize(new java.awt.Dimension(165, 50));

        jLabel17.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/reserva (1).png"))); // NOI18N
        jLabel17.setText("MESA");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel17.setIconTextGap(8);

        javax.swing.GroupLayout PanelNombre9Layout = new javax.swing.GroupLayout(PanelNombre9);
        PanelNombre9.setLayout(PanelNombre9Layout);
        PanelNombre9Layout.setHorizontalGroup(
            PanelNombre9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre9Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel17)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        PanelNombre9Layout.setVerticalGroup(
            PanelNombre9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNombre9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblTitulo.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 0, 0));
        lblTitulo.setText("Reserva de Mesas");

        PanelNombre10.setBackground(new java.awt.Color(255, 204, 0));
        PanelNombre10.setPreferredSize(new java.awt.Dimension(165, 50));

        btnFecha.setBackground(new java.awt.Color(255, 204, 0));
        btnFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/9004741_calendar_date_schedule_event_icon (1).png"))); // NOI18N
        btnFecha.setBorder(null);
        btnFecha.setBorderPainted(false);
        btnFecha.setContentAreaFilled(false);
        btnFecha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFecha.setDefaultCapable(false);
        btnFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelNombre10Layout = new javax.swing.GroupLayout(PanelNombre10);
        PanelNombre10.setLayout(PanelNombre10Layout);
        PanelNombre10Layout.setHorizontalGroup(
            PanelNombre10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );
        PanelNombre10Layout.setVerticalGroup(
            PanelNombre10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        PactBuscar.setBackground(new java.awt.Color(0, 0, 102));
        PactBuscar.setPreferredSize(new java.awt.Dimension(165, 50));

        btnBuscar.setBackground(new java.awt.Color(153, 51, 0));
        btnBuscar.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/BuscarCliente.png"))); // NOI18N
        btnBuscar.setText("Buscar Cliente");
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnBuscar.setIconTextGap(6);
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

        javax.swing.GroupLayout PactBuscarLayout = new javax.swing.GroupLayout(PactBuscar);
        PactBuscar.setLayout(PactBuscarLayout);
        PactBuscarLayout.setHorizontalGroup(
            PactBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
            .addGroup(PactBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PactBuscarLayout.createSequentialGroup()
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        PactBuscarLayout.setVerticalGroup(
            PactBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
            .addGroup(PactBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
        );

        Pconfi.setBackground(new java.awt.Color(102, 153, 0));
        Pconfi.setPreferredSize(new java.awt.Dimension(165, 50));

        btnConfirmar.setBackground(new java.awt.Color(102, 153, 0));
        btnConfirmar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 14)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/9004716_tick_check_accept_mark_icon (1).png"))); // NOI18N
        btnConfirmar.setText("Confirmar Reserva");
        btnConfirmar.setBorder(null);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnConfirmar.setIconTextGap(6);
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseExited(evt);
            }
        });
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PconfiLayout = new javax.swing.GroupLayout(Pconfi);
        Pconfi.setLayout(PconfiLayout);
        PconfiLayout.setHorizontalGroup(
            PconfiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PconfiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PconfiLayout.setVerticalGroup(
            PconfiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        Pcancee.setBackground(new java.awt.Color(102, 0, 0));
        Pcancee.setPreferredSize(new java.awt.Dimension(165, 50));

        btnCancelar.setBackground(new java.awt.Color(102, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/back.png"))); // NOI18N
        btnCancelar.setText("Regresar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar.setIconTextGap(6);
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

        javax.swing.GroupLayout PcanceeLayout = new javax.swing.GroupLayout(Pcancee);
        Pcancee.setLayout(PcanceeLayout);
        PcanceeLayout.setHorizontalGroup(
            PcanceeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PcanceeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PcanceeLayout.setVerticalGroup(
            PcanceeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        PactActualizar.setBackground(new java.awt.Color(102, 0, 0));

        btnActualizar.setBackground(new java.awt.Color(153, 51, 0));
        btnActualizar.setFont(new java.awt.Font("Roboto SemiCondensed Medium", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/actualizar-flecha.png"))); // NOI18N
        btnActualizar.setText("Actualizar Datos");
        btnActualizar.setBorder(null);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnActualizar.setIconTextGap(6);
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarMouseExited(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PactActualizarLayout = new javax.swing.GroupLayout(PactActualizar);
        PactActualizar.setLayout(PactActualizarLayout);
        PactActualizarLayout.setHorizontalGroup(
            PactActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PactActualizarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PactActualizarLayout.setVerticalGroup(
            PactActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PanelNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PanelNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PanelNombre7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PanelNombre8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTitulo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelNombre6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelNombre9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PanelNombre10, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                            .addComponent(spnPersonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbHora, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMesaSeleccionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PactBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                    .addComponent(PactActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Pconfi, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(Pcancee, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblTitulo)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PanelNombre3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(PactBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(PanelNombre4, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(PactActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelNombre6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelNombre10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelNombre7, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(cmbHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelNombre8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMesaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pconfi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PanelNombre9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Pcancee, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 570));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        reservaExitosa = false;
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
                                                
    reservaExitosa = true;

    String nombreCliente = txtNombre.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String fechaTexto = txtFecha.getText().trim();
    String horaSeleccionada = (String) cmbHora.getSelectedItem();
    String mesaTexto = lblMesaSeleccionada.getText().trim();
    String correo = txtCorreo.getText().trim();

    if (nombreCliente.isEmpty() || telefono.isEmpty() || fechaTexto.isEmpty() || horaSeleccionada == null || mesaTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try {
        int numeroMesa;

        try {
         numeroMesa = Integer.parseInt(mesaTexto.split(" ")[1]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de mesa inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idMesa = obtenerIdMesa(numeroMesa);

        if (idMesa == -1) {
            JOptionPane.showMessageDialog(this, "Mesa no encontrada en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.Date utilDate = sdf.parse(fechaTexto);
        java.sql.Date fechaSQL = new java.sql.Date(utilDate.getTime());

        Conexion conexion = new Conexion();
        String sql = "INSERT INTO dbo.TblReservarMesas (Nombre, Telefono, Correo, Fecha, Hora, Personas, IdMesa, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreCliente);
            ps.setString(2, telefono);
            ps.setString(3, correo.isEmpty() ? null : correo);
            ps.setDate(4, fechaSQL);
            ps.setTime(5, java.sql.Time.valueOf(horaSeleccionada + ":00"));
            ps.setInt(6, (Integer) spnPersonas.getValue());
            ps.setInt(7, idMesa); 
            ps.setString(8, "reservada");

            ps.executeUpdate();
            String sqlMesa = "UPDATE TblMesas SET Estado = 'reservada' WHERE IdMesa = ?";
            PreparedStatement psMesa = con.prepareStatement(sqlMesa);
            psMesa.setInt(1, idMesa);
            psMesa.executeUpdate();

            JOptionPane.showMessageDialog(this, "Reserva confirmada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al reservar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnConfirmarActionPerformed
public int obtenerIdMesa(int numeroMesa) {
    int idMesa = -1;

    try {
        Conexion conexion = new Conexion();
        String sql = "SELECT IdMesa FROM TblMesas WHERE NumeroMesa = ?";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroMesa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idMesa = rs.getInt("IdMesa");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return idMesa;
}
    private void btnFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFechaActionPerformed
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        int option = JOptionPane.showOptionDialog(this, dateChooser, "Seleccionar Fecha",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION && dateChooser.getDate() != null) {
            Date selectedDate = dateChooser.getDate();
            if (!selectedDate.before(new Date())) {
                txtFecha.setText(sdf.format(selectedDate));
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnFechaActionPerformed

    private void cmbHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbHoraActionPerformed

    private void btnConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseEntered
       Pconfi.setBackground(new Color(145,215,0));
    }//GEN-LAST:event_btnConfirmarMouseEntered

    private void btnConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseExited
      Pconfi.setBackground(new Color(102,153,0));
    }//GEN-LAST:event_btnConfirmarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
         Pcancee.setBackground(new Color(230,0,0));
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
 Pcancee.setBackground(new Color(153,0,0));        

    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        PactActualizar.setBackground(new Color(215,0,0));
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseExited
         PactActualizar.setBackground(new Color(102,0,0));
    }//GEN-LAST:event_btnActualizarMouseExited

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
     String idTexto = txtIdReserva.getText().trim();
    String nombreTexto = txtNombre.getText().trim();

    if (!idTexto.isEmpty()) {
        try {
            int id = Integer.parseInt(idTexto);
            buscarReservaPorId(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un numero valido.");
        }
    } else if (!nombreTexto.isEmpty()) {
        buscarReservaPorNombre(nombreTexto);
    } else {
        JOptionPane.showMessageDialog(null, "Ingrese el nombre o el ID de la reserva para buscar.");
    } 
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (!formularioValido()) {
        JOptionPane.showMessageDialog(this, "Complete correctamente el formulario.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    int idReserva = getIdReserva();
    if (idReserva <= 0) {
        JOptionPane.showMessageDialog(this, "ID de reserva no valido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try {
        Conexion conexion = new Conexion();
        String sql = "UPDATE dbo.TblReservarMesas SET Nombre = ?, Telefono = ?, Correo = ?, Fecha = ?, Hora = ?, Personas = ? WHERE IdReserva = ?";

        try (Connection con = conexion.establecerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, getNombre()); 
            ps.setString(2, getTelefono()); 
            ps.setString(3, getCorreo().isEmpty() ? null : getCorreo()); 

            Date fechaUtil = sdf.parse(getFecha());
            ps.setDate(4, new java.sql.Date(fechaUtil.getTime()));  

            ps.setString(5, getHora());  
            ps.setInt(6, getPersonas());  
            ps.setInt(7, idReserva);  
           int filasActualizadas = ps.executeUpdate();

        if (filasActualizadas > 0) {

            String sqlMesa = "SELECT IdMesa FROM TblReservarMesas WHERE IdReserva = ?";

            try (PreparedStatement psMesa = con.prepareStatement(sqlMesa)) {
                psMesa.setInt(1, idReserva);
                ResultSet rs = psMesa.executeQuery();

                if (rs.next()) {
                    int idMesa = rs.getInt("IdMesa");

                    String updateMesa = "UPDATE TblMesas SET Estado = 'reservada' WHERE IdMesa = ?";

                    try (PreparedStatement psUpdateMesa = con.prepareStatement(updateMesa)) {
                        psUpdateMesa.setInt(1, idMesa);
                        psUpdateMesa.executeUpdate();
                    }
                }
            }
    JOptionPane.showMessageDialog(this, "Reserva actualizada exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
    this.dispose();

} else {
    JOptionPane.showMessageDialog(this, "No se encontro la reserva para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
}
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
       PactBuscar.setBackground(new Color(51, 51, 153));
    }//GEN-LAST:event_btnBuscarMouseEntered

    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
       PactBuscar.setBackground(new Color(0, 0, 102));
    }//GEN-LAST:event_btnBuscarMouseExited

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

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
            java.util.logging.Logger.getLogger(FormularioReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormularioReserva dialog = new FormularioReserva(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PactActualizar;
    private javax.swing.JPanel PactBuscar;
    private javax.swing.JPanel PanelNombre10;
    private javax.swing.JPanel PanelNombre3;
    private javax.swing.JPanel PanelNombre4;
    private javax.swing.JPanel PanelNombre5;
    private javax.swing.JPanel PanelNombre6;
    private javax.swing.JPanel PanelNombre7;
    private javax.swing.JPanel PanelNombre8;
    private javax.swing.JPanel PanelNombre9;
    private javax.swing.JPanel Pcancee;
    private javax.swing.JPanel Pconfi;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnFecha;
    private javax.swing.JComboBox<String> cmbHora;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblMesaSeleccionada;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JSpinner spnPersonas;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdReserva;
    public javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
