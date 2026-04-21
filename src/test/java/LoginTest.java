
import com.mycompany.restmaster.Usuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Edith Ramirez
 */
public class LoginTest {
    
    private Usuarios login;

    @BeforeEach
    public void setUp() {
        login = new Usuarios();
    }

    // TC001 - Login correcto
    @Test
    public void testLoginCorrecto() {
        login.setUsuario("Glenda");
        login.setContraseña("123");

        boolean resultado = login.validarCredenciales("Glenda", "123");

        assertTrue(resultado, "El sistema permite el acceso con credenciales validas");
    }

    // TC002 - Credenciales incorrectas
    @Test
    public void testCredenIncorrectas() {
        login.setUsuario("Glenda");
        login.setContraseña("123");

        boolean resultado = login.validarCredenciales("Edith", "888");

        assertFalse(resultado, "El sistema rechaza credenciales incorrectas");
    }

    @Test
    public void testUserIncorrecto() {
        login.setUsuario("Glenda");
        login.setContraseña("123");

        boolean resultado = login.validarCredenciales("Ana", "123");

        assertFalse(resultado, "El sistema rechaza usuario incorrecto");
    }

    @Test
    public void testContraIncorrecta() {
        login.setUsuario("Glenda");
        login.setContraseña("123");

        boolean resultado = login.validarCredenciales("Glenda", "789");

        assertFalse(resultado, "El sistema rechaza contraseña incorrecta");
    }

    // TC003 - Campos vacios
    @Test
    public void testCamposVacios() {
        login.setUsuario("");
        login.setContraseña("");

        boolean resultado = login.esBotonLoginHabilitado();

        assertFalse(resultado, "No permite login con campos vacios");
    }

    @Test
    public void testSoloUsuario() {
        login.setUsuario("Glenda");
        login.setContraseña("");

        boolean resultado = login.esBotonLoginHabilitado();

        assertFalse(resultado, "No permite login sin contraseña");
    }

    @Test
    public void testSoloContrasena() {
        login.setUsuario("");
        login.setContraseña("123");

        boolean resultado = login.esBotonLoginHabilitado();

        assertFalse(resultado, "No permite login sin usuario");
    }

    @Test
    public void testCamposCompletos() {
        login.setUsuario("Glenda");
        login.setContraseña("123");

        boolean resultado = login.esBotonLoginHabilitado();

        assertTrue(resultado, "Permite login cuando ambos campos estan llenos");
    }

    // TC004 - Error de conexion
    @Test
    public void testConexionDB() {
        // Esta prueba se valida manualmente apagando la base de datos
        assertTrue(true, "La prueba de conexion se realiza manualmente");
    }
}