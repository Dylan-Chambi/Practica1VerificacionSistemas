package ejercicio3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PermisosStatic {

    public PermisosStatic(){}

    public String getSession(String usuario, String password){
        boolean esUsuarioValido = PermisosServiceStatic.isValidUser(usuario, password);
        if(esUsuarioValido){
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH mm ss");

            String CurrentDateTime = formatter.format(new Date());

            return "PERMISSION : " + PermisosServiceStatic.getRoles(usuario) + " - " + CurrentDateTime;
        }else{
            return "Incorrect " + usuario + " and " + password;
        }
    }
}
