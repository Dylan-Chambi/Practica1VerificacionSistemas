package ejercicio2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Permisos {
    PermisosService permisosService;

    public Permisos(){
        permisosService = new PermisosService();
    }

    public Permisos(PermisosService permisosService){
        this.permisosService = permisosService;
    }
    public String getSession(String usuario, String password){
        boolean esUsuarioValido = permisosService.isValidUser(usuario, password);
        if(esUsuarioValido){
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH mm ss");

            String CurrentDateTime = formatter.format(new Date());

            return "PERMISSION : " + permisosService.getRoles(usuario) + " - " + CurrentDateTime;
        }else{
            return "Incorrect " + usuario + " and " + password;
        }
    }
}
