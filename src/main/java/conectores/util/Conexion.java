package conectores.util;

//Los paquetes de java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Estas son las variables para la base de datos
    private static Connection conexion = null;
    private static final String USUARIO = "usuario";
    private static final String CLAVE = "usuario";
    private static final String URL = "jdbc:mysql://localhost:3306/securefilescs?useUnicode=true&characterEncoding=utf-8";
    
    public static Connection getConnection() {
        try {
            //Establecemos la conexión con el servidor
            conexion = DriverManager.getConnection(URL,USUARIO,CLAVE);
            if (conexion != null) {
                //si se establece la conexión se devuelve la respuesta del servidor
                System.out.println("La conexion ha sido correcta");
                return conexion;
            }
        } catch (SQLException ex) {
            //si hay algún error se muestra la excepción
            System.out.println(ex.getMessage());
        } 
        return null;
    }
    
}
