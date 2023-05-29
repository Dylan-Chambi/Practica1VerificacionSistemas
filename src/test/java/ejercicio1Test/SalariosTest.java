package ejercicio1Test;

import ejercicio1.Salarios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SalariosTest {

    private Salarios salarios = new Salarios();

    // negativos: 0 -1 -2    -2147483646 -2147483647 -2147483648
    // sin descuento: 1 2     999 1000 1001    1999 2000
    // descuento del 5%: 2001 2002     2999 3000 3001   3999 4000
    // descuento del 15%: 4001 4002    1073741822 1073741823 1073741824     2147483646 2147483647
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 2",
            "999, 999",
            "1000, 1000",
            "1001, 1001",
            "1999, 1999",
            "2000, 2000",
            "2001, 1901",
            "2002, 1902",
            "2999, 2850",
            "3000, 2850",
            "3001, 2851",
            "3999, 3800",
            "4000, 3800",
            "4001, 3401",
            "4002, 3402",
            "1073741822, 912680549",
            "1073741823, 912680550",
            "1073741824, 912680551",
            "2147483646, 1825361100",
            "2147483647, 1825361100"
    })
    public void verifybotieneDescuentoPositive(int salarioTest, int expectedResult){
        int actualResult = salarios.botieneDescuento(salarioTest);
        Assertions.assertEquals(expectedResult, actualResult, "ERROR: el descuento aplicado no es el esperado");
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-1000",
            "-5000",
            "-90000000",
            "-2147483646",
            "-2147483647",
            "-2147483648"
    })
    public void verifybotieneDescuentoNegative(int salarioTest){
        Assertions.assertThrows(Exception.class, () -> {salarios.botieneDescuento(salarioTest);}, "ERROR: deberia salir una excepcion para este salario");
    }
}
