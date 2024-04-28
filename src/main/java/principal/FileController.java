package principal;

import conectores.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileController {
    
    //variable para establecer la conexión con la base de datos
    private Connection conexion = Conexion.getConnection();
    
    //Función para insertar el archivo en la base de datos
    public int insertFile(String nombre,String  clave,String img, String email){
        String sql = "insert into Ficheros (nombre,archivo) values(?,?)";
        PreparedStatement ps;
        
        try {
            conexion.setAutoCommit(false);
            
            //Los datos que se envían en la query
            ps = conexion.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            ps.setString(2,img );
            ps.executeUpdate();
            
            //obtenemos la fila insertada
            ResultSet respuesta = ps.getGeneratedKeys();
            
            //nos guardamos con el id que se ha guardado
            int idArchivo=-1;
            if(respuesta.next()){
                idArchivo= respuesta.getInt(1);
            }
             
            
            System.out.println("respuesta: " + idArchivo);
            
            String sql2 = "insert into tiene (email, archivo,clave) values (?,?,?)";
            PreparedStatement ps2;
            ps2 = conexion.prepareStatement(sql2);
            ps2.setString(1,email);
            ps2.setInt(2,idArchivo);
            ps2.setString(3, clave);
            ps2.executeUpdate();
            
            conexion.commit();
            return idArchivo;
         
         //Manejo de excepciones
        } catch (SQLException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //en caso de error
        return -1;
    }
    
    public boolean updateFile(int id, String ruta){
        String sql = "update ficheros set archivo = ? where id = ?";
        PreparedStatement ps;
        
        try {
            conexion.setAutoCommit(false);
             
            //Los datos que se envían en la query
            ps = conexion.prepareStatement(sql);
            ps.setString(1, ruta);
            ps.setInt(2,id );
            ps.executeUpdate();
            
            conexion.commit();
            
            return true;
        
         //Manejo de excepciones
        } catch (SQLException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            
            try {
                conexion.rollback();
                return false;  
            } catch (SQLException ex1) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }
    
    //Función para obtener todos los archivos de la base de datos
    public ResultSet getFiles(){
        String sql = "select nombre from Ficheros";
        PreparedStatement ps;
        
        try{
            //Se ejecuta y crea la query y devolvemos un ResutSet
            ps = conexion.prepareStatement(sql);
            ResultSet files = ps.executeQuery();
            return files;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //Función para obtener todos los archivos de la base de datos
    public ResultSet getFilesNameByUser(String email){
        String sql = "select nombre from Ficheros, tiene where email = ? and Ficheros.id = tiene.archivo ";
        PreparedStatement ps;
        
        try{
            //Se ejecuta y crea la query y devolvemos un ResutSet
            ps = conexion.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet files = ps.executeQuery();
            return files;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //Devuelve los datos del arhivo que se indica
    public ResultSet getFileByName(String nombre, String email){
        String sql = "select ficheros.id, nombre, clave, ficheros.archivo from  tiene, ficheros where nombre = ? and email = ? and ficheros.id = tiene.archivo";
        PreparedStatement ps;
        
        try{
            //Se ejecuta y crea la query y devolvemos un ResutSet
            ps = conexion.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setString(2,email);
            ResultSet files = ps.executeQuery();
            return files;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ResultSet getUserByEmail(String email){
        String sql = "Select email, password, publica, privada from usuarios where email = ?";
        PreparedStatement ps;
        
        try{
            //Se ejecuta y crea la query y devolvemos un ResutSet
            ps = conexion.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet rows = ps.executeQuery();
            return rows;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //metodo para crear un usuario en la base de datos
    public boolean crearUsuario (String email, String password, String publica, String privada){
        String sql = "insert into usuarios (email, password, publica, privada) values (?,?,?,?)";
        PreparedStatement ps;
        //le pasamos los valores recogidos en la interfaz
        try{
           ps = conexion.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2,password );
            ps.setString(3,publica );
            ps.setString(4,privada );
            ps.executeUpdate();
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        return false;
        }
       
    }
    
    
    public boolean shareFile (String email, int idFoto, String clave){
        String sql = "insert into tiene ( email, archivo ,clave ) values (?,?,?)";
        PreparedStatement ps;
        //le pasamos los valores recogidos en la interfaz
        try{
           ps = conexion.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setInt(2,idFoto );
            ps.setString(3,clave );
            ps.executeUpdate();

            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean deleteFile (String email, String archivo) throws SQLException{
        
        //obtenemos fichero
        ResultSet auxFile =  getFileByName(archivo, email);
        auxFile.next();
        
        String sql = "delete from tiene where email = ? and archivo = ?";
        PreparedStatement ps;
        //le pasamos los valores recogidos en la interfaz
        try{
           ps = conexion.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setInt(2, auxFile.getInt("id"));
            ps.executeUpdate();

            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
