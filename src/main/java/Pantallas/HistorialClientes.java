package Pantallas;

import com.mycompany.restmaster.HistorialDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author valen
 */
    public class HistorialClientes extends javax.swing.JPanel {

        /**
         * Creates new form VerHistorialClientes
         */

        private HistorialDAO historialDAO;
        private DefaultTableModel modeloTabla;

        public HistorialClientes() {
            initComponents();

            txtBuscar.setEnabled(false);

            historialDAO = new HistorialDAO();

            modeloTabla = (DefaultTableModel) tblHistorial.getModel();

            historialDAO.obtenerListadoHistorial(modeloTabla);

            tblHistorial.removeColumn(tblHistorial.getColumn("ID"));
            tblHistorial.removeColumn(tblHistorial.getColumn("Estado"));

            tblHistorial.setRowHeight(25);
            
            tblHistorial.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {

                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                   
                    if (isSelected) {
                        c.setBackground(new Color(0, 102, 155)); 
                        c.setForeground(Color.WHITE);           
                    } else {
                        c.setBackground(new Color(173,216,230)); 
                        c.setForeground(Color.BLACK);              
                    }
                    
                    
                    if (c instanceof JLabel) {
                        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                    }

                    if (c instanceof JComponent) {
                        ((JComponent) c).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE)); 
                    }

                    return c;
                }
            });
            
            JTableHeader header = tblHistorial.getTableHeader();
            header.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {

                    JLabel label = new JLabel(value.toString());
                    label.setBackground(new Color(0, 102, 153)); 
                    label.setForeground(Color.WHITE);           
                    label.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
                    label.setOpaque(true);                       
                    label.setHorizontalAlignment(SwingConstants.CENTER); 
                    label.setBorder(BorderFactory.createEtchedBorder());

                    return label;
                }
            });
            
            cmbBuscarPor.setBackground(new Color(0,102,153)); 
            cmbBuscarPor.setForeground(Color.BLACK);           
 
            cmbBuscarPor.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    if (isSelected) {
                        label.setBackground(new Color(153,217,234)); 
                        label.setForeground(Color.WHITE);            
                    } else {
                        label.setBackground(new Color(0,150,150)); 
                        label.setForeground(Color.BLACK);             
                    }

                    return label;
                }
            });

            configurarColumnas();

            cmbBuscarPor.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtBuscar.setText("");
                    String seleccion = (String) cmbBuscarPor.getSelectedItem();

                    if (seleccion == null || seleccion.equals("Seleccionar...")) {
                        txtBuscar.setEnabled(false);
                        return;
                    } else {
                        txtBuscar.setEnabled(true);
                    }

                    if ("Telefono".equals(seleccion)) {
                        txtBuscar.setDocument(new javax.swing.text.PlainDocument() {
                            @Override
                            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException {
                                if (str != null && str.matches("[0-9]+")) {
                                    super.insertString(offs, str, a);
                                }
                            }
                        });
                    } else if ("Nombre".equals(seleccion)) {
                        txtBuscar.setDocument(new javax.swing.text.PlainDocument() {
                            @Override
                            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException {
                                if (str != null && str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                                    super.insertString(offs, str, a);
                                }
                            }
                        });
                    } else if ("Correo".equals(seleccion)) {
                        txtBuscar.setDocument(new javax.swing.text.PlainDocument() {
                            @Override
                            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException {
                                if (str != null) {
                                    String nuevoTexto = new StringBuilder(txtBuscar.getText()).insert(offs, str).toString();
                                    if (nuevoTexto.matches("[a-zA-Z0-9._%+-]*@?[a-zA-Z0-9.-]*\\.?[a-zA-Z]{0,6}")) {
                                        super.insertString(offs, str, a);
                                    }
                                }
                            }
                        });
                    } else {
                        txtBuscar.setDocument(new javax.swing.text.PlainDocument());
                    }
                   
                    modeloTabla.setRowCount(0); 
                    historialDAO.obtenerListadoHistorial(modeloTabla); 
                }
            });
        }

        private void configurarColumnas() {
            TableColumnModel modeloColumnas = tblHistorial.getColumnModel();

            int totalAncho = tblHistorial.getWidth();

            int anchoCol1 = (int) (totalAncho * 0.4);
            int anchoCol2 = (int) (totalAncho * 0.1);
            int anchoCol3 = (int) (totalAncho * 0.1);
            int anchoCol4 = (int) (totalAncho * 0.3);
            int anchoCol5 = (int) (totalAncho * 0.05);
            int anchoCol6 = (int) (totalAncho * 0.05);
            int anchoCol7 = (int) (totalAncho * 0.05);

            modeloColumnas.getColumn(0).setPreferredWidth(anchoCol1);
            modeloColumnas.getColumn(1).setPreferredWidth(anchoCol2);
            modeloColumnas.getColumn(2).setPreferredWidth(anchoCol3);
            modeloColumnas.getColumn(3).setPreferredWidth(anchoCol4);
            modeloColumnas.getColumn(4).setPreferredWidth(anchoCol5);
            modeloColumnas.getColumn(5).setPreferredWidth(anchoCol6);
            modeloColumnas.getColumn(6).setPreferredWidth(anchoCol7);

        }
    
    public void actualizarListadoHistorial() {
        modeloTabla.setRowCount(0);

        historialDAO.obtenerListadoHistorial(modeloTabla);

        configurarColumnas();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panelinfo1 = new javax.swing.JPanel();
        lblHistorialClientes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        cmbBuscarPor = new javax.swing.JComboBox<>();
        pbuscar = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        Pmostrar = new javax.swing.JPanel();
        btnMostrarDatos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Pexpo = new javax.swing.JPanel();
        btnExportarDatos = new javax.swing.JButton();
        Peli = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblBuscarPor = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(217, 243, 255));

        Panelinfo1.setBackground(new java.awt.Color(122, 197, 205));
        Panelinfo1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHistorialClientes.setBackground(new java.awt.Color(255, 255, 255));
        lblHistorialClientes.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 24)); // NOI18N
        lblHistorialClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblHistorialClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/history.png"))); // NOI18N
        lblHistorialClientes.setText(" HISTORIAL DE CLIENTES");
        Panelinfo1.add(lblHistorialClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 15, -1, -1));

        tblHistorial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Teléfono", "Correo", "Fecha", "Hora", "Personas", "Mesa", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHistorial);

        Panelinfo1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 1083, 402));

        txtBuscar.setBackground(new java.awt.Color(0, 102, 153));
        txtBuscar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setToolTipText("Ingrese el dato a buscar");
        txtBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Panelinfo1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 383, 40));

        cmbBuscarPor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbBuscarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar...", "Nombre", "Telefono", "Correo" }));
        cmbBuscarPor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbBuscarPor.setPreferredSize(new java.awt.Dimension(103, 30));
        Panelinfo1.add(cmbBuscarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 156, 40));

        pbuscar.setBackground(new java.awt.Color(0, 102, 102));

        btnBuscar.setBackground(new java.awt.Color(102, 153, 0));
        btnBuscar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/search_client.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setIconTextGap(8);
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

        javax.swing.GroupLayout pbuscarLayout = new javax.swing.GroupLayout(pbuscar);
        pbuscar.setLayout(pbuscarLayout);
        pbuscarLayout.setHorizontalGroup(
            pbuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pbuscarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pbuscarLayout.setVerticalGroup(
            pbuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Panelinfo1.add(pbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 90, 130, 40));

        Pmostrar.setBackground(new java.awt.Color(0, 51, 102));
        Pmostrar.setForeground(new java.awt.Color(255, 255, 255));

        btnMostrarDatos.setBackground(new java.awt.Color(147, 200, 214));
        btnMostrarDatos.setFont(new java.awt.Font("Roboto SemiCondensed ExtraBold", 1, 18)); // NOI18N
        btnMostrarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/user_data.png"))); // NOI18N
        btnMostrarDatos.setText("Mostrar Datos");
        btnMostrarDatos.setBorder(null);
        btnMostrarDatos.setBorderPainted(false);
        btnMostrarDatos.setContentAreaFilled(false);
        btnMostrarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMostrarDatos.setPreferredSize(new java.awt.Dimension(75, 35));
        btnMostrarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMostrarDatosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMostrarDatosMouseExited(evt);
            }
        });
        btnMostrarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PmostrarLayout = new javax.swing.GroupLayout(Pmostrar);
        Pmostrar.setLayout(PmostrarLayout);
        PmostrarLayout.setHorizontalGroup(
            PmostrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        PmostrarLayout.setVerticalGroup(
            PmostrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMostrarDatos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Panelinfo1.add(Pmostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 90, 160, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        Panelinfo1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1120, 10));

        Pexpo.setBackground(new java.awt.Color(102, 153, 0));

        btnExportarDatos.setBackground(new java.awt.Color(102, 153, 0));
        btnExportarDatos.setFont(new java.awt.Font("Roboto SemiCondensed ExtraBold", 1, 18)); // NOI18N
        btnExportarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/excel.png"))); // NOI18N
        btnExportarDatos.setText("Exportar Datos");
        btnExportarDatos.setBorder(null);
        btnExportarDatos.setBorderPainted(false);
        btnExportarDatos.setContentAreaFilled(false);
        btnExportarDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportarDatos.setPreferredSize(new java.awt.Dimension(75, 35));
        btnExportarDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExportarDatosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExportarDatosMouseExited(evt);
            }
        });
        btnExportarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PexpoLayout = new javax.swing.GroupLayout(Pexpo);
        Pexpo.setLayout(PexpoLayout);
        PexpoLayout.setHorizontalGroup(
            PexpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(PexpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PexpoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnExportarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        PexpoLayout.setVerticalGroup(
            PexpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(PexpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PexpoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnExportarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Panelinfo1.add(Pexpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 590, 180, 40));

        Peli.setBackground(new java.awt.Color(153, 0, 0));

        btnEliminar.setBackground(new java.awt.Color(248, 149, 149));
        btnEliminar.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/delete_data.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setPreferredSize(new java.awt.Dimension(75, 35));
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PeliLayout = new javax.swing.GroupLayout(Peli);
        Peli.setLayout(PeliLayout);
        PeliLayout.setHorizontalGroup(
            PeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
            .addGroup(PeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PeliLayout.createSequentialGroup()
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        PeliLayout.setVerticalGroup(
            PeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
            .addGroup(PeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PeliLayout.createSequentialGroup()
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Panelinfo1.add(Peli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 160, 40));

        jPanel5.setBackground(new java.awt.Color(0, 51, 102));

        lblBuscarPor.setFont(new java.awt.Font("Roboto SemiCondensed Black", 1, 18)); // NOI18N
        lblBuscarPor.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscarPor.setText("    Buscar por:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBuscarPor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBuscarPor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Panelinfo1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 40));

        jPanel6.setBackground(new java.awt.Color(16, 78, 119));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        Panelinfo1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelinfo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelinfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String valor = txtBuscar.getText().trim();
        String seleccion = (String) cmbBuscarPor.getSelectedItem();

        if (seleccion == null || seleccion.equals("Seleccionar...")) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un campo de búsqueda.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (valor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor escribe un valor a buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.setRowCount(0);

        switch (seleccion) {
            case "Nombre":
                historialDAO.buscarPorCampo("Nombre", valor, modeloTabla);
                break;
            case "Telefono":
                historialDAO.buscarPorCampo("Telefono", valor, modeloTabla);
                break;
            case "Correo":
                historialDAO.buscarPorCampo("Correo", valor, modeloTabla);
                break;
        }

        
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados con ese valor.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private String mostrarDato(DefaultTableModel modelo, int fila, int columna) {
        Object valor = modelo.getValueAt(fila, columna);
        return (valor != null) ? valor.toString() : "N/A";
    }
    
    private void btnMostrarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarDatosActionPerformed
        int filaSeleccionada = tblHistorial.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una fila.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) tblHistorial.getModel();

        String nombre = mostrarDato(modeloTabla, filaSeleccionada, 1);
        String telefono = mostrarDato(modeloTabla, filaSeleccionada, 2);
        String correo = mostrarDato(modeloTabla, filaSeleccionada, 3);
        String fecha = mostrarDato(modeloTabla, filaSeleccionada, 4);
        String hora = mostrarDato(modeloTabla, filaSeleccionada, 5);
        String personas = mostrarDato(modeloTabla, filaSeleccionada, 6);
        String mesa = mostrarDato(modeloTabla, filaSeleccionada, 7);

        String mensaje = "Datos del Cliente Seleccionado:\n\n" +
                         "Nombre: " + nombre + "\n" +
                         "Teléfono: " + telefono + "\n" +
                         "Correo: " + correo + "\n" +
                         "Fecha: " + fecha + "\n" +
                         "Hora: " + hora + "\n" +
                         "Personas: " + personas + "\n" +
                         "Mesa: " + mesa;

        JOptionPane.showMessageDialog(this, mensaje, "Datos", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnMostrarDatosActionPerformed

    private void btnExportarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarDatosActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx"));

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().endsWith(".xlsx")) {
                archivo = new File(archivo.getAbsolutePath() + ".xlsx");
            }

            try (Workbook libro = new XSSFWorkbook()) {
                Sheet hoja = libro.createSheet("Historial");

                
                Row filaEncabezados = hoja.createRow(0);
                TableColumnModel columnasVisibles = tblHistorial.getColumnModel();

                for (int i = 0; i < columnasVisibles.getColumnCount(); i++) {
                    String nombreColumna = columnasVisibles.getColumn(i).getHeaderValue().toString();
                    Cell celda = filaEncabezados.createCell(i);
                    celda.setCellValue(nombreColumna);
                }

                for (int i = 0; i < tblHistorial.getRowCount(); i++) {
                    Row fila = hoja.createRow(i + 1);
                    for (int j = 0; j < columnasVisibles.getColumnCount(); j++) {
                        int modeloIndex = columnasVisibles.getColumn(j).getModelIndex();
                        Object valor = tblHistorial.getValueAt(i, j); 
                        Cell celda = fila.createCell(j);
                        celda.setCellValue(valor != null ? valor.toString() : "N/A");
                    }
                }

                try (FileOutputStream out = new FileOutputStream(archivo)) {
                    libro.write(out);
                    JOptionPane.showMessageDialog(this, "Datos exportados exitosamente a:\n" + archivo.getAbsolutePath(), "Exportación exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
                
                    
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (archivo.exists()) {
                            desktop.open(archivo);
                        }
                    }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar los datos:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnExportarDatosActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblHistorial.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una fila para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabla.getValueAt(filaSeleccionada, 0); 

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = historialDAO.eliminarCliente(idReserva);
            if (eliminado) {
                modeloTabla.removeRow(filaSeleccionada); 
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
         pbuscar.setBackground(new Color(0,153,153));
    }//GEN-LAST:event_btnBuscarMouseEntered

    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
         pbuscar.setBackground(new Color(0,102,102));
    }//GEN-LAST:event_btnBuscarMouseExited

    private void btnMostrarDatosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMostrarDatosMouseEntered
        Pmostrar.setBackground(new Color(0,78,155));
    }//GEN-LAST:event_btnMostrarDatosMouseEntered

    private void btnMostrarDatosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMostrarDatosMouseExited
        Pmostrar.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_btnMostrarDatosMouseExited

    private void btnExportarDatosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarDatosMouseEntered
       Pexpo.setBackground(new Color(134,198,0));
    }//GEN-LAST:event_btnExportarDatosMouseEntered

    private void btnExportarDatosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarDatosMouseExited
       Pexpo.setBackground(new Color(102,153,0)); 
    }//GEN-LAST:event_btnExportarDatosMouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        Peli.setBackground(new Color(230,0,0)); 
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
       Peli.setBackground(new Color(153,0,0)); 
    }//GEN-LAST:event_btnEliminarMouseExited

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panelinfo1;
    private javax.swing.JPanel Peli;
    private javax.swing.JPanel Pexpo;
    private javax.swing.JPanel Pmostrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportarDatos;
    private javax.swing.JButton btnMostrarDatos;
    private javax.swing.JComboBox<String> cmbBuscarPor;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscarPor;
    private javax.swing.JLabel lblHistorialClientes;
    private javax.swing.JPanel pbuscar;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
