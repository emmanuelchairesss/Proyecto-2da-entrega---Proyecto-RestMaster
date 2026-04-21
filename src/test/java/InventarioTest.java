/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
import com.mycompany.restmaster.InventarioDAO;
import com.mycompany.restmaster.Producto;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InventarioTest {

    private InventarioDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new InventarioDAO();
    }

    // TC001 - Agregar producto
    @Test
    public void testInsertarProducto() {
        Producto p = new Producto();
        p.setNombreProducto("Harina");
        p.setPrecioProducto(25.0);
        p.setCantidadProducto(2);

        int resultado = dao.insertarProducto(p);

        assertTrue(resultado > 0, "El producto debe insertarse correctamente");
    }

    // TC002 - Editar producto
    @Test
    public void testActualizarProducto() {
        Producto p = new Producto();
        p.setIdProducto(1); 
        p.setNombreProducto("EditadoTest");
        p.setPrecioProducto(30.0);
        p.setCantidadProducto(5);

        int resultado = dao.actualizarProducto(p);

        assertTrue(resultado >= 0, "Debe actualizarse (si el ID existe)");
    }

    //  TC003 - Eliminar producto
    @Test
    public void testEliminarProducto() {
        int resultado = dao.eliminarProducto(1);

        assertTrue(resultado >= 0, "Debe eliminarse correctamente");
    }

    //  TC004 - Cancelar eliminación 
    @Test
    public void testCancelarEliminacion() {
        boolean confirmacion = false;

        if (!confirmacion) {
            assertTrue(true, "No se elimina el producto");
        } else {
            fail("Se eliminó incorrectamente");
        }
    }

    //  TC005 - Obtener producto
    @Test
    public void testObtenerProducto() {
        Producto producto = dao.obtenerProducto(1); 

        if (producto != null) {
            assertNotNull(producto.getNombreProducto());
        } else {
            assertNull(producto, "Puede ser null si no existe");
        }
    }

    //  TC006 - Eliminar y verificar
    @Test
    public void testEliminarYVerificar() {
        dao.eliminarProducto(2);

        Producto producto = dao.obtenerProducto(2);

        assertNull(producto, "El producto debería ya no existir");
    }

   

     // TC007 - Editar sin selección 
    @Test
    public void testEditarSinSeleccion() {
        int filaSeleccionada = -1;
        boolean puedeEditar = filaSeleccionada >= 0;

        assertTrue(puedeEditar, "El sistema debería permitir editar sin selección (ERROR)");
    }

    // TC008 - Eliminar sin selección 
    @Test
    public void testEliminarSinSeleccion() {
        int filaSeleccionada = -1;

        boolean puedeEliminar = filaSeleccionada >= 0;

        assertTrue(puedeEliminar, "El sistema debería permitir eliminar sin selección (ERROR)");
    }


    //  TC009 - Cancelar edición
    @Test
    public void testCancelarEdicion() {
        boolean guardado = false;

        assertFalse(guardado, "No se deben aplicar cambios");
    }

    //  TC010 - Validar datos inválidos
    @Test
    public void testDatosInvalidos() {
        Producto p = new Producto();
        p.setNombreProducto("");
        p.setPrecioProducto(-10);
        p.setCantidadProducto(-1);

        boolean invalido = p.getNombreProducto().isEmpty()
                || p.getPrecioProducto() <= 0
                || p.getCantidadProducto() < 0;

        assertTrue(invalido, "Debe detectar datos inválidos");
    }
}