package ejercicio3Test;

import ejercicio3.PermisosServiceStatic;
import ejercicio3.PermisosStatic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PermisosTest {
    MockedStatic<PermisosServiceStatic> overridePermisosServStatic;

    @BeforeEach
    public void setup(){
        overridePermisosServStatic = Mockito.mockStatic(PermisosServiceStatic.class);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Dylan", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Joel", "joel123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Juan", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Pablo", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Jose", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Maria", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Sebastian", "seb123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Hector", "hector123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Carla", "pass123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Ana", "admin")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Cliente1", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Cliente2", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Cliente3", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("Cliente4", "admin123")).thenReturn(true);
        overridePermisosServStatic.when(() -> PermisosServiceStatic.isValidUser("German", "admin123")).thenReturn(false);


        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Dylan")).thenReturn("CRUD");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Joel")).thenReturn("");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Juan")).thenReturn("C");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Pablo")).thenReturn("R");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Jose")).thenReturn("U");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Maria")).thenReturn("D");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Sebastian")).thenReturn("CR");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Hector")).thenReturn("CU");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Carla")).thenReturn("RD");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Ana")).thenReturn("UD");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Cliente1")).thenReturn("CRU");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Cliente2")).thenReturn("CRD");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Cliente3")).thenReturn("CUD");
        overridePermisosServStatic.when(() -> PermisosServiceStatic.getRoles("Cliente4")).thenReturn("RUD");
    }

    @AfterEach
    public void cleanup(){
        overridePermisosServStatic.close();
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
        PermisosStatic permisosStatic = new PermisosStatic();
        String actualResult = permisosStatic.getSession(usuarioTest, usuarioPass);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        String expectedDate = formatter.format(new Date());


        String actualPermissions = actualResult.split(" - ")[0];
        String actualDate = actualResult.split(" - ")[1];


        Assertions.assertEquals("PERMISSION : " + expectedResultRoles, actualPermissions);
        Assertions.assertTrue(actualDate.contains(expectedDate));


        overridePermisosServStatic.verify(() -> PermisosServiceStatic.isValidUser(usuarioTest, usuarioPass));
        overridePermisosServStatic.verify(() -> PermisosServiceStatic.getRoles(usuarioTest));
    }
    @ParameterizedTest
    @CsvSource({
            "German,admin123,Incorrect German and admin123"
    })
    public void verifyObtenerRolesFail(String usuarioTest, String usuarioPass, String expectedResult){
        PermisosStatic permisosStatic = new PermisosStatic();
        String actualResult = permisosStatic.getSession(usuarioTest, usuarioPass);

        Assertions.assertEquals(expectedResult, actualResult);

        overridePermisosServStatic.verify(() -> PermisosServiceStatic.isValidUser(usuarioTest, usuarioPass));
    }
}
