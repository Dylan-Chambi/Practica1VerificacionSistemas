package ejercicio2Test;

import ejercicio2.Permisos;
import ejercicio2.PermisosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PermisosTest {
    PermisosService permisosServiceMock = Mockito.mock(PermisosService.class);

    @BeforeEach
    public void setup(){
        Mockito.when(permisosServiceMock.isValidUser("Dylan", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Joel", "joel123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Juan", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Pablo", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Jose", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Maria", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Sebastian", "seb123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Hector", "hector123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Carla", "pass123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Ana", "admin")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Cliente1", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Cliente2", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Cliente3", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("Cliente4", "admin123")).thenReturn(true);
        Mockito.when(permisosServiceMock.isValidUser("German", "admin123")).thenReturn(false);
        Mockito.when(permisosServiceMock.isValidUser("Cliente5", "admin")).thenReturn(false);


        Mockito.when(permisosServiceMock.getRoles("Dylan")).thenReturn("CRUD");
        Mockito.when(permisosServiceMock.getRoles("Joel")).thenReturn("");
        Mockito.when(permisosServiceMock.getRoles("Juan")).thenReturn("C");
        Mockito.when(permisosServiceMock.getRoles("Pablo")).thenReturn("R");
        Mockito.when(permisosServiceMock.getRoles("Jose")).thenReturn("U");
        Mockito.when(permisosServiceMock.getRoles("Maria")).thenReturn("D");
        Mockito.when(permisosServiceMock.getRoles("Sebastian")).thenReturn("CR");
        Mockito.when(permisosServiceMock.getRoles("Hector")).thenReturn("CU");
        Mockito.when(permisosServiceMock.getRoles("Carla")).thenReturn("RD");
        Mockito.when(permisosServiceMock.getRoles("Ana")).thenReturn("UD");
        Mockito.when(permisosServiceMock.getRoles("Cliente1")).thenReturn("CRU");
        Mockito.when(permisosServiceMock.getRoles("Cliente2")).thenReturn("CRD");
        Mockito.when(permisosServiceMock.getRoles("Cliente3")).thenReturn("CUD");
        Mockito.when(permisosServiceMock.getRoles("Cliente4")).thenReturn("RUD");
    }

    @ParameterizedTest
    @CsvSource({
            "Dylan,admin123,CRUD",
            "Joel, joel123,''",
            "Juan,admin123,C",
            "Pablo,admin123,R",
            "Jose,admin123,U",
            "Maria,admin123,D",
            "Sebastian, seb123, CR",
            "Hector,hector123,CU",
            "Carla,pass123,RD",
            "Ana,admin,UD",
            "Cliente1, admin123, CRU",
            "Cliente2, admin123, CRD",
            "Cliente3, admin123, CUD",
            "Cliente4, admin123, RUD"
    })
    public void verifyObtenerRoles(String usuarioTest, String usuarioPass, String expectedResultRoles){
        Permisos permisos = new Permisos(permisosServiceMock);
        String actualResult = permisos.getSession(usuarioTest, usuarioPass);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        String expectedDate = formatter.format(new Date());


        String actualPermissions = actualResult.split(" - ")[0];
        String actualDate = actualResult.split(" - ")[1];


        Assertions.assertEquals("PERMISSION : " + expectedResultRoles, actualPermissions);
        Assertions.assertTrue(actualDate.contains(expectedDate));

        Mockito.verify(permisosServiceMock).isValidUser(usuarioTest, usuarioPass);
        Mockito.verify(permisosServiceMock).getRoles(usuarioTest);
    }
    @ParameterizedTest
    @CsvSource({
            "German,admin123,Incorrect German and admin123",
            "Cliente5,admin,Incorrect Cliente5 and admin"
    })
    public void verifyObtenerRolesFail(String usuarioTest, String usuarioPass, String expectedResult){
        Permisos permisos = new Permisos(permisosServiceMock);
        String actualResult = permisos.getSession(usuarioTest, usuarioPass);

        Assertions.assertEquals(expectedResult, actualResult);

        Mockito.verify(permisosServiceMock).isValidUser(usuarioTest, usuarioPass);
    }
}
