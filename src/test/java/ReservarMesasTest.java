/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import Pantallas.ReservarMesas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import javax.swing.JButton;
import java.util.HashMap;

/**
 *
 * @author Emma
 */

public class ReservarMesasTest {

    private ReservarMesas panel;

    @BeforeEach
    public void setUp() {
        panel = new ReservarMesas();
    }

    
    // TC001 - SELECCIÓN
    
    @Test
    public void testSeleccionMesaDisponible() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            if (panel.getEstadosMesas().get(btn).equals("disponible")) {
                mesa = btn;
                panel.seleccionarMesa(btn);
                break;
            }
        }

        assertNotNull(mesa);
        assertEquals(mesa, panel.getMesaSeleccionada());
        assertTrue(panel.getBtnReservarMesa().isEnabled());
    }

    @Test
    public void testSeleccionMesaReservada() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("reservada", Color.YELLOW);
            break;
        }

        panel.seleccionarMesa(mesa);

        assertFalse(panel.getBtnReservarMesa().isEnabled());
    }

    @Test
    public void testSeleccionMesaOcupada() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("ocupada", Color.RED);
            break;
        }

        panel.seleccionarMesa(mesa);

        assertFalse(panel.getBtnReservarMesa().isEnabled());
    }


    // TC002 - RESERVAR

    @Test
    public void testReservarMesaExitosa() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            if (panel.getEstadosMesas().get(btn).equals("disponible")) {
                mesa = btn;
                panel.seleccionarMesa(btn);
                break;
            }
        }

        panel.cambiarEstadoMesa("reservada", Color.YELLOW);

        assertEquals("reservada", panel.getEstadosMesas().get(mesa));
    }

    @Test
    public void testNoReservarMesaOcupada() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("ocupada", Color.RED);
            break;
        }

        panel.seleccionarMesa(mesa);

        assertFalse(panel.getBtnReservarMesa().isEnabled());
    }


    // TC003 - EDITAR

    @Test
    public void testEditarReservaValida() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("reservada", Color.YELLOW);
            break;
        }

        panel.seleccionarMesa(mesa);

        assertFalse(panel.getBtnReservarMesa().isEnabled());
    }

   
    //  TC004 - CANCELAR

    @Test
    public void testCancelarReserva() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("reservada", Color.YELLOW);
            break;
        }

        panel.cambiarEstadoMesa("disponible", Color.GREEN);

        assertEquals("disponible", panel.getEstadosMesas().get(mesa));
    }


    // TC005 - OCUPADA
    
    @Test
    public void testMesaOcupada() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            break;
        }

        panel.cambiarEstadoMesa("ocupada", Color.RED);

        assertEquals("ocupada", panel.getEstadosMesas().get(mesa));
    }


    // TC006 - FILTROS
    
    @Test
    public void testFiltrarDisponibles() {
        panel.mostrarMesasPorEstado("disponible");

        HashMap<JButton, String> estados = panel.getEstadosMesas();

        for (JButton btn : estados.keySet()) {
            if (estados.get(btn).equals("disponible")) {
                assertTrue(btn.isVisible());
            } else {
                assertFalse(btn.isVisible());
            }
        }
    }

    @Test
    public void testFiltrarReservadas() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("reservada", Color.YELLOW);
            break;
        }

        panel.mostrarMesasPorEstado("reservada");

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            if (panel.getEstadosMesas().get(btn).equals("reservada")) {
                assertTrue(btn.isVisible());
            } else {
                assertFalse(btn.isVisible());
            }
        }
    }

    @Test
    public void testFiltrarOcupadas() {
        JButton mesa = null;

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            mesa = btn;
            panel.seleccionarMesa(btn);
            panel.cambiarEstadoMesa("ocupada", Color.RED);
            break;
        }

        panel.mostrarMesasPorEstado("ocupada");

        for (JButton btn : panel.getEstadosMesas().keySet()) {
            if (panel.getEstadosMesas().get(btn).equals("ocupada")) {
                assertTrue(btn.isVisible());
            } else {
                assertFalse(btn.isVisible());
            }
        }
    }
}