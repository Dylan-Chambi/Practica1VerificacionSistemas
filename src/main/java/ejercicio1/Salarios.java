package ejercicio1;

public class Salarios {

    public int botieneDescuento(int salario){
        
        if(salario > 0 && salario <= 2000){
            return salario;
        } else if (salario > 2000 && salario <= 4000) {
            return salario - (int) (salario*0.05);
        } else if (salario > 4000) {
            return salario - (int) (salario*0.15);
        }else{
            throw new ArithmeticException();
        }
    }
}
