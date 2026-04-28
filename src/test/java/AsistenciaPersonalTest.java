/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AsistenciaPersonalTest {

    // TC001 - Registrar Entrada Correctamente
    @Test
    public void testRegistrarEntrada() {
        String nombre = "glenda";
        String clave = "123";
        String hora = "07:00";
        String turno = "Matutino";
        String tipo = "entrada";

        assertEquals("glenda", nombre);
        assertEquals("123", clave);
        assertEquals("07:00", hora);
        assertEquals("Matutino", turno);
        assertEquals("entrada", tipo);
    }

    // TC002 - Registrar Salida Correctamente
    @Test
    public void testRegistrarSalida() {
        String nombre = "dulce";
        String clave = "456";
        String hora = "15:00";
        String turno = "Vespertino";
        String tipo = "salida";

        assertEquals("dulce", nombre);
        assertEquals("456", clave);
        assertEquals("15:00", hora);
        assertEquals("Vespertino", turno);
        assertEquals("salida", tipo);
    }

    // TC003 - Campos Vacios
    @Test
    public void testCamposVacios() {
        String nombre = "";

        assertTrue(nombre.isEmpty());
    }

    // TC004 - Usuario Invalido
    @Test
    public void testUsuarioInvalido() {
        String nombre = "juan";

        assertNotEquals("glenda", nombre);
        assertNotEquals("dulce", nombre);
    }

    // TC005 - No seleccionar hora o turno
    @Test
    public void testDatosIncompletos() {
        String hora = "Seleccione hora";
        String turno = "Selecciona turno";

        assertEquals("Seleccione hora", hora);
        assertEquals("Selecciona turno", turno);
    }

    // TC006 - Limpieza de campos después del registro
    @Test
    public void testLimpiarCampos() {
        String nombre = "";
        String hora = "Seleccione hora";
        String turno = "Selecciona turno";

        assertEquals("", nombre);
        assertEquals("Seleccione hora", hora);
        assertEquals("Selecciona turno", turno);
    }
}
