package Pantallas;

import com.mycompany.restmaster.InventarioDAO;
import com.mycompany.restmaster.Producto;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class Inventario extends javax.swing.JPanel {

    /**
     * Creates new form Inventario
     */
    
    private InventarioDAO inventarioDAO;
    private DefaultTableModel modeloTabla;
    public boolean cancelado = false; 
    
    public Inventario() {
        initComponents();
        
        inventarioDAO = new InventarioDAO();
        
        modeloTabla = (DefaultTableModel) tblInventario.getModel();

        inventarioDAO.obtenerListadoProductos(modeloTabla);
        
        agregarBotones();
        
        tblInventario.removeColumn(tblInventario.getColumn("ID"));
        
        configurarColumnas();
     
        tblInventario.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

          
                if (isSelected) {
                    c.setBackground(new Color(150, 111, 51)); 
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(210, 180, 140)); 
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

       
        JTableHeader header = tblInventario.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = new JLabel(value.toString());
                label.setBackground(new Color(120, 72, 0)); 
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEtchedBorder());

                return label;
            }
        });
    }
    
    
    private void configurarColumnas() {
    TableColumnModel modeloColumnas = tblInventario.getColumnModel();

    int totalAncho = tblInventario.getWidth();

    int anchoCol1 = (int) (totalAncho * 0.3);
    int anchoCol2 = (int) (totalAncho * 0.2);
    int anchoCol3 = (int) (totalAncho * 0.2);
    int anchoCol4 = (int) (totalAncho * 0.2);
    int anchoCol5 = (int) (totalAncho * 0.2);

    modeloColumnas.getColumn(0).setPreferredWidth(anchoCol1);
    modeloColumnas.getColumn(1).setPreferredWidth(anchoCol2);
    modeloColumnas.getColumn(2).setPreferredWidth(anchoCol3);
    modeloColumnas.getColumn(3).setPreferredWidth(anchoCol4);
    modeloColumnas.getColumn(4).setPreferredWidth(anchoCol5);

    int alturaFila = 30; 
    tblInventario.setRowHeight(alturaFila);
}
    
    public void actualizarListadoProductos() {
            modeloTabla.setRowCount(0);  

            inventarioDAO.obtenerListadoProductos(modeloTabla);

            configurarColumnas(); 
    }
    
    private void agregarBotones() {
      
        tblInventario.getColumnModel().getColumn(
            tblInventario.getColumnModel().getColumnIndex("Editar"))
            .setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
                JButton btn = new JButton("Editar");
                btn.setPreferredSize(new Dimension(40, 15));

               
                btn.setBackground(new Color(0, 123, 255));  
                btn.setForeground(Color.WHITE);  

             
                btn.setFocusPainted(false);

                return btn;
            });

        
        tblInventario.getColumnModel().getColumn(
            tblInventario.getColumnModel().getColumnIndex("Eliminar"))
            .setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
                JButton btn = new JButton("Eliminar");
                btn.setPreferredSize(new Dimension(40, 15));

            
                btn.setBackground(new Color(220, 53, 69));  
                btn.setForeground(Color.WHITE);  

                btn.setFocusPainted(false);

                return btn;
            });

   
    tblInventario.getColumnModel().getColumn(
        tblInventario.getColumnModel().getColumnIndex("Editar")).setCellEditor(new AccionBotones());

    tblInventario.getColumnModel().getColumn(
        tblInventario.getColumnModel().getColumnIndex("Eliminar")).setCellEditor(new AccionBotones());
}

    class AccionBotones extends AbstractCellEditor implements TableCellEditor {

    private final JButton btnEditar;
    private final JButton btnEliminar;
    private final JPanel panel = new JPanel(new FlowLayout());

    public AccionBotones() {
       
        btnEditar = new JButton("Editar");
        btnEditar.setPreferredSize(new Dimension(80, 25));

       
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(new Dimension(80, 25));

       
       btnEditar.addActionListener(e -> {

    if (filaSeleccionada >= 0) {

        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        Producto productoAEditar = inventarioDAO.obtenerProducto(idProducto);

        if (productoAEditar == null) {
            JOptionPane.showMessageDialog(null, "Error: no se pudo obtener el producto.");
            return;
        }

        FormularioProducto formulario = new FormularioProducto();
        formulario.setVentanaPrincipal(Inventario.this);
        formulario.setProducto(productoAEditar);
        formulario.setVisible(true);

    } else {
        JOptionPane.showMessageDialog(null, "Primero seleccione un producto");
    }
});
       
       btnEliminar.addActionListener(e -> {

    if (filaSeleccionada >= 0) {

        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar este producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int resultado = inventarioDAO.eliminarProducto(idProducto);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado");
                actualizarListadoProductos();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");
            }
        }

    } else {
        JOptionPane.showMessageDialog(null, "Primero seleccione un producto");
    }
});
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
    private int filaSeleccionada;
        @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.removeAll();

        filaSeleccionada = row; 

        int colEditar = tblInventario.getColumnModel().getColumnIndex("Editar");
        int colEliminar = tblInventario.getColumnModel().getColumnIndex("Eliminar");

        if (column == colEditar) {
            panel.add(btnEditar);
        } else if (column == colEliminar) {
            panel.add(btnEliminar);
        }

        return panel;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        AgregarProducto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(252, 232, 159));
        jPanel1.setForeground(new java.awt.Color(249, 225, 154));

        tblInventario.setBackground(new java.awt.Color(210, 183, 160));
        tblInventario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblInventario.setFont(new java.awt.Font("Segoe UI Symbol", 0, 12)); // NOI18N
        tblInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Precio", "Cantidad", "Editar", "Eliminar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInventario);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setText("INVENTARIO");

        AgregarProducto.setBackground(new java.awt.Color(89, 38, 9));
        AgregarProducto.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        AgregarProducto.setForeground(new java.awt.Color(255, 255, 255));
        AgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/agregarProd.png"))); // NOI18N
        AgregarProducto.setText("Agregar Producto");
        AgregarProducto.setBorder(null);
        AgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                        .addComponent(AgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(109, Short.MAX_VALUE)
                        .addComponent(AgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/food12.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProductoActionPerformed
        FormularioProducto formulario = new FormularioProducto();
        formulario.setVentanaPrincipal(Inventario.this); 
        formulario.setModoNuevo();  
        formulario.setVisible(true);
    }//GEN-LAST:event_AgregarProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInventario;
    // End of variables declaration//GEN-END:variables
}
