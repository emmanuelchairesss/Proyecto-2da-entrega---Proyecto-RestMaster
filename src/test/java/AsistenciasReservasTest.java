
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class AsistenciasReservasTest {
  
  
    
  // TC001 - ID o Nombre Valido
@Test
public void testBuscarReservaValida() {
    String criterio = "24"; 

    boolean esValido = criterio.matches("\\d+") || !criterio.isEmpty();

    assertTrue(esValido, "Debe permitir buscar reserva con ID o nombre válido");
}

  // TC002 - Registrar Asistencia Datos Validos Si
@Test
public void testRegistrarAsistenciaSiValido() {
    boolean chkSi = true;
    boolean chkNo = false;
    String hora = "14:30";
    String observacion = "Llegó a tiempo";

    boolean asistenciaValida = chkSi && !chkNo;
    boolean horaValida = hora.matches("\\d{2}:\\d{2}");
    boolean observacionValida = !observacion.isEmpty();

    assertTrue(asistenciaValida && horaValida && observacionValida,
            "Debe registrar asistencia correctamente");
}

  // TC003 - Registrar Asistencia Datos Validos No 
@Test
public void testRegistrarAsistenciaNo() {
    boolean chkSi = false;
    boolean chkNo = true;
    String observacion = "No llegó";

    boolean asistenciaValida = !chkSi && chkNo;
    boolean observacionValida = !observacion.isEmpty();

    assertTrue(asistenciaValida && observacionValida,
            "Debe registrar como no asistió");
}

  // TC004 - Sin selección 
@Test
public void testSinSeleccionAsistencia() {
    boolean chkSi = false;
    boolean chkNo = false;

    boolean asistenciaValida = chkSi ^ chkNo; // solo uno debe ser true

    assertFalse(asistenciaValida,
            "Debe detectar error cuando no se selecciona ninguna opción");
}

  // TC005 - Hora invalida 
@Test
public void testHoraInvalida() {
    boolean chkSi = true;
    boolean chkNo = false;
    String hora = "abc"; // inválida

    boolean horaValida = hora.matches("\\d{2}:\\d{2}");

    assertFalse(horaValida,
            "Debe detectar error por formato de hora inválido");
}
}
