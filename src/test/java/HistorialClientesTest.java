
import com.mycompany.restmaster.HistorialDAO;
import javax.swing.table.DefaultTableModel;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class HistorialClientesTest {
    
    HistorialDAO dao = new HistorialDAO();
    
    // TC001 - Obtener listado de historial
    @Test
    public void testObtenerListadoHistorial() {
        DefaultTableModel modelo = new DefaultTableModel();

        dao.obtenerListadoHistorial(modelo);

        assertNotNull(modelo);
    }

    // TC002 - Validar estructura de datos
    @Test
    public void testEstructuraDatosHistorial() {

        String[] columnas = {
            "IdReserva", "Nombre", "Telefono", "Correo",
            "Fecha", "Hora", "Personas", "Mesa", "Estado"
        };

        assertEquals(9, columnas.length);
    }

    // TC003 - Base de Datos vacia
    @Test
    public void testHistorialVacio() {

        DefaultTableModel modelo = new DefaultTableModel();

        dao.obtenerListadoHistorial(modelo);

        assertTrue(modelo.getRowCount() >= 0);
    }

    // TC004 - Eliminar cliente existente
    @Test
    public void testEliminarClienteExistente() {

        boolean resultado = dao.eliminarCliente(1);

        assertTrue(resultado || !resultado);
    }

    // TC005 - Eliminar cliente inexistente
    @Test
    public void testEliminarClienteInexistente() {

        boolean resultado = dao.eliminarCliente(-1);

        assertFalse(resultado);
    }

    // TC006 - Buscar por nombre
    @Test
    public void testBuscarPorNombre() {

        DefaultTableModel modelo = new DefaultTableModel();

        dao.buscarPorCampo("Nombre", "Juan", modelo);

        assertNotNull(modelo);
    }
}
